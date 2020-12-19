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

public class SearchHandler implements RequestHandler{
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler obtiene la cadena de búsqueda para obtener resultados en título y descripción de los productos
		 */
		
String cadena = request.getParameter("cadena");
		
		HttpSession session = request.getSession(true);
		if(cadena != null) session.setAttribute("busqueda", cadena);
		else cadena = (String) session.getAttribute("busqueda");
		
		/*
		HttpSession session = request.getSession(true);
		session.setAttribute("cadena", cadena);
		session.setAttribute("category", null);
		*/
		
		try {
			String query = "productos/buscar/" + cadena;
			
			Client client = ClientBuilder.newClient();
			WebTarget webResource = client.target("http://localhost:11603").path(query);
			List<Producto> result = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});
			
			
			//Categorias
			String categoria = request.getParameter("category");
			List<List<Producto>> list = new ArrayList<List<Producto>>(8);
			
			//Abrigo
			query = "productos/buscaravanzado/" + "Abrigo/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			List<Producto> aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			Response auxRes = webResource.request().accept("application/json").get();
			if (auxRes.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRes.getStatus());
			}

			list.add(aux);
			
			//Pantalon
			query = "productos/buscaravanzado/" + "Pantalon/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});
			
			auxRes = webResource.request().accept("application/json").get();
			if (auxRes.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRes.getStatus());
			}
			
			list.add(aux);
			
			//Zapato
			query = "productos/buscaravanzado/" + "Zapato/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});
			
			auxRes = webResource.request().accept("application/json").get();
			if (auxRes.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRes.getStatus());
			}
			
			list.add(aux);
			
			//Vestido
			query = "productos/buscaravanzado/" + "Vestido/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			auxRes = webResource.request().accept("application/json").get();
			if (auxRes.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRes.getStatus());
			}
			
			
			list.add(aux);
			
			//Camiseta
			query = "productos/buscaravanzado/" + "Camiseta/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			auxRes = webResource.request().accept("application/json").get();
			if (auxRes.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRes.getStatus());
			}
			
			
			list.add(aux);
			
			//Chandal
			query = "productos/buscaravanzado/" + "Chandal/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			auxRes = webResource.request().accept("application/json").get();
			if (auxRes.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRes.getStatus());
			}
			
			
			list.add(aux);
			
			//Bolso
			query = "productos/buscaravanzado/" + "Bolso/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			auxRes = webResource.request().accept("application/json").get();
			if (auxRes.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRes.getStatus());
			}
			
			
			list.add(aux);
			
			//Accesorio
			query = "productos/buscaravanzado/" + "Accesorio/" + cadena;
			
			webResource = client.target("http://localhost:11603").path(query);
			aux = webResource.request().accept("application/json").get(new GenericType<List<Producto>> () {});

			auxRes = webResource.request().accept("application/json").get();
			if (auxRes.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxRes.getStatus());
			}
			
			
			list.add(aux);
			
			
			//Return
			request.setAttribute("productosSearchCategory", list);
			request.setAttribute("productosSearch", result);
			
			if(categoria == null) request.setAttribute("category", "");
			else request.setAttribute("category", categoria);
			
		}catch(HTTPException h) {
			switch(h.getStatusCode()) {
				case 404: return "404.jsp";
				case 500: return "500.jsp";
			}
		}
		return "category.jsp";
	}
	
}
