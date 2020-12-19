package g16.handler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import g16.model.*;

public class ShowUserHandler implements RequestHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Este handler obtiene el email del usuario a mostrar y obtiene sus datos
		 */
		
		String email = request.getParameter("email");

		DBHelper helper = new DBHelper();
		Usuario nuevo = helper.getUser(email);
		
		HttpSession session = request.getSession(true);
        session.setAttribute("email", nuevo.getEmail());
        session.setAttribute("nombre", nuevo.getNombre());
        session.setAttribute("apellido1", nuevo.getApellido1());
        session.setAttribute("apellido2", nuevo.getApellido2());
        session.setAttribute("ciudad", nuevo.getCiudad());
		
		return "user.jsp";
	}

}