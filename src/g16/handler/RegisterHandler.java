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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

import g16.model.*;

public class RegisterHandler implements RequestHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler permite crear un usuario en el sistema de acuerdo a los datos introducidos en el formulario
		 */
		
		String name = request.getParameter("name");
		String lastname1 = request.getParameter("lastname1");
		String lastname2 = request.getParameter("lastname2");
		String city = request.getParameter("city");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		//int balance = Integer.parseInt(request.getParameter("balance"));
		
		Usuario _usuario = new Usuario();
		_usuario.setNombre(name);
		_usuario.setApellido1(lastname1);
		_usuario.setApellido2(lastname2);
		_usuario.setCiudad(city);
		_usuario.setEmail(email);
        _usuario.setPasswd(password);
        
        //AccountManager am = new AccountManager();
        HttpSession session = request.getSession(true);
        
        String query = "usuarios/" + email;
		
        try {
			Client client = ClientBuilder.newClient();
			WebTarget webResource = client.target("http://localhost:11604").path(query);
			Usuario user = webResource.request().accept("application/json").get(new GenericType<Usuario> () {});
	        
			
				Response userResponse = webResource.request().accept("application/json").get();
				if (userResponse.getStatus() != Response.Status.OK.getStatusCode()) {
					throw new HTTPException(userResponse.getStatus());
				}
				
			
			/*
	        if(am.getUser(email) != null) {
	        	session.setAttribute("aux", "taken");
	        	return "registration.jsp";
	        }
	        */
			if(user != null) {
	        	session.setAttribute("aux", "taken");
	        	return "registration.jsp";
	        }
			
			query = "usuarios/new";
			
			client = ClientBuilder.newClient();
			webResource = client.target("http://localhost:11604").path(query);
			Invocation.Builder invocationBuilder = webResource.request(MediaType.APPLICATION_JSON);
			
			Response responseUser = invocationBuilder.post(Entity.entity(_usuario, MediaType.APPLICATION_JSON));
			if (responseUser.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(responseUser.getStatus());
			}
			user = responseUser.readEntity(Usuario.class);
	        
			/*
			DBHelper helper = new DBHelper();
			helper.insert(_usuario);
			*/
			 session.setAttribute("email", email);
	        session.setAttribute("nombre", name);
	        session.setAttribute("apellido1", lastname1);
	        session.setAttribute("apellido2", lastname2);
	        session.setAttribute("ciudad", city);
	        session.setAttribute("passwd", password);
	        session.setAttribute("aux", null);
		
        }catch(HTTPException h) {
			switch (h.getStatusCode()) {
				case 404: return "404.jsp";
				default: return "500.jsp";
			}
		}
		
        return "index";
	}

}