package g16.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import g16.handler.*;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/Controller", "/register", "/update", "/delete", "/publishproduct", "/showmodifyproduct",
		"/modifyproduct", "/deleteproduct", "/search", "/showproduct", "/showuser", "/sendmessage", "/showmessages", 
		"/showsendmessage", "/complexsearch" , "/index"})
@MultipartConfig
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public controller() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    Map<String, RequestHandler> dictionary;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		//Dependinedo de la url se llama a un handler distinto
		dictionary = new HashMap<String, RequestHandler>();
		dictionary.put("/register", new RegisterHandler()); //hecho
		dictionary.put("/update", new UpdateHandler());
		dictionary.put("/delete", new DeleteHandler());
		dictionary.put("/publishproduct", new PublishProductHandler()); //hecho
		dictionary.put("/showmodifyproduct", new ShowModifyProductHandler());
		dictionary.put("/modifyproduct", new ModifyProductHandler());
		dictionary.put("/deleteproduct", new DeleteProductHandler());
		dictionary.put("/search", new SearchHandler());
		dictionary.put("/complexsearch", new ComplexSearchHandler());
		dictionary.put("/showproduct", new ShowProductHandler());
		dictionary.put("/showuser", new ShowUserHandler());	//hecho	
		dictionary.put("/sendmessage", new SendMessage()); //hecho
		dictionary.put("/showmessages", new ShowMessages()); //hecho
		dictionary.put("/showsendmessage", new ShowSendMessageHandler());

		dictionary.put("/index", new ShowAllUsersREST());

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		RequestHandler rh = dictionary.get(path);
		
		if(rh == null) {
			response.sendError(500);
		}else {
			String view = rh.process(request, response);
			
			RequestDispatcher rd = request.getRequestDispatcher(view);
			rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}