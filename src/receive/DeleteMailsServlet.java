package receive;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeleteMailsServlet
 */
@WebServlet("/DeleteMailsServlet")
public class DeleteMailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pop3Host = "pop.gmail.com";
	    String mailStoreType = "pop3s";	
	    HttpSession session1 = request.getSession();
	    String username = (String)session1.getAttribute("email");
		String password = (String)session1.getAttribute("password");
		Properties props = new Properties();
		props.put("mail.pop3.host", pop3Host);
	 
		//Session session = Session.getInstance(props);
		
		try {
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});
			 Store store = session.getStore(mailStoreType);
			 store.connect(pop3Host, username, password);
		 
			 Folder folder = store.getFolder("INBOX");
			 folder.open(Folder.READ_WRITE);
		 
		         Message[] messages = folder.getMessages();
		         for(String i : request.getParameterValues("product")) {
		        	 
		        	 messages[Integer.parseInt(i)].
				       setFlag(Flags.Flag.DELETED, true);
		         }
		       
		 
		       folder.close(true);	       
		       store.close();
		 
		       System.out.println("Email" +
		       		" deleted successfully.");
		       RequestDispatcher rd = request.getRequestDispatcher("ReceiveEmailServlet");
		       rd.forward(request,response);
			} catch (Exception e) {
		        e.printStackTrace();
		        System.err.println("Error in deleting email.");
		    }  
	}

}
