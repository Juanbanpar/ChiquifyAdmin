package g16.handler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
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

public class ModifyProductHandler implements RequestHandler{
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler sirve para modificar un producto del sistema, se obtienen sus nuevos atributos
		 * y se aplican.
		 */
		
		HttpSession session = request.getSession(true);
		
		int id = Integer.parseInt((String)session.getAttribute("idtoEdit"));
		String categoria = request.getParameter("categoria");
		String descripcion = request.getParameter("descripcion");
		String estado = "Disponible";
		int precio = Integer.parseInt(request.getParameter("precio"));
		String titulo = request.getParameter("titulo");
		
		/*
		ProductManager pm = new ProductManager();
		Producto product = pm.getProduct(id);
		*/
		String query = "productos/" + id;
				
		Client client = ClientBuilder.newClient();
		WebTarget webResource = client.target("http://localhost:11603").path(query);
		Producto product = webResource.request().accept("application/json").get(new GenericType<Producto> () {});
		
		try {
			Response prodResponse = webResource.request().accept("application/json").get();
			if (prodResponse.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(prodResponse.getStatus());
			}
		}catch(HTTPException h) {
			switch (h.getStatusCode()) {
			case 404: return "404.jsp";
			default: return "500";
			}
		}
		
		InputStream imagen = null;
		try {
			imagen = request.getPart("imagen").getInputStream();
			if (imagen instanceof  FileInputStream) {
				product.setImagenIS(imagen);
			} else {
				product.setBase64(product.getImagen());
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		product.setCategoria(categoria);
		product.setDescripcion(descripcion);
		product.setEstado(estado);

		product.setPrecio(precio);
		product.setTitulo(titulo);
		
		//pm.modifyProduct(id, product);
		
		query = "productos/edit/" + id;
		
		client = ClientBuilder.newClient();
		webResource = client.target("http://localhost:11603").path(query);
		Invocation.Builder invocationBuilder = webResource.request(MediaType.APPLICATION_JSON);
		
		Response responseProduct = invocationBuilder.put(Entity.entity(product, MediaType.APPLICATION_JSON));
		
		try {
			if (responseProduct.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(responseProduct.getStatus());
			}
		}catch(HTTPException h) {
			switch (h.getStatusCode()) {
			case 404: return "404.jsp";
			default: return "500";
			}
		}
		
		Producto productR = responseProduct.readEntity(Producto.class);
		
		return "user.jsp";
	}
}
