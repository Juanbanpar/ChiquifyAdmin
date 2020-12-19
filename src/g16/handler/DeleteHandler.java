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

import g16.model.DBHelper;
import g16.model.Usuario;

public class DeleteHandler implements RequestHandler{
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler sirve para dar de baja a un usuario del sistema
		 */
		
		try {
			HttpSession session = request.getSession(true);
			String email = request.getParameter("email");
			
			System.out.println("email eliminar" + email);
			
			String query = "usuarios/delete/" + email;
			
			Client client = ClientBuilder.newClient();
			WebTarget webResource = client.target("http://localhost:11604").path(query);
			Response result = webResource.request().accept("application/json").delete();
			System.out.println("hola que ase1");
			Response auxRespon = webResource.request().accept("application/json").get();
			if (auxRespon.getStatus() != Response.Status.OK.getStatusCode()) {
				System.out.println("HTTP");
				throw new HTTPException(auxRespon.getStatus());
			}
			session.invalidate();
			System.out.println("hola que ase");
		 }catch(HTTPException h) {
			   switch(h.getStatusCode()) {
			   
			   	case 404:
			   		return "404.jsp";
			   		
			   	default:
			   		return "500.jsp";
			   }// fin switch
		   }// fin catch
		
		return "index.jsp";
	}
}
