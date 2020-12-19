package g16.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

import g16.model.*;

public class ShowAllUsersREST implements RequestHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Client client = ClientBuilder.newClient();
			WebTarget webResource = client.target("http://localhost:11604").path("usuarios");
			List <Usuario> result = webResource.request().accept("application/json").get(new GenericType<List<Usuario>> () {});
			request.setAttribute("usuarios", result);
			
			Response loginResponse = webResource.request().accept("application/json").get();
			if (loginResponse.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(loginResponse.getStatus());
			}
			
		}catch(HTTPException h) {
			switch (h.getStatusCode()) {
			case 404: return "404.jsp";
			default: return "500.jsp";
			}
		}
			
		return "index.jsp";
	}
}
