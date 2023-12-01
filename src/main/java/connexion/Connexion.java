package connexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * Servlet implementation class Connexion
 */
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connexion() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate(); 
        response.sendRedirect("Authetification.jsp"); 
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (validateUser( request, response ,username, password)) {
        	HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("Accueil.jsp");
        } else {
        	 response.sendRedirect("Authetification.jsp?error=true"); 
        }
	}
	private boolean validateUser(HttpServletRequest request, HttpServletResponse response , String username , String password) throws ServletException, IOException {
		PrintWriter out= response.getWriter();
		boolean validUser = false;
		 try {
	        	Class.forName("org.postgresql.Driver");
				Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
	            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, username);
	            stmt.setString(2, password);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	            	validUser = true;
	            }
	            rs.close();
	            stmt.close();
	            conn.close();
	        } catch (Exception e) {
	        	out.println(e);
	        }
	        return validUser;
	}

}
