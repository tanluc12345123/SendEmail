package receive;


import java.io.IOException;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;

/**
 * Servlet implementation class ReadMail
 */
@WebServlet("/ReadMail")
public class ReadMail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadMail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String destination = null;
		
		String host = "pop.gmail.com";// change accordingly
		HttpSession session1 = request.getSession();
		String username = (String)session1.getAttribute("email");
		String password = (String)session1.getAttribute("password");
//	      String username = 
//	         "sonkunnai@gmail.com";// change accordingly
//	      String password = "luc0977184804";
	      String Id = request.getParameter("ID");
	      int i = Integer.parseInt(Id);
	      try {
	      Properties properties = new Properties();
	         properties.put("mail.store.protocol", "pop3");
	         properties.put("mail.pop3.host", host);
	         properties.put("mail.pop3.port", "995");
	         properties.put("mail.pop3.starttls.enable", "true");
	         Session emailSession = Session.getDefaultInstance(properties);
	         // emailSession.setDebug(true);

	         // create the POP3 store object and connect with the pop server
	         Store store = emailSession.getStore("pop3s");

	         store.connect(host, username, password);

	         // create the folder object and open it
	         Folder emailFolder = store.getFolder("INBOX");
	         emailFolder.open(Folder.READ_ONLY);

	         

	         // retrieve the messages from the folder in an array and print it
	         Message[] messages = emailFolder.getMessages();
	         
	         Message message = messages[i];
	         
	         Address[] froms = message.getFrom();
             String from = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
             String subject = message.getSubject();
             String toList = parseAddresses(message
                     .getRecipients(RecipientType.TO));

             String ccList = parseAddresses(message
                     .getRecipients(RecipientType.CC));
             String sentDate = message.getSentDate().toString();
             String msg = "";
             //Tao doi tuong p
             Part p = (Part) message;
             if (p.isMimeType("multipart/*")) {//Kiem tra p co phai la mot multipart
//                 System.out.println("This is a Multipart");
//                 System.out.println("---------------------------");
                 Multipart mp = (Multipart) p.getContent();//Lay noi dung cua multipart
                 int count = mp.getCount();
                 for (int j = 0; j < count; j++)
                	 
                	 if (mp.getBodyPart(j).isMimeType("text/plain")) {//Kiem tra cac phan con cua mp co phai la mot text/plain
//                         System.out.println("This is plain text");
//                         System.out.println("---------------------------");
//                         System.out.println((String) mp.getBodyPart(j).getContent());
                         msg = (String) mp.getBodyPart(j).getContent();//Lay noi dung tin nhan
                         break;
                      } 
//                    writePart(mp.getBodyPart(i));
              }
             
 	    	request.setAttribute("From", from);
 	    	request.setAttribute("Subject", subject);
 	    	request.setAttribute("To", toList);
 	    	request.setAttribute("Cc", ccList);
 	    	request.setAttribute("Date", sentDate);
 	    	request.setAttribute("Message", msg);
 	    	request.setAttribute("Idmail", Id);
 			destination = "/readmail.jsp";
 			RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
 			rd.forward(request, response);
	      } catch (NoSuchProviderException e) {
		         e.printStackTrace();
		      } catch (MessagingException e) {
		         e.printStackTrace();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	      
		doGet(request, response);
	}
	private String parseAddresses(Address[] address) {
        String listAddress = "";
 
        if (address != null) {
            for (int i = 0; i < address.length; i++) {
                listAddress += address[i].toString() + ", ";
            }
        }
        if (listAddress.length() > 1) {
            listAddress = listAddress.substring(0, listAddress.length() - 2);
        }
 
        return listAddress;
    }
}
