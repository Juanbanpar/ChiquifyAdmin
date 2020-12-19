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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

import g16.model.*;

public class UpdateHandler implements RequestHandler{
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler permite actualizar los datos de usuario
		 */
		
		String name = request.getParameter("name");
		String lastname1 = request.getParameter("lastname1");
		String lastname2 = request.getParameter("lastname2");
		String city = request.getParameter("city");
		String email = request.getParameter("email");
		String passwd = request.getParameter("password");
		
		Usuario _usuario = new Usuario();
		_usuario.setNombre(name);
		_usuario.setApellido1(lastname1);
		_usuario.setApellido2(lastname2);
		_usuario.setCiudad(city);
		_usuario.setEmail(email);
        _usuario.setPasswd(passwd);
		
        /*
        HttpSession session = request.getSession(true);
		DBHelper helper = new DBHelper();
		helper.updateUser((String) session.getAttribute("email"), _usuario);
		*/
        
		String query = "usuarios/edit/" + email;
		try {
			Client client = ClientBuilder.newClient();
			WebTarget webResource = client.target("http://localhost:11604").path(query);
			Invocation.Builder invocationBuilder = webResource.request(MediaType.APPLICATION_JSON);
			
			Response responseUser = invocationBuilder.put(Entity.entity(_usuario, MediaType.APPLICATION_JSON));
		
			if (responseUser.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(responseUser.getStatus());
			}
			
			Usuario user = responseUser.readEntity(Usuario.class);
			
	        HttpSession session = request.getSession(true);
	        session.setAttribute("email", _usuario.getEmail());
	        session.setAttribute("nombre", _usuario.getNombre());
	        session.setAttribute("apellido1", _usuario.getApellido1());
	        session.setAttribute("apellido2", _usuario.getApellido2());
	        session.setAttribute("ciudad", _usuario.getCiudad());
	        session.setAttribute("passwd", passwd);
	        
		}catch(HTTPException h) {
			switch(h.getStatusCode()) {
				case 404: return "404.jsp";
				default: return "500.jsp";
			}
		}
				
		return "user.jsp";
	}
}
