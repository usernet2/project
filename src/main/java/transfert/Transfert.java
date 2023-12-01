package transfert;

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
import agences.Agences;

/**
 * Servlet implementation class Transfert
 */
public class Transfert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		String op  = request.getParameter("action");
		if (op != null) {
			selection(request, response);
			}else {
			response.sendRedirect("reparation.jsp");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	private void selection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		String op  = request.getParameter("action");
		try  {
		switch (op) {
		case("Transfert"):lister(request,response);break;
		case("Update"):update(request,response);break;
		case("insertion"):insert(request,response);break;
		case("select"):searchbar(request,response);break;
		case("Delete"):deletetransfert(request,response);break;
		default : response.sendRedirect("reparation.jsp");break;
		}
		}catch(Exception e) {
		out.println(e);
		}
	}
	public void lister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		Agences AgServlet = new Agences();
		Materielcrud matserv = new Materielcrud();
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT * FROM  transfert  ";
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
		
		request.getRequestDispatcher("transfert.jsp").forward(request, response);

	    }
	private void deletetransfert(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String data = request.getParameter("rowIndex");
	    int ide = Integer.parseInt(data);
	    String mac = request.getParameter("machine");
	    String ag = request.getParameter("agence");
	    String date = request.getParameter("date");
	    Agences AgServlet = new Agences();
	    Materielcrud matserv = new Materielcrud();

	    try {
	        Class.forName("org.postgresql.Driver");
	        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "n");
	        String deleteQuery = "DELETE FROM transfert WHERE id_trans = ?";
            PreparedStatement statDelete = conn.prepareStatement(deleteQuery);
            statDelete.setInt(1, ide);
            statDelete.executeUpdate();
            if (lastdate(conn, mac, Date.valueOf(date) )) {
 	           setagence(conn , ag , mac);
	        } 
	        AgServlet.listagences(request, response);
            matserv.listemac(request, response);
            lister(request, response);
	    } catch (Exception e) {
	        out.println(e);
	    }
	}

public String expedi(String mac) {
	String value = null ;
	try {
		Class.forName("org.postgresql.Driver");
		Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
		String rq = "SELECT agence FROM  materiel  WHERE  n°serie_mat = ? ";
		PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stat.setString(1, mac);
		ResultSet rs = stat.executeQuery();
		while(rs.next()) {
			 value = rs.getString("agence");
		}
	}catch( Exception e  ) {
		e.printStackTrace();
	}
	return value;
}
private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException {
	request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	String date = request.getParameter("DateT"); 
	String obj = request.getParameter("TMateriel");
	String des = request.getParameter("IDes");
	String exp = expedi(obj);
	String mot = request.getParameter("motifT");
	Agences AgServlet = new Agences();
	Materielcrud matserv = new Materielcrud();
	try {
		Class.forName("org.postgresql.Driver");
		Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
		String rq = "INSERT INTO transfert (date_trans , objet , expediteur , destinataire , motif) values (?,?,?,?,?)";
		PreparedStatement stat = conn.prepareStatement(rq);
		stat.setDate(1, Date.valueOf(date));
		stat.setString(2, obj);
		stat.setString(3, exp);
		stat.setString(4, des);
		stat.setString(5, mot);
        if (lastdate(conn, obj, Date.valueOf(date))) {
	    setagence(conn , des , obj);
		stat.executeUpdate();
		}
	    conn.close();
		AgServlet.listagences(request,response);
		matserv.listemac(request,response);
		lister(request,response);
		}catch(Exception e){
		out.println(e);
	}
  }
private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
	request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	String idrep = request.getParameter("UTid");
	int id = Integer.parseInt(idrep);
	String date = request.getParameter("UDateT"); 
	String obj = request.getParameter("UTMateriel");
	String exp = request.getParameter("UIExp");
	String des = request.getParameter("UIDes");
	String mot = request.getParameter("UmotifT");
	Agences AgServlet = new Agences();
	Materielcrud matserv = new Materielcrud();
	try {
		Class.forName("org.postgresql.Driver");
		Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
		String rq = "UPDATE transfert date_trans = ? , objet = ? , expediteur = ?, destinataire = ? , motif = ? WHERE id_trans = ?";
        PreparedStatement stat = conn.prepareStatement(rq);
		stat.setDate(1, Date.valueOf(date));
		stat.setString(2, obj);
		stat.setString(3, exp);
		stat.setString(4, des);
		stat.setString(5, mot);
		stat.setInt(6, id);
		stat.executeUpdate();
        if (lastdate(conn, obj, Date.valueOf(date))) {
	           setagence(conn , des , obj);
	        } 
	    conn.close();
		AgServlet.listagences(request,response);
		matserv.listemac(request,response);
		lister(request,response);
		}catch(Exception e){
		out.println(e);
	}
  }
private boolean lastdate(Connection conn, String objet, Date dateTrans) throws SQLException {
    String query = "SELECT MAX(date_trans) FROM transfert WHERE objet = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, objet);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Date maxDate = rs.getDate(1);
               if (maxDate == null) {
                    return true; 
                }
                return dateTrans.after(maxDate);
            }
        }
    }
    return false; 
}

private void setagence(Connection conn, String agence, String numeroSerieMat) throws SQLException {
    String updateQuery = "UPDATE materiel SET agence = ? WHERE n°serie_mat = ?";
    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
        updateStmt.setString(1, agence);
        updateStmt.setString(2, numeroSerieMat);
        updateStmt.executeUpdate();
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
		String rq = "SELECT * FROM transfert WHERE objet LIKE  ?  OR expediteur LIKE ? OR destinataire LIKE ? OR motif LIKE ? ";
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
	
	request.getRequestDispatcher("transfert.jsp").forward(request, response);
}

}
