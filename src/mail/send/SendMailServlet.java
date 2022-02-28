package mail.send;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;



/**
 * Servlet implementation class SendMailServlet
 */
@WebServlet("/SendMailServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,   // 2MB
				maxFileSize = 1024 * 1024 * 10,         // 10MB
				maxRequestSize = 1024 * 1024 * 50) 
public class SendMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Send successful");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<File> uploadedFiles = saveUploadedFiles(request);//Lay file tu jsp va luu vao 1 folder
		HttpSession session1 = request.getSession();
		String username = (String)session1.getAttribute("email");
		String password = (String)session1.getAttribute("password");
		
		Properties prop = new Properties();
		//Cau hinh cac thuoc tinh cho SMTP server cua gmail
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		//Tao doi tuong session ket noi voi email va password
		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
		//Lay thong tin tu form
		String emailTo = request.getParameter("to");
		String emailSubject = request.getParameter("subject");
		String emailContent = request.getParameter("content");
//		String filePath = request.getParameter("file");
		//Lay list cac email can gui
		String[] to = emailTo.split(", ");
		try {
			Message message=null;
			for(String w:to) {
				//Tao doi tuong message de gui tin nhan
				message = new MimeMessage(session);
				//Thiet lap cac gia tri cho tin nhan
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(w));
				message.setSubject(emailSubject);
				message.setText(emailContent);
				//tao 1 MimeBodypart chua noi dung tin nhan
				MimeBodyPart messageBodyPart = new MimeBodyPart();
		        messageBodyPart.setContent(emailContent, "text/html");
		 
		        // creates multi-part
		        Multipart multipart = new MimeMultipart();
		        multipart.addBodyPart(messageBodyPart);//Them doi tuong vao multipart
		 
		        // adds attachments
		        if (uploadedFiles != null && uploadedFiles.size() > 0) {
		            for (File aFile : uploadedFiles) {
		                MimeBodyPart attachPart = new MimeBodyPart();//tao 1 MimeBodypart chua file gui
		 
		                try {
		                    attachPart.attachFile(aFile);//them file vao attachPart
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                }
		 
		                multipart.addBodyPart(attachPart);//Them file vao multipart
		            }
		        }
		 
		        // thiet lap noi dung bao gom noi dung va tep gui
		        message.setContent(multipart);
				Transport.send(message);
			}
			
			
		}catch(Exception e) {
	}finally {
		deleteUploadFiles(uploadedFiles);
		doGet(request, response);
	}
		
	}
	private List<File> saveUploadedFiles(HttpServletRequest request)
            throws IllegalStateException, IOException, ServletException {
        List<File> listFiles = new ArrayList<File>();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        Collection<Part> multiparts = request.getParts();//Lay cac doi tuong tu jsp
        if (multiparts.size() > 0) {
            for (Part part : request.getParts()) {
                // tao 1 file de luu tru
                String fileName = extractFileName(part);
                if (fileName == null || fileName.equals("")) {
                    // not attachment part, continue
                    continue;
                }
                File saveFile = new File(fileName);
                System.out.println("saveFile: " + saveFile.getAbsolutePath());
                FileOutputStream outputStream = new FileOutputStream(saveFile);
                 
                // Luu tep da tai len
                InputStream inputStream = part.getInputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                 
                listFiles.add(saveFile);
            }
        }
        return listFiles;
    }
 
    /**
     * Truy xuất tên tệp của phần tải lên từ tiêu đề HTTP của nó
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }
     
    /**
     * Deletes all uploaded files, should be called after the e-mail was sent.
     */
    private void deleteUploadFiles(List<File> listFiles) {
        if (listFiles != null && listFiles.size() > 0) {
            for (File aFile : listFiles) {
                aFile.delete();
            }
        }
    }
}
