package reparation;

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
import java.sql.SQLException;
import java.sql.Statement;

import agences.Agences;

/**
 * Servlet implementation class Reparation
 */
public class Reparation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		String op  = request.getParameter("action");
		String mac = request.getParameter("serie");
		if (op != null) {
			selection(request, response);
			}else if(mac != null){
				designation (request, response,mac) ;
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	}
	private void selection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		String op  = request.getParameter("action");
		try  {
		switch (op) {
		case("Liste"):lister(request,response);break;
		case("Update"):updateData(request,response);break;
		case("insertion"):insertion(request,response);break;
		case("reparerpc"):pcrepar(request,response);break;
		case("reparerprinter"):printerrepar(request,response);break;
		case("select"):searchbar(request,response);break;
		case("Delete"):deleteData(request,response);break;
		default : response.sendRedirect("reparation.jsp");break;
		}
		}catch(Exception e) {
		out.println(e);
		}
	}
	public void lister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		Materielcrud matserv = new Materielcrud();
		Agences AgServlet = new Agences();
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT * FROM  reparation  ORDER BY  debut_rep ASC ";
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
			matserv.listemac(request,response);
		}catch(Exception e) {
			out.println(e);
		}
		
		request.getRequestDispatcher("reparation.jsp").forward(request, response);

	}	
	private void insertion (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		String arr = request.getParameter("DateAR");
		String deb = request.getParameter("DebutR");
		Date DebR =Date.valueOf(deb);
		String Mac = request.getParameter("Materiel");
		String Refmac = references(Mac);
		String Resp = request.getParameter("Respbl");
		String Pblm = request.getParameter("Pblm");
		String Solt = request.getParameter("solt");
		String fin = request.getParameter("FinR");
		Date finR = null;
		if (fin != null) {
			 finR = Date.valueOf(fin);
		}
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq= "INSERT INTO reparation (date_arrivee, debut_rep , serie_machine , references_machine, observation , responsable , solution ,fin_rep) values (?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(rq);
			pstmt.setDate(1, Date.valueOf(arr));
			pstmt.setDate(2, DebR);
			pstmt.setString(3, Mac);
			pstmt.setString(4, Refmac);
			pstmt.setString(5, Pblm);
			pstmt.setString(6, Resp);
			pstmt.setString(7, Solt);
			pstmt.setDate(8, finR);
			pstmt.executeUpdate();
		    conn.close();
		   lister(request,response);
		}catch( Exception e  ) {
			out.println("erreur : "+e);
		}
	}
	private void  deleteData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String data = request.getParameter("rowIndex");
		int idata = Integer.parseInt(data);
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq= "DELETE FROM reparation WHERE id_rep=\'"+idata+"\'";
			Statement  stat = conn.createStatement();
			stat.execute(rq);
			lister(request,response);
		}catch(Exception e){
			out.println(e);
		}

	  }
	private void updateData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		String idrep = request.getParameter("Uid");
		int id = Integer.parseInt(idrep);
		String arr = request.getParameter("UDateAR");
		String deb = request.getParameter("UDebutR");
		Date DebR = Date.valueOf(deb);
		String Mac = request.getParameter("UMateriel");
		String Refmac = references(Mac);
		String Resp = request.getParameter("URespbl");
		String Pblm = request.getParameter("UPblm");
		String Solt = request.getParameter("Usolt");
		String fin = request.getParameter("UFinR");
		Date finR = null;
		if (fin != null) {
			 finR = Date.valueOf(fin);
		}
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq= "UPDATE reparation SET date_arrivee = ?, debut_rep = ? , serie_machine = ?, references_machine = ? , observation = ? , responsable = ?, solution = ? , fin_rep = ? WHERE id_rep = ?";
			PreparedStatement pstmt = conn.prepareStatement(rq);
			pstmt.setDate(1, Date.valueOf(arr));
			pstmt.setDate(2, DebR);
			pstmt.setString(3, Mac);
			pstmt.setString(4, Refmac);
			pstmt.setString(5, Pblm);
			pstmt.setString(6, Resp);
			pstmt.setString(7, Solt);
			pstmt.setDate(8, finR);
			pstmt.setInt(9, id);
			pstmt.executeUpdate();
		    conn.close();
		   lister(request,response);
		}catch( Exception e  ) {
			out.println("erreur : "+e);
		}
	}
	private String references (String serie) {
		String value = null;
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT reference_mat FROM  materiel  WHERE  n°serie_mat = ? ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stat.setString(1, serie);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				 value = rs.getString("reference_mat");
				return value;
			}
		}catch( Exception e  ) {
			e.printStackTrace();
		}
		return value ;
	}
	private void designation (HttpServletRequest request, HttpServletResponse response , String serie) throws ServletException, IOException  {
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT designation_mat FROM  materiel  WHERE  n°serie_mat = ? ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stat.setString(1, serie);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				String value = rs.getString("designation_mat");
				response.getWriter().println(value) ;
			}
		}catch( Exception e  ) {
			e.printStackTrace();
		}
	
	}
	public void printerrepar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    String imac =request.getParameter("infomach");
	 	String dateM = request.getParameter("datei");
		Date dateSQL = Date.valueOf(dateM);  
	    String iton = request.getParameter("SRepToner");
		String rton = request.getParameter("RepToner");
		String aton = request.getParameter("HRepToner");
		reparermac(request, response , iton, rton , "TONER" , dateSQL , imac ,aton );
		String itam = request.getParameter("SRepTambour");
		String rtam = request.getParameter("RepTambour");
		String atam = request.getParameter("HRepTambour");
		reparermac(request, response , itam , rtam , "TAMBOUR" , dateSQL , imac, atam );
		String itete = request.getParameter("SRepTete");
		String rtete = request.getParameter("RepTete");
		String atete = request.getParameter("HRepTete");
		reparermac(request, response , itete , rtete , "TETE" , dateSQL , imac, atete );
		String imir = request.getParameter("SRepMiroir");
		String rmir = request.getParameter("RepMiroir");
		String amir = request.getParameter("HRepMiroir");
		reparermac(request, response , imir , rmir , "MIROIR" , dateSQL , imac , amir);
		String imemv = request.getParameter("SRepmemV");
		String rmemv = request.getParameter("RepmemV");
		String amemv = request.getParameter("HRepmemV");
		reparermac(request, response , imemv , rmemv , "MEMOIRE VIVE" , dateSQL , imac , amemv);
		lister(request,response);
	}	
	public void pcrepar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    String imac =request.getParameter("infomach");
	 	String dateM = request.getParameter("datei");
		Date dateSQL = Date.valueOf(dateM);  
	    String icm = request.getParameter("SRepCm");
		String rcm = request.getParameter("RepCm");
		String acm = request.getParameter("HRepCm");
		reparermac(request, response , icm, rcm , "CM" , dateSQL , imac ,acm );
		String icpu = request.getParameter("SRepCpu");
		String rcpu = request.getParameter("RepCpu");
		String acpu = request.getParameter("HRepCpu");
		reparermac(request, response , icpu , rcpu , "CPU" , dateSQL , imac, acpu );
		String iram = request.getParameter("SRepRam");
		String rram = request.getParameter("RepRam");
		String aram = request.getParameter("HRepRam");
		reparermac(request, response , iram , rram , "RAM" , dateSQL , imac, aram );
		String rrom = request.getParameter("Repmem");
		String arom = request.getParameter("HRepmem");
		String irom = request.getParameter("SRepmem");
		reparermac(request, response , irom , rrom , "DISQUE-DUR" , dateSQL , imac , arom);
		String ralim = request.getParameter("Repalim");
		String aalim = request.getParameter("HRepalim");
		String ialim = request.getParameter("SRepalim");
		reparermac(request, response , ialim , ralim , "ALIMENTATION" , dateSQL , imac , aalim);
		lister(request,response);
	}
	public void ancienne ( String mac , String design) {
		Connection conn = null;
		try {
		        Class.forName("org.postgresql.Driver");
		        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "n");
		        String Ancienne = "UPDATE composant SET machine = ? , etat_cmp = 'Reparation' WHERE designation_cmp = ? AND machine = ? ";
	            PreparedStatement stat = conn.prepareStatement(Ancienne);
	            stat.setString(1, null);
	            stat.setString(2, design);
	            stat.setString(3, mac);
	            stat.executeUpdate();
		   } catch (Exception e) {
				e.printStackTrace();
		    } finally {
		        try {
		            if (conn != null) {
		                conn.close();
		            }
		        } catch (SQLException e) {
					e.printStackTrace();
		        }
		    }
	}
	
	public void reparermac(HttpServletRequest request, HttpServletResponse response, String ser, String ref, String des, Date date, String mac , String Aserie) throws ServletException, IOException {
	    response.setContentType("text/html");
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();

	    Connection conn = null;
	    try {
	        Class.forName("org.postgresql.Driver");
	        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "n");
	        ancienne(mac , des);
            
	        String checkQuery = "SELECT * FROM composant WHERE n°serie_cmp = ? ";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setString(1, ser);
	        ResultSet resultSet = checkStmt.executeQuery();

	        if (resultSet.next()) {
	            String updateQuery = "UPDATE composant SET machine = ? , etat_cmp = 'Active' WHERE n°serie_cmp = ? ";
	            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
	            updateStmt.setString(1, mac);
	            updateStmt.setString(2, ser);
	            updateStmt.executeUpdate();
	        } else {
	        	String insertQuery = "INSERT INTO composant (n°serie_cmp, reference_cmp, designation_cmp, etat_cmp, date_entree_cmp, machine) values (?, ?, ?, 'Active', ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
	            insertStmt.setString(1, ser);
	            insertStmt.setString(2, ref);
	            insertStmt.setString(3, des);
	            insertStmt.setDate(4, date);
	            insertStmt.setString(5, mac);
	            insertStmt.executeUpdate();
	        }
	    } catch (Exception e) {
	        out.println("Erreur : " + e);
	    } finally {
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            out.println("Erreur lors de la fermeture de la connexion : " + e);
	        }
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
			String rq = "SELECT * FROM reparation WHERE serie_machine LIKE  ?  OR references_machine LIKE ? OR observation LIKE ? OR responsable LIKE ? OR solution LIKE ? ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stat.setString(1, "%"+search+"%");
			stat.setString(2, "%"+search+"%");
			stat.setString(3, "%"+search+"%");
			stat.setString(4, "%"+search+"%");
			stat.setString(5, "%"+search+"%");
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
		
		request.getRequestDispatcher("reparation.jsp").forward(request, response);
	}
}
