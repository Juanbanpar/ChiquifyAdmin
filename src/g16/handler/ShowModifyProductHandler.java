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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

import g16.model.*;

public class ShowModifyProductHandler implements RequestHandler{
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler obtiene el id del producto a editar y muestra la página de edición
		 */
		
		try {
			HttpSession session = request.getSession(true);
			session.setAttribute("idtoEdit", request.getParameter("Id"));
			
			String query = "productos/" + request.getParameter("Id");
			
			Client client = ClientBuilder.newClient();
			WebTarget webResource = client.target("http://localhost:11603").path(query);
			Producto result = webResource.request().accept("application/json").get(new GenericType<Producto> () {});
			
			Response auxResponse = webResource.request().accept("application/json").get();
			if (auxResponse.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxResponse.getStatus());
			}
			
			request.setAttribute("productoEdit", result);
			
		}catch(HTTPException h) {
			switch(h.getStatusCode()) {
				case 404: return "404.jsp";
				default: return "500.jsp";
			}
		}
		
		return "editProduct.jsp";
	}
}
