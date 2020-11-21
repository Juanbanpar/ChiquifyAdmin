package g16.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowSendMessageHandler implements RequestHandler{
	@Override
	
	public String process(HttpServletRequest request, HttpServletResponse response) {
		
		String email = request.getParameter("emailSeller");
		 
		HttpSession session = request.getSession(true);
		session.setAttribute("emailSeller", email);
		
		
		return "chat.jsp";
	}
}