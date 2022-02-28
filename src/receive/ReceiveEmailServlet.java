package receive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.mail.internet.InternetAddress;

/**
 * Servlet implementation class ReceiveEmailServlet
 */
@WebServlet("/ReceiveEmailServlet")
public class ReceiveEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceiveEmailServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String destination = null;
		String host = "pop.gmail.com";// change accordingly
		ArrayList<Receive> receivelist = new ArrayList<Receive>();
		Receive receive = null;
		String username = "";
		String password = "";
		HttpSession session1 = request.getSession();//Tao doi tuong session
		if (session1.isNew()) {//Neu session chua co
			username = request.getParameter("email");
		    password = request.getParameter("password");
		    session1.setAttribute("email", username);
		    session1.setAttribute("password", password);
        } else {
        	username = (String)session1.getAttribute("email");
			password = (String)session1.getAttribute("password");
        }
	    
	    
	    try {
	    	
	    	Properties properties = new Properties();
	    	//Thiet lap cac thuoc tinh cho SMTP server cua gmail
	    	properties.put("mail.pop3.host", host);
	    	properties.put("mail.pop3.port", "995");
	    	properties.put("mail.pop3.starttls.enable", "true");
	    	//Lay doi tuong sesstion
	    	Session emailSession = Session.getDefaultInstance(properties);
	  
	      //Tao doi tuong store va ket noi toi pop server
	    	Store store = emailSession.getStore("pop3s");

	    	store.connect(host, username, password);

	      //tao doi tuong folder va mo folder "INBOX"
	    	Folder emailFolder = store.getFolder("INBOX");
	    	emailFolder.open(Folder.READ_ONLY);

	      // Lay danh sach cac message va cho vao mang 
	    	Message[] messages = emailFolder.getMessages();
	    	//System.out.println("messages.length---" + messages.length);

	    	for (int i = 0, n = messages.length; i < n; i++) {
	    		receive = new Receive();
	    		
	    		Message message = messages[i];
	    		Address[] froms = message.getFrom();//Lay dia chi cua nguoi gui
                String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();//Lay dia chi nguoi gui o dang String
	    		
	    		receive.setSubject(message.getSubject());//Lay subject cho vao doi tuong receive
	    		receive.setFrom(email);//Lay dia chi nguoi gui cho vao doi tuong receive
	    		receive.setSentday(message.getSentDate());//Lay ngay gui cho vao doi tuong receive
	    		receivelist.add(receive);//Them doi tuong receive vao ArrayList

	    	}
	    	request.setAttribute("ReceiveList", receivelist);//Gui ArrayList sang Jsp de hien thi
			destination = "/receiveemail.jsp";
			RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);//Chuyen sang trang jsp
			rd.forward(request, response);

	      //close the store and folder objects
		      emailFolder.close(false);
		      store.close();
		      } catch (NoSuchProviderException e) {
		    	  destination = "/login.jsp";
					RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
					rd.forward(request, response);
			      } catch (MessagingException e) {
			    	  destination = "/login.jsp";
						RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
						rd.forward(request, response);
			      } catch (Exception e) {
			    	  destination = "/login.jsp";
						RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
						rd.forward(request, response);
			      }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);

	    
	    
	}
	

}
