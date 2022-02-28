package receive;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Store;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class DeleteMailServlet
 */
@WebServlet("/DeleteMailServlet")
public class DeleteMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pop3Host = "pop.gmail.com";
	    String mailStoreType = "pop3s";	
	    HttpSession session1 = request.getSession();
	    String username = (String)session1.getAttribute("email");
		String password = (String)session1.getAttribute("password");
	    String Id = request.getParameter("ID");
	    int i = Integer.parseInt(Id);
	    Properties props = new Properties();
		props.put("mail.pop3.host", pop3Host);
	 
		Session session = Session.getInstance(props);
	 
		try {
		 Store store = session.getStore(mailStoreType);
		 store.connect(pop3Host, username, password);
	 
		 Folder folder = store.getFolder("INBOX");
		 folder.open(Folder.READ_WRITE);
	 
	         Message[] messages = folder.getMessages();
	       messages[i].
	       setFlag(Flags.Flag.DELETED, true);
	 
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
