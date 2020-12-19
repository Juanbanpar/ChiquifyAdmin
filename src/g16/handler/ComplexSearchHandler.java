package g16.handler;

import java.util.ArrayList;
import java.util.List;

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

import com.oracle.wls.shaded.org.apache.xalan.transformer.ResultNameSpace;

import g16.model.*;

public class ComplexSearchHandler implements RequestHandler{
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler obtiene la categoría de la búsqueda avanzada y la guarda en sesión
		 */
		
		try {
			String categoria = request.getParameter("category");
			
			List<List<Producto>> result = new ArrayList<List<Producto>>(8);
			
			//Abrigo
			String cadena = request.getParameter("cadena");
			String query = "productos/buscaravanzado/" + "Abrigo/" + cadena;
			
			Client client = ClientBuilder.newClient();
			WebTarget webResource = client.target("http://localhost:11603").path(query);
			List<Producto> aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});
			
			Response auxRespon = webResource.request().accept("application/json").get();
			if (auxRespon.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRespon.getStatus());
			}
			
			result.add(aux);
			
			//Pantalon
			query = "productos/buscaravanzado/" + "Pantalon/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});
			
			auxRespon = webResource.request().accept("application/json").get();
			if (auxRespon.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRespon.getStatus());
			}
			
			result.add(aux);
			
			//Zapato
			query = "productos/buscaravanzado/" + "Zapato/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});
			
			auxRespon = webResource.request().accept("application/json").get();
			if (auxRespon.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRespon.getStatus());
			}
			
			
			result.add(aux);
			
			//Vestido
			query = "productos/buscaravanzado/" + "Vestido/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			auxRespon = webResource.request().accept("application/json").get();
			if (auxRespon.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRespon.getStatus());
			}
			
			result.add(aux);
			
			//Camiseta
			query = "productos/buscaravanzado/" + "Camiseta/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			auxRespon = webResource.request().accept("application/json").get();
			if (auxRespon.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRespon.getStatus());
			}
			
			result.add(aux);
			
			//Chandal
			query = "productos/buscaravanzado/" + "Chandal/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			auxRespon = webResource.request().accept("application/json").get();
			if (auxRespon.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRespon.getStatus());
			}
			
			result.add(aux);
			
			//Bolso
			query = "productos/buscaravanzado/" + "Bolso/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			auxRespon = webResource.request().accept("application/json").get();
			if (auxRespon.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRespon.getStatus());
			}
			
			result.add(aux);
			
			//Accesorio
			query = "productos/buscaravanzado/" + "Accesorio/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			auxRespon = webResource.request().accept("application/json").get();
			if (auxRespon.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRespon.getStatus());
			}
			
			result.add(aux);
			
			
			//Return
			request.setAttribute("productosSearchCategory", result);
			request.setAttribute("category", categoria);
			
		}catch(HTTPException h) {
			switch(h.getStatusCode()) {
			case 404:
				return "404.jsp";
			default: 
				return "500.jsp";
			}
		}
		
		return "category.jsp";
	}
	
}