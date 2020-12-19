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

import g16.model.*;

public class ShowUserHandler implements RequestHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler obtiene el email del usuario a mostrar y obtiene sus datos
		 */
		
		String email = request.getParameter("email");

		
		String query = "usuarios/" + email;
				
		Client client = ClientBuilder.newClient();
		WebTarget webResource = client.target("http://localhost:11604").path(query);
		Usuario user = webResource.request().accept("application/json").get(new GenericType<Usuario> () {});
		
		HttpSession session = request.getSession(true);
        session.setAttribute("email", user.getEmail());
        System.out.println("prueba show " + user.getEmail());
        session.setAttribute("nombre", user.getNombre());
        session.setAttribute("apellido1", user.getApellido1());
        session.setAttribute("apellido2", user.getApellido2());
        session.setAttribute("ciudad", user.getCiudad());
		
		return "user.jsp";
	}

}