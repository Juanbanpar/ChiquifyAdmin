package g16.handler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

import g16.model.*;

public class DeleteProductHandler implements RequestHandler{
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler sirve para dar de baja un producto del sistema
		 */
		
		//int id = Integer.parseInt(request.getParameter("id"));
		try {
			HttpSession session = request.getSession(true);
			int id = Integer.parseInt((String)session.getAttribute("idtoEdit"));
			
			String query = "productos/delete/" + id;
			
			Client client = ClientBuilder.newClient();
			WebTarget webResource = client.target("http://localhost:11603").path(query);
			Response result = webResource.request().accept("application/json").delete();
			
			Response auxRespon = webResource.request().accept("application/json").get();
			if (auxRespon.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRespon.getStatus());
			}
			
		 }catch(HTTPException h) {
			   switch(h.getStatusCode()) {
			   
			   	case 404:
			   		return "404.jsp";
			   		
			   	default:
			   		return "500.jsp";
			   }// fin switch
		   }// fin catch
		
		return "user.jsp";
	}
}
