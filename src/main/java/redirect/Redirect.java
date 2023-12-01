package redirect;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import materielcrud.Materielcrud;
import reparation.Reparation;

import java.io.IOException;
import agences.Agences;
import composant.Composant;

public class Redirect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		String op  = request.getParameter("test");
		if (op != null) {
			selection(request, response,op);
			}else {
			response.sendRedirect("materel.jsp");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	private void selection(HttpServletRequest request, HttpServletResponse response , String operation) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    Materielcrud materiel = new Materielcrud();
	    Composant composant = new Composant();
	    Reparation reparation = new Reparation();
	    Agences agences = new Agences();
	    switch (operation) {
		case("materiel"):materiel.Lister(request,response);break;
		case("composant"):composant.Lister(request,response);break;
		case("reparation"):reparation.lister(request,response);break;
		case("agences"):agences.agenceslist(request,response);break;
		default : response.sendRedirect("Accueil.jsp");break;
	    }
	}
}
