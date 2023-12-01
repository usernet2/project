package agences;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import materielcrud.Materielcrud;
import transfert.Transfert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;








public class Agences extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String op  = request.getParameter("action");
		if (op != null ){
		selection(request, response,op);}
		String ag =request.getParameter("agence");
		listamac(request,response,ag);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    String action = request.getParameter("action");
	    if("Liste".equals(action)) {
	    	agenceslist(request,response);
	    }else {
	    		response.getWriter().println(action);
	    }
	}
	private void selection(HttpServletRequest request, HttpServletResponse response , String op) throws ServletException, IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		Transfert tran = new Transfert();
		try  {
		switch (op) {
		case("Liste"):agenceslist(request,response);;break;
		case("Transfert"):tran.lister(request,response);break;
		case("select"):searchbar(request,response);break;
		default : response.sendRedirect("reparation.jsp");break;
		}
		}catch(Exception e) {
		out.println(e);
		}
	}
	public void agenceslist (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		Materielcrud matserv = new Materielcrud();
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT * FROM agences ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stat.executeQuery();
            int rowCount = 0;
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            String[] noms = new String[rowCount];

            int count = 0;
            while (rs.next()) {
                noms[count] = rs.getString("nom");
                count++;
            }
            String[] occurrenceCounts = new String[rowCount];
            for (int i = 0; i < rowCount; i++) {
                String nom = noms[i];
                int occurrences = nombre(nom, conn);
                String nbr = Integer.toString(occurrences);
                occurrenceCounts[i] = nbr ;
            }
            rs.close();
            rs.close();
            rs.close();
            request.setAttribute("noms", noms);
            request.setAttribute("nbrmac", occurrenceCounts);
            listagences(request,response);
            matserv.listemac(request,response);
    		request.getRequestDispatcher("agences.jsp").forward(request, response);
        } catch (Exception e) {
            out.println(e);
        }
	}
	private int nombre(String nom, Connection conn) throws SQLException {
	    int count = 0;
	    String countQuery = "SELECT COUNT(*) AS count FROM materiel WHERE agence = ?";
	    PreparedStatement countStmt = conn.prepareStatement(countQuery);
	    countStmt.setString(1, nom);

	    ResultSet countRs = countStmt.executeQuery();
	    if (countRs.next()) {
	        count = countRs.getInt("count");
	    }

	    countRs.close();
	    countStmt.close();

	    return count;
	   }

	public void listamac(HttpServletRequest request, HttpServletResponse response, String agence) throws ServletException, IOException {
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();

	    try {
	        Class.forName("org.postgresql.Driver");
	        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "n");
	        String rq = "SELECT * FROM materiel WHERE agence LIKE ?";
	        PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        stat.setString(1, agence);
	        ResultSet rs = stat.executeQuery();

	        StringBuilder jsonArray = new StringBuilder("[");
	        boolean first = true;

	        while (rs.next()) {
	            if (!first) {
	                jsonArray.append(",");
	            } else {
	                first = false;
	            }

	            jsonArray.append("{");

	            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
	                String columnName = rs.getMetaData().getColumnName(i);
	                String value = rs.getString(i);

	                if (i > 1) {
	                    jsonArray.append(",");
	                }

	                jsonArray.append("\"").append(columnName).append("\":\"").append(value).append("\"");
	            }

	            jsonArray.append("}");
	        }

	        jsonArray.append("]");

	        out.println( jsonArray.toString());

	        rs.close();
	        stat.close();
	        conn.close();
	    } catch (Exception e) {
	        out.println(e);
	    }
	}

	
public void listagences(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
	PrintWriter out= response.getWriter();
	try {
		Class.forName("org.postgresql.Driver");
		Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
		String rq = "SELECT nom FROM agences ";
		PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stat.executeQuery();
        int rowCount = 0;
        if (rs.last()) {
            rowCount = rs.getRow();
            rs.beforeFirst();
        }
        String[] noms = new String[rowCount];
        int count = 0;
        while (rs.next()) {
            noms[count] = rs.getString("nom");
            count++;
        }
        rs.close();
        rs.close();
        rs.close();
        request.setAttribute("agences", noms);
//		request.getRequestDispatcher("agences.jsp").forward(request, response);
    } catch (Exception e) {
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
		String rq = "SELECT * FROM agences WHERE code LIKE  ?  OR nom LIKE ? ";
		PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stat.setString(1, "%"+search+"%");
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
	
	request.getRequestDispatcher("agences.jsp").forward(request, response);
}
}
