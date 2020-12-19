package g16.handler;
import javax.jms.ObjectMessage;
import java.io.Serializable;

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

import java.util.Enumeration;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

public class SendMessage implements RequestHandler{
	
	private javax.jms.ConnectionFactory factory = null;
	private javax.naming.InitialContext contextoInicial = null;
	private javax.jms.Destination cola = null;
	private javax.jms.Connection Qcon = null;
	private javax.jms.Session QSes = null;
	private javax.jms.MessageProducer Mpro = null;
	private javax.jms.MessageConsumer Mcon = null;
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler permite enviar mensajes entre usuarios
		 */
		
		try {
			
			HttpSession session = request.getSession();
			
			String contenido = request.getParameter("mensaje");
			String destino = (String) session.getAttribute("emailSeller");
			String origen = "admin@admin.com";

			Mensaje mensaje = new Mensaje();
			mensaje.setContenido(contenido);
			mensaje.setDestino(destino);
			mensaje.setOrigen(origen);
	        
	        String query = "/mensajes/new";
			
			Client client = ClientBuilder.newClient();
			WebTarget webResource = client.target("http://localhost:11602").path(query);
			Invocation.Builder invocationBuilder = webResource.request(MediaType.APPLICATION_JSON);
			
			Response responseMensaje = invocationBuilder.post(Entity.entity(mensaje, MediaType.APPLICATION_JSON));
			if (responseMensaje.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(responseMensaje.getStatus());
			}
			
			Mensaje mensajeR = responseMensaje.readEntity(Mensaje.class);
		
		}catch(HTTPException h) {
			switch (h.getStatusCode()){
				case 404:
					return "404.jsp";
					
				default:
					return "500.jsp";
			}
		}
			
		return "chat.jsp";
	}
}
