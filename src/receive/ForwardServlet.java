package receive;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Properties;


import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Servlet implementation class ForwardServlet
 */
@WebServlet("/ForwardServlet")
public class ForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForwardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Forward Successful");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String username = "sonkunnai@gmail.com";
//		String password = "luc0977184804";
		String username = request.getParameter("email");
	    String password = request.getParameter("password");
		String emailSMTPserver = "smtp.gmail.com";
		String emailSMTPPort = "587";
		String mailStoreType = "pop3s";	
		String Id = request.getParameter("id");
	    int i = Integer.parseInt(Id);
		Properties props = new Properties(); 
		 props.put("mail.smtp.auth", "true"); 
		 props.put("mail.smtp.starttls.enable", "true"); 
		 props.put("mail.smtp.host", emailSMTPserver); 
		 props.put("mail.smtp.port", emailSMTPPort); 
		 String emailContent = request.getParameter("content");
		 String to = request.getParameter("to");
		try {
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});
		 
		 Store mailStore = session.getStore(mailStoreType);
		 mailStore.connect(emailSMTPserver, username, password);
		 
		 Folder folder = mailStore.getFolder("INBOX");
		 folder.open(Folder.READ_ONLY);
		 
		 
		 Message emailMessage = folder.getMessage(i+1);
		 
		 
		 Message mimeMessage = new MimeMessage(session);
		 mimeMessage = (MimeMessage) emailMessage.reply(false);
		 mimeMessage.setFrom(new InternetAddress(username));
		 mimeMessage.setSubject("Fwd: " + mimeMessage.getSubject()); 
		 mimeMessage.setText(emailContent);
		 mimeMessage.setRecipients(Message.RecipientType.TO, 
					InternetAddress.parse(to));
		 
		 Transport.send(mimeMessage);
		 System.out.println("Email message " +
		   "forwarded successfully.");   
		 
		   folder.close(false);
		   mailStore.close();
		 } catch (Exception e) {
		    e.printStackTrace();
		    System.err.println("Error in forwarding email.");
		 }
		doGet(request, response);
	}

}
