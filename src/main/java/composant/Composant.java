package composant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import materielcrud.Materielcrud;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import agences.Agences;


public class Composant extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		String op  = request.getParameter("action");
		if (op != null) {
			selection(request, response,op);
			}else {
			response.sendRedirect("composant.jsp");
		}
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	private void selection(HttpServletRequest request, HttpServletResponse response , String oper) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		try  {Class.forName("org.postgresql.Driver");
		switch (oper) {
		case("Liste"):Lister(request,response);break;
		case("Update"):updateData(request,response);break;
		case("Delete"):deleteData(request,response);break;
		case("insertion"):insertion(request,response);break;
		case("select"):searchbar(request,response);break;
		case("filter"):filterbar(request,response);break;
		default : response.sendRedirect("Main.jsp");break;
		}
	}catch(Exception e) {
		out.println(e);
	}
	}
	private void insertion (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		String NSer = request.getParameter("N°SerC");
		String ref = request.getParameter("RefC");
		String DesM = request.getParameter("DesC");
		String dateM = request.getParameter("DateC");
		Date dateSQL = Date.valueOf(dateM); 
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq= "INSERT INTO composant (n°serie_cmp , reference_cmp, designation_cmp  , date_entree_cmp ) values (?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(rq);
			pstmt.setString(1, NSer);
			pstmt.setString(2, ref);
			pstmt.setString(3, DesM);
			pstmt.setDate(4, dateSQL);
			pstmt.executeUpdate();
		    conn.close();
			Lister(request,response);
		}catch( Exception e  ) {
			out.println("erreur: "+e);
		}
	}
	private void  updateData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String NSer = request.getParameter("UN°SerC");
		String Aser = request.getParameter("AUN°SerC");
		String ref = request.getParameter("URefC");
		String DesM = request.getParameter("UDesC");
		String Etat = request.getParameter("UEtatC");
		String dateM = request.getParameter("UDateEC");
		String mac = request.getParameter("UMacC");
		Date dateSQL = Date.valueOf(dateM); 

		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq= "UPDATE composant SET n°serie_cmp = ? , reference_cmp = ? , designation_cmp = ? , etat_cmp = ? , date_entree_cmp = ? , machine = ? WHERE n°serie_cmp = ?";
			PreparedStatement  stat = conn.prepareStatement(rq);
			stat.setString(1 , NSer);
			stat.setString(2 , ref);
			stat.setString(3, DesM);
			stat.setString(4,Etat);
			stat.setDate(5, dateSQL);
			stat.setString(6,mac);
			stat.setString(7, Aser);
			stat.executeUpdate();
			Lister(request,response);
		}catch(Exception e){
			out.println(e);
		}
		
		}
	private void  deleteData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String data = request.getParameter("rowIndex");
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq= "DELETE FROM composant WHERE n°serie_cmp=\'"+data+"\'";
			Statement  stat = conn.createStatement();
			stat.execute(rq);
			Lister(request,response);
		}catch(Exception e){
			out.println(e);
		}

	  }
	private void searchbar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String search=request.getParameter("barsearch");
		try {

			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT * FROM composant WHERE n°serie_cmp LIKE  ?  OR reference_cmp LIKE ? OR designation_cmp LIKE ? OR machine LIKE ? ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stat.setString(1, "%"+search+"%");
			stat.setString(2, "%"+search+"%");
			stat.setString(3, "%"+search+"%");
			stat.setString(4, "%"+search+"%");
			ResultSet rs = stat.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			rs.last();
		    int numRows = rs.getRow();
		    
		    String[][] noms = new String[columnCount][numRows];
			String[] header = new String[columnCount];
			String[] headerK = new String[columnCount];
			
			for (int i = 0; i < columnCount; i++) {
		        String columnName = metaData.getColumnName(i+1);
		        String[] parts = columnName.split("_");
		        headerK[i] = columnName;
		            if (parts.length > 0) {
		            header[i] = parts[0];
		            } else {
		            header[i] = columnName;
		        }
		        int j = 0;
		        rs.beforeFirst();
			while (rs.next()) {	
				noms[i][j] = rs.getString(headerK[i]);
				out.println(noms[i][j]);
				j++;
			}  }
			  request.setAttribute("col",header);
			  request.setAttribute("coll",headerK);
			  request.setAttribute("noms", noms);
			rs.close();
			stat.close();
			conn.close();
		}catch(Exception e) {
			out.println(e);
		}
		
		request.getRequestDispatcher("composant.jsp").forward(request, response);
	}
	private void filterbar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String search=request.getParameter("serie");
		String categorie=request.getParameter("Categorie");
		String etat=request.getParameter("Etat");
		String datedeb = request.getParameter("dated");
		String datefn = request.getParameter("datef");
		Date dateSQL = Date.valueOf(datedeb);
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT * FROM composant WHERE (n°serie_cmp =  ? ) AND (date_entrée_cmp BETWEEN ? AND ? ) AND (designation_cmp = ? ) AND ( etat_cmp =? ) ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stat.setString(1, search);
			stat.setDate(2, dateSQL);
			stat.setDate(3, Date.valueOf(datefn));
			stat.setString(4, categorie);
			stat.setString(5, etat);
			ResultSet rs = stat.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			rs.last();
		    int numRows = rs.getRow();
		    
		    String[][] noms = new String[columnCount][numRows];
			String[] header = new String[columnCount];
			String[] headerK = new String[columnCount];
			
			for (int i = 0; i < columnCount; i++) {
		        String columnName = metaData.getColumnName(i+1);
		        String[] parts = columnName.split("_");
		        headerK[i] = columnName;
		            if (parts.length > 0) {
		            header[i] = parts[0];
		            } else {
		            header[i] = columnName;
		        }
		        int j = 0;
		        rs.beforeFirst();
			while (rs.next()) {	
				noms[i][j] = rs.getString(headerK[i]);
				j++;
			}  }
			  request.setAttribute("col",header);
			  request.setAttribute("coll",headerK);
			  request.setAttribute("noms", noms);
			rs.close();
			stat.close();
			conn.close();
		}catch(Exception e) {
			out.println(e);
		}
		
		request.getRequestDispatcher("composant.jsp").forward(request, response);
	}
	public void Lister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		Agences AgServlet = new Agences();
		Materielcrud matserv = new Materielcrud();
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT * FROM  composant ORDER BY designation_cmp ,  n°serie_cmp ASC ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stat.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			rs.last();
		    int numRows = rs.getRow();
		    
		    String[][] noms = new String[columnCount][numRows];
			String[] header = new String[columnCount];
			String[] headerK = new String[columnCount];
			
			for (int i = 0; i < columnCount; i++) {
		        String columnName = metaData.getColumnName(i+1);
		        String[] parts = columnName.split("_");
		        headerK[i] = columnName;
		            if (parts.length > 0) {
		            header[i] = parts[0];
		            } else {
		            header[i] = columnName;
		        }
		        int j = 0;
		        rs.beforeFirst();
			while (rs.next()) {	
				noms[i][j] = rs.getString(headerK[i]);
				j++;
			}  }
			  request.setAttribute("col",header);
			  request.setAttribute("noms", noms);

			rs.close();
			stat.close();
			conn.close();
			AgServlet.listagences(request,response);
			categfiltre(request,response);
			etatfiltre(request,response);
			matserv.listemac(request,response);
		}catch(Exception e) {
			out.println(e);
		}
		
		request.getRequestDispatcher("composant.jsp").forward(request, response);
}

public void etatfiltre  (HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT DISTINCT etat_cmp FROM composant";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stat.executeQuery();

			int rowCount = 0;
		     while (rs.next()) {
		          rowCount++;
		    }

		      rs.beforeFirst();
		            
		      String [] designationArray = new String[rowCount];
		            
		       int index = 0;
		        while (rs.next()) {
		              String designation = rs.getString("etat_mat");
		              designationArray[index] = designation;
		              index++;
		          }
		           request.setAttribute("etat", designationArray);
		          rs.close();
		          stat.close();
		          conn.close();
	    } catch (Exception e) {
	    	out.println(e);
	    }	
	}
	public void categfiltre  (HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT DISTINCT designation_cmp FROM composant";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stat.executeQuery();

			int rowCount = 0;
		     while (rs.next()) {
		          rowCount++;
		    }

		      rs.beforeFirst();
		            
		      String [] designationArray = new String[rowCount];
		            
		       int index = 0;
		        while (rs.next()) {
		              String designation = rs.getString("designation_mat");
		              designationArray[index] = designation;
		              index++;
		          }
		           request.setAttribute("options", designationArray);
		          rs.close();
		          stat.close();
		          conn.close();
	    } catch (Exception e) {
	    	out.println(e);
	    }	
	}

}
