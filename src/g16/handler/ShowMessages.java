package g16.handler;
import javax.jms.ObjectMessage;
import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;
import javax.jms.ObjectMessage;
import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import g16.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

public class ShowMessages implements RequestHandler{
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler permite leer mensajes de la cola del sistema que recibe el usuario de la sesión
		 */
		
		try {
			
			Client client = ClientBuilder.newClient();
			HttpSession session = request.getSession();
			String email = (String)session.getAttribute("emailSeller");
			
			String query = "/mensajes/destino/" + email;
			WebTarget webResource = client.target("http://localhost:11602").path(query);
			List <Mensaje> result = webResource.request().accept("application/json").get(new GenericType<List<Mensaje>> () {});
			
			Response auxResponse = webResource.request().accept("application/json").get();
			if (auxResponse.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new HTTPException(auxResponse.getStatus());
			}
			
			request.setAttribute("mensajesRespuesta", result);
			
		}catch(HTTPException h) {
			switch(h.getStatusCode()) {
				case 404: return "404.jsp";
				default: return "500.jsp";
			}
		}
			
		return "mensajesLeidos.jsp";
	}
}
