package materielcrud;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import info.Info;

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

/**
 * Servlet implementation class Materielcrud
 */
public class Materielcrud extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		String op  = request.getParameter("action");
		if (op != null) {
			selection(request, response,op);
			}else {
			response.sendRedirect("materel.jsp");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	private void selection(HttpServletRequest request, HttpServletResponse response, String oper) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		try  {
		switch (oper) {
		case("Liste"):Lister(request,response);break;
		case("Update"):updateData(request,response);break;
		case("Delete"):deleteData(request,response);break;
		case("filter"):filterbar(request,response);break;
		case("insertion"):insertion(request,response);break;
		case("select"):searchbar(request,response);break;
		default : response.sendRedirect("materiel.jsp");break;
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
		String NSer = request.getParameter("N°SerM");
		String ref = request.getParameter("RefM");
		String DesM = request.getParameter("Dropdes");
		String Etat = request.getParameter("EtatM");
		String location = request.getParameter("location");
		String dateM = request.getParameter("DateEM");
		Date dateSQL = Date.valueOf(dateM);
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq= "INSERT INTO materiel (n°serie_mat , reference_mat, designation_mat , etat_mat , date_entrée_mat ,agence) values (?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(rq);
			pstmt.setString(1, NSer);
			pstmt.setString(2, ref);
			pstmt.setString(3, DesM);
			pstmt.setString(4, Etat);
			pstmt.setDate(5, dateSQL);
			pstmt.setString(6, location);
			pstmt.executeUpdate();
		    conn.close();
		    if(DesM.startsWith("PC") ) {
		    	insert(request,response);
		    }else if (DesM.startsWith("IMPRIMANTE") ) {
		    	printercmp(request,response);	
		    }else if ("ONDULEUR".equals(DesM)) {
		    	
		    }
			Lister(request,response);
		}catch( Exception e  ) {
			out.println("erreur : "+e);
		}
	}
	public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		Info LoginServlet = new Info();
		PrintWriter out= response.getWriter();
	    String imac =request.getParameter("N°SerM");
	 	String dateM = request.getParameter("DateEM");
		Date dateSQL = Date.valueOf(dateM);  
		out.println(imac +  dateM);
	    String icm = request.getParameter("Scm");
		String rcm = request.getParameter("Rcm");
		LoginServlet.composant_mac(request, response , icm, rcm , "CM" , dateSQL , imac );
		String icpu = request.getParameter("Scpu");
		String rcpu = request.getParameter("Rcpu");
		LoginServlet.composant_mac(request, response , icpu , rcpu , "CPU" , dateSQL , imac );
		String iram = request.getParameter("Sram");
		String rram = request.getParameter("Rram");
		LoginServlet.composant_mac(request, response , iram , rram , "RAM" , dateSQL , imac  );
		String irom = request.getParameter("Srom");
		String rrom = request.getParameter("Rrom");
		LoginServlet.composant_mac(request, response , irom , rrom , "DISQUE-DUR" , dateSQL , imac );
		String ialim = request.getParameter("Salim");
		String ralim = request.getParameter("Ralim");
		LoginServlet.composant_mac(request, response , ialim , ralim , "ALIMENTATION" , dateSQL , imac );
	
	}
	public void printercmp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		Info LoginServlet = new Info();
		PrintWriter out= response.getWriter();
	    String imac =request.getParameter("N°SerM");
	 	String dateM = request.getParameter("DateEM");
		Date dateSQL = Date.valueOf(dateM);  
		out.println(imac +  dateM);
	    String ston = request.getParameter("SToner");
		String rton = request.getParameter("RToner");
		LoginServlet.composant_mac(request, response , ston, rton , "TONER" , dateSQL , imac );
		String stam = request.getParameter("STambour");
		String rtam = request.getParameter("RTambour");
		LoginServlet.composant_mac(request, response , stam , rtam , "TAMBOUR" , dateSQL , imac );
		String stete = request.getParameter("STete");
		String rtete = request.getParameter("RTete");
		LoginServlet.composant_mac(request, response , stete , rtete , "TETE" , dateSQL , imac  );
		String smir = request.getParameter("SMiroir");
		String rmir = request.getParameter("RMiroir");
		LoginServlet.composant_mac(request, response , smir , rmir , "MIROIR" , dateSQL , imac );
		String smemv = request.getParameter("SmemV");
		String rmemv = request.getParameter("RmemV");
		LoginServlet.composant_mac(request, response , smemv , rmemv , "MEMOIRE VIVE" , dateSQL , imac );
	
	}
	public void onduleurcmp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		Info LoginServlet = new Info();
		PrintWriter out= response.getWriter();
	    String imac =request.getParameter("N°SerM");
	 	String dateM = request.getParameter("DateEM");
		Date dateSQL = Date.valueOf(dateM);  
		out.println(imac +  dateM);
	    String sbat = request.getParameter("Sbatt");
		String rbat = request.getParameter("Rbatt");
		LoginServlet.composant_mac(request, response , sbat, rbat , "Batterie" , dateSQL , imac );
	}
	private void  updateData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String NSer = request.getParameter("UNSerM");
		String Aser = request.getParameter("AUNSerM");
		String ref = request.getParameter("URefM");
		String DesM = request.getParameter("UDropdes");
		String Etat = request.getParameter("UEtatM");
		String location = request.getParameter("Ulocation");
		String dateM = request.getParameter("UDateEM");
		Date dateSQL = Date.valueOf(dateM); 

		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq= "UPDATE materiel SET n°serie_mat = ? , reference_mat = ? , designation_mat = ? , etat_mat = ? , date_entrée_mat = ? , agence = ?  WHERE n°serie_mat = ?";
			PreparedStatement  stat = conn.prepareStatement(rq);
			stat.setString(1 , NSer);
			stat.setString(2 , ref);
			stat.setString(3, DesM);
			stat.setString(4,Etat);
			stat.setDate(5, dateSQL);
			stat.setString(6, location);
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
			String rq= "DELETE FROM materiel WHERE n°serie_mat=\'"+data+"\'";
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
			String rq = "SELECT * FROM materiel WHERE n°serie_mat LIKE  ?  OR reference_mat LIKE ? OR designation_mat LIKE ? OR agence LIKE ? ";
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
			categfiltre(request,response);
			etatfiltre(request,response);
		}catch(Exception e) {
			out.println(e);
		}
		
		request.getRequestDispatcher("materiel.jsp").forward(request, response);
	}
	private void filterbar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String categorie=request.getParameter("Categorie");
		String etat=request.getParameter("Etat");
		String datedeb = request.getParameter("dated");
		String datefn = request.getParameter("datef");
		Date dateSQL = Date.valueOf(datedeb);
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT * FROM materiel WHERE  (date_entrée_mat BETWEEN ? AND ? ) AND (designation_mat = ? ) AND ( etat_mat =? ) ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stat.setDate(1, dateSQL);
			stat.setDate(2, Date.valueOf(datefn));
			stat.setString(3, categorie);
			stat.setString(4, etat);
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
			categfiltre(request,response);
			etatfiltre(request,response);
		}catch(Exception e) {
			out.println(e);
		}
		
		request.getRequestDispatcher("materiel.jsp").forward(request, response);
	}
	public void Lister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		Agences AgServlet = new Agences();
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT * FROM materiel  ORDER BY designation_mat ,  n°serie_mat ASC ";
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
				//out.println(noms[i][j]);
				j++;
			}  }
			  request.setAttribute("col",header);
			  request.setAttribute("coll",headerK);
			  request.setAttribute("noms", noms);
			rs.close();
			stat.close();
			conn.close();
			AgServlet.listagences(request,response);
			categfiltre(request,response);
			etatfiltre(request,response);
			listemac(request,response);
		}catch(Exception e) {
			out.println(e);
		}
		
		request.getRequestDispatcher("materiel.jsp").forward(request, response);
}
	
	
	public void etatfiltre  (HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT DISTINCT etat_mat FROM materiel";
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
	public void listemac  (HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT n°serie_mat FROM materiel";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stat.executeQuery();

			int rowCount = 0;
		     while (rs.next()) {
		          rowCount++;
		    }

		      rs.beforeFirst();
		      String[] machine = new String[rowCount] ;     
		      int i=0;
		        while (rs.next()) {
		             String designation = rs.getString("n°serie_mat");
		             machine[i] = designation;	
		             i++;
		        }
		          request.setAttribute("lsmac", machine);
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
			String rq = "SELECT DISTINCT designation_mat FROM materiel";
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

