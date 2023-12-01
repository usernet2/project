package info;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Info extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    String mac = request.getParameter("mac");
		if(mac != null){
			designation(request,response,mac);
		}
}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	public String listinfo(HttpServletRequest request, HttpServletResponse response , String mac , String des) throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		 String reference = "";
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT reference_cmp  FROM composant WHERE machine = ? AND designation_cmp = ? ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stat.setString(1,mac);
			stat.setString(2,des);
			ResultSet rs = stat.executeQuery();
            while (rs.next()) {
               reference = rs.getString("reference_cmp");
            }
			rs.close();
			stat.close();
			conn.close();
			
		}catch(Exception e) {
			out.println(e);
		}
		return reference;
	}
	public String infopc(HttpServletRequest request, HttpServletResponse response , String mac) throws ServletException, IOException{
		response.setContentType("text/html");
		String cm = listinfo(request,response,mac,"CM");
		String cpu = listinfo(request,response,mac,"CPU");
		String rom = listinfo(request,response,mac,"DISQUE-DUR");
		String ram = listinfo(request,response,mac,"RAM");
		String alim = listinfo(request,response,mac,"ALIMENTATION");
		String info = cm + "," + cpu + "," + rom + "," + ram + "" + alim;
		return info;
	}public String serpc(HttpServletRequest request, HttpServletResponse response , String rrep) throws ServletException, IOException{
		response.setContentType("text/html");
		String cm = listser(request,response,rrep,"CM");
		String cpu = listser(request,response,rrep,"CPU");
		String rom = listser(request,response,rrep,"DISQUE-DUR");
		String ram = listser(request,response,rrep,"RAM");
		String alim = listinfo(request,response,rrep,"ALIMENTATION");
		String ser = cm + "," + cpu + "," + rom + "," + ram + "" +alim;
		return ser;
	}
	public String infoprinter(HttpServletRequest request, HttpServletResponse response , String mac) throws ServletException, IOException{
		response.setContentType("text/html");
		String toner = listinfo(request,response,mac,"TONER");
		String tambour = listinfo(request,response,mac,"TAMBOUR");
		String tete = listinfo(request,response,mac,"TETE");
		String miroir  = listinfo(request,response,mac,"MIROIR");
		String memoire  = listinfo(request,response,mac,"MEMOIRE VIVE");
		String info = toner + "," + tambour + "," + tete + "," + miroir+ "," + memoire;
		return info;
	}
	public String serprinter(HttpServletRequest request, HttpServletResponse response , String rrep) throws ServletException, IOException{
		response.setContentType("text/html");
		String toner = listser(request,response,rrep,"TONER");
		String tambour = listser(request,response,rrep,"TAMBOUR");
		String tete = listser(request,response,rrep,"TETE");
		String miroir  = listser(request,response,rrep,"MIROIR");
		String memoire  = listser(request,response,rrep,"MEMOIRE VIVE");
		String ser = toner + "," + tambour + "," + tete + "," + miroir+ "," + memoire;
		return ser;
	}
	public String listser(HttpServletRequest request, HttpServletResponse response , String mac , String des) throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		 String reference = "";
		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT n°serie_cmp  FROM composant WHERE machine = ? AND designation_cmp = ? ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stat.setString(1,mac);
			stat.setString(2,des);
			ResultSet rs = stat.executeQuery();
            while (rs.next()) {
               reference = rs.getString("n°serie_cmp");
            }
			rs.close();
			stat.close();
			conn.close();
			
		}catch(Exception e) {
			out.println(e);
		}
		return reference;
	}
	
	public void composant_mac(HttpServletRequest request, HttpServletResponse response, String ser, String ref, String des, Date date, String mac ) throws ServletException, IOException {
	    response.setContentType("text/html");
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
	    if (ser != null && !ser.isEmpty() &&
	    	    ref != null && !ref.isEmpty() &&
	    	    des != null && !des.isEmpty() &&
	    	    date != null &&
	    	    mac != null && !mac.isEmpty()) {
	    Connection conn = null;
	    try {
	        Class.forName("org.postgresql.Driver");
	        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "n");

	        String checkQuery = "SELECT * FROM composant WHERE n°serie_cmp = ? ";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setString(1, ser);
	        ResultSet resultSet = checkStmt.executeQuery();

	        if (resultSet.next()) {
	            String updateQuery = "UPDATE composant SET machine = ? WHERE n°serie_cmp = ? AND machine = ? ";
	            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
	            updateStmt.setString(1, mac);
	            updateStmt.setString(2, ser);
	            updateStmt.setString(3, mac);
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
	    }}
	}
	private void designation (HttpServletRequest request, HttpServletResponse response ,String serie) throws IOException {
		String value = null ;
	    PrintWriter out = response.getWriter();

		try {
			Class.forName("org.postgresql.Driver");
			Connection	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","n");
			String rq = "SELECT designation_mat FROM  materiel  WHERE  n°serie_mat = ? ";
			PreparedStatement stat = conn.prepareStatement(rq, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stat.setString(1, serie);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				 value = rs.getString("designation_mat");
				}
			if (value.startsWith("PC") ) {
			String infoR = infopc(request,response,serie) ;
			String infoS = serpc(request,response,serie);
			String info = infoR + ","+ infoS ;
			out.println(info);
			}else if (value.startsWith("IMPRIMANTE") ) {
				String infoR = infoprinter(request,response,serie) ;
				String infoS = serprinter(request,response,serie);
				String info = infoR + ","+ infoS ;
				out.println(info);}
		}catch( Exception e  ) {
			e.printStackTrace();
		}

	}
}
