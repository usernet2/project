<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@page import="java.sql.*" %>
    <%@page import="java.util.*" %>
<%
    HttpSession sesion = request.getSession(false); // Do not create a new session if none exists

    // Check if a valid session exists
    if (sesion == null || sesion.getAttribute("username") == null) {
        response.sendRedirect("Authetification.jsp");
    } else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel=stylesheet href="index.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<body>
<div class="container-fluid">
    <div class="row flex-nowrap">
        <div class="col-auto col-md-3 col-xl-2 px-sm-2 px-0 bg-dark">
            <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">
                <a href="Accueil.jsp" class="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                    <span class="fs-5 d-none d-sm-inline">Menu</span>
                </a>
                <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
                    <li class="nav-item">
                        <a href="#submenu1"  data-bs-toggle="collapse" class="nav-link px-0 align-middle">
                            <i class="fs-4 bi bi-pc-display"></i> <span class="ms-1 d-none d-sm-inline">Machine</span> </a>
                			   <form action="Materielcrud">
                   				<ul class="collapse hide nav flex-column ms-1" id="submenu1" data-bs-parent="#menu">
                           			 <li class="w-100">
                              			  <input type="submit" class="nav-link px-0" value="Liste" name="action">
                            		 </li>
                           			 <li>
                               			   <a onclick="MacForm()" class="nav-link px-0" >Ajouter</a>
                            		 </li>
                            		
                        		</ul> 
                        	   </form> 
                     </li>
                    	<li>
                        <a href="#submenu2"  data-bs-toggle="collapse"  class="nav-link px-0 align-middle">
                           <i class="fs-4 bi bi-cpu"></i> <span class="ms-1 d-none d-sm-inline">Composant</span> </a>
                  			 <form action="Composant" method="GET">      
                    		       <ul class="collapse hide nav flex-column ms-1" id="submenu2" data-bs-parent="#menu">
                           			<li class="w-100">
                              			  <input type="submit" class="nav-link px-0" value="Liste" name="action">
                            		 </li>
                           			 <li>
                               			   <a onclick="ComForm()" class="nav-link px-0" >Ajouter</a>
                            		 </li>
                            		
                        		</ul> 
                        	</form> 
                    </li>
                    	<li>
                        <a href="#submenu3" data-bs-toggle="collapse" class="nav-link px-0 align-middle">
                           <i class="fs-4 bi bi-buildings"></i> <span class="ms-1 d-none d-sm-inline">Agence</span> </a>
                  			 <form action="Agences" method="GET">      
                    		       <ul class="collapse hide nav flex-column ms-1" id="submenu3" data-bs-parent="#menu">
                           			<li class="w-100">
                              			  <input type="submit" class="nav-link px-0" value="Liste" name="action">
                            		 </li>
                           			 <li>
                              			  <input type="submit" class="nav-link px-0" value="Transfert" name="action">
                            		 </li>
                        		</ul> 
                        	</form> 
                    </li> 
                  <li>
                        <a href="#submenu4" data-bs-toggle="collapse" class="nav-link px-0 align-middle">
                        <input type="hidden" id="replink" value="repar">
                           <i class="fs-4 bi bi-wrench-adjustable-circle"></i> <span class="ms-1 d-none d-sm-inline">Reparation</span> </a>
                  			 <form action="Reparation" method="GET">      
                    		       <ul class="collapse hide nav flex-column ms-1" id="submenu4" data-bs-parent="#menu">
                           			<li class="w-100">
                              			  <input type="submit" class="nav-link px-0" value="Liste" name="action">
                            		 </li>
                           			 <li>
                               			   <a onclick="RepForm()" class="nav-link px-0" >Ajouter</a>
                            		 </li>
                        		</ul> 
                        	</form> 
                    </li> 
                </ul>
                <hr>
            </div>
        </div>
    <!-- SEARCH BAR	 -->
        <div class="col py-3">
        <form action="reparation" class="row" method="GET">
        <div class="col-sm-3"></div>
        <div class=" col-sm-7">  <input type="text" name="barsearch" id="form1" class="form-control" placeholder="Recherche" aria-label="Search" required /> </div>
        <div class="col-sm-2 "><button name="action" value="select" id="search-button" type="submit" class="btn btn-primary"> <i class="bi bi-search"></i>RECHERCHE</button></div>
        </form> <br>  
        <!-- TABLEAU  -->
          <table id="tablebd" class="table table-borderd table-hover table-sptriped mt-4" style=" display : inline block">
    <thead>
        <tr>
        <th id ="hbox" style=" display : none"></th>
            <%
            String[] head = (String[]) request.getAttribute("col");
            if (head != null) {
                for (int j = 1; j < head.length; j++) {
                    String heads = head[j];
                %>
                <th><%= heads %></th>
                <%
                }
            %>
            <th>
                <button id="addRowButton" class="btn btn-link-black" onclick="toggleTForm()">
                    <i class="bi bi-plus-lg"></i>Ajouter
                </button>
            </th>
            <th>
                <button id="addRowButton2" class="btn btn-link-black" onclick="togglefiltre()">
                    <i class="bi bi-filter-circle"></i>Filtrer
                </button>
            </th>
            <%
            }
            %>
        </tr>
    </thead>
    <tbody>
        <%
        String[][] noms = (String[][]) request.getAttribute("noms");
        if (noms != null) {
            for (int j = 0; j < noms[0].length; j++) {
            %>
            <tr class="selectable-row"  data-row-index="<%= j %>">
             <td id="box"  style=" display :none "  ><input type="checkbox" name="selectedRow" value="<%= noms[0][j] %>" checked></td>
                <%
                for (int i = 1; i < head.length; i++) {
                    String nom = noms[i][j];
                %>                
                <td onclick="infogrp(<%= j %>)" >
                <input type="hidden" id="info_<%= j %>" value="<%= noms[0][j] %>">
                <input type="hidden" id="inforef_<%= j %>" value="<%= noms[1][j] %>">
                <input type="hidden" id="infodes_<%= j %>" value="<%= noms[2][j] %>">
                <%= nom %>
                </td>
                <%
                }
                %>
                <td>
                    <input type="hidden" id="rowIndex_<%= j %>" value="<%= noms[0][j] %>">
                    <input type="hidden" id="nom_<%= j %>" value="<%= noms[1][j] %>">
                    <button onclick="afficherPopupT(<%= j %>)" class="btn btn-secondary">Update</button>
                </td>
                <td>
                    <form class="" action="Transfert" method="GET">
                        <input type="hidden" name="rowIndex" value="<%= noms[0][j] %>">
                        <input type="hidden" name="machine" value="<%= noms[2][j] %>">
                        <input type="hidden" name="date" value="<%= noms[1][j] %>">
                        <input type="hidden" name="agence" value="<%= noms[3][j] %>">
                        <input class="btn btn-secondary" type="submit" name="action" value="Delete">
                    </form>
                </td>
            </tr>
            <%
            }}
            %>
        </tbody>
    </table>
<!-- Ajout machine -->
           <div id="add_mac"  style=" display :none">
				<form action="Materielcrud" class="row g-3" method = "GET" >
  				<div class="col-md-4">
    				<label for="inputEmail4" class="form-label">N° Serie Materiel</label>
   					<input type="text" class="form-control" name="N°SerM" id="N°SerM" required>
				</div>
  				<div class="col-md-4">
    				<label for="inputPassword4" class="form-label">References</label>
    				<input type="text" class="form-control" id="RefM" name="RefM" required>
 				</div>
  				<div class="col-md-2" >
    				<label for="inputAddress" class="form-label">Designation</label>
   	 				<select name="Dropdes" id="Dropdes" class="form-select">
      					<option selected>PC portable</option>
      					<option >PC U.C</option>
      					<option >IMPRIMANTE Laser</option>
      					<option >IMPRIMANTE Epson</option>
      					<option>ONDULEUR</option>
      					<option >ECRAN</option>
    				</select>
  				</div>
  				<div class="col-md-4">
   		 			<label for="inputAddress2" class="form-label">Date Entree</label>
    				<input type="date" class="form-control" id="DateEM" name="DateEM" required>
 	 			</div>
  				<div class="col-md-4">
    				<label for="inputState" class="form-label">Etat</label>
    				<select name="EtatM" id="inputState" class="form-select">
      					<option>Active</option>
      					<option selected>Stock</option>
      					<option>Reparation</option>
    				</select>
  				</div>
  	<div class="col-sm-2">
  	    	<label for="inputState" class="form-label">Agences</label>
    		<select name="location" id="inputState" class="form-select">
          <%  String[] agences = (String[]) request.getAttribute("agences");
          if (agences != null) {
              for (int j = 0; j < agences.length; j++) {
                  String option = agences[j];
              %>
              <option><%= option %></option>
              <%
              }}
          %> 
    		</select>
  		</div>
  				<div class="col-auto">
  				<br>
    				<a id="btncmp" onclick="compinfo()" class="btn btn-light" >Composant</a>
  				</div>
 				<div id="bouton" class="col-12">
    				<button type="submit" name="action" value="insertion" class="btn btn-outline-success" >Enregistrer</button>
   					<a  onclick="clearInputs()" class="btn btn-outline-danger" >Annuler</a>
  				</div>
  				<!-- Add info PC  -->  
      <div id="addcmppc" style=" display :none" >
          <h2>Les composant de materiel</h2>
            <div id="popupForm" class="row g-3" >
  				<div class="col-md-4">
    				<label  class="form-label">CM</label>
   	 				<input type="text" class="form-control" id="Scm" name="Scm" placeholder="n°serie">
   	 				<input type="text" class="form-control" id="Rcm" name="Rcm" placeholder="references">
  				</div>
  				<div class="col-md-4">
   		 			<label class="form-label">CPU</label>
    				<input type="text" class="form-control" id="Scpu" name="Scpu" placeholder="n°serie">
    				<input type="text" class="form-control" id="Rcpu" name="Rcpu" placeholder="references">
 	 			</div>
  				<div class="col-md-4">
    				<label  class="form-label">RAM</label>
    				<input type="text" class="form-control" id="Sram" name="Sram" placeholder="n°serie">
    				<input type="text" class="form-control" id="Rram" name="Rram" placeholder="references">
  				</div>
  				<div class="col-md-4">
    				<label  class="form-label">Memoires</label>
    				<input type="text" class="form-control" id="Srom" name="Srom" placeholder="n°serie">
    				<input type="text" class="form-control" id="Rrom" name="Rrom" placeholder="references">
  				</div><div class="col-md-4">
    				<label  class="form-label">Alimentation</label>
    				<input type="text" class="form-control" id="Salim" name="Salim" placeholder="n°serie">
    				<input type="text" class="form-control" id="Ralim" name="Ralim" placeholder="references">
  				</div>
 				<div class="col-12">
    				<button type="submit" name="action" value="insertion" class="btn btn-outline-success" onclick="MacForm()">Enregistrer</button>
   					<button type="submit" class="btn btn-outline-danger">Annuler</button>
  				</div>
        </div>
    </div>
    			<!-- Add info printer  -->  
   <div id="addcmpprinter" style=" display :none" >
          <h2>Les composant de materiel</h2>
            <div id="popupForm" class="row g-3" >
  				<div class="col-md-4">
    				<label  class="form-label">Toner</label>
    				<input type="text" class="form-control" id="SToner" name="SToner"  >
   	 				<input type="text" class="form-control" id="RToner" name="RToner"  >
  				</div>
  				<div class="col-md-4">
   		 			<label class="form-label">Tambour</label>
    				<input type="text" class="form-control" id="STambour" name="STambour" >
    				<input type="text" class="form-control" id="RTambour" name="RTambour" >
 	 			</div>
  				<div class="col-md-4">
    				<label  class="form-label">Tête d'impression</label>
    				<input type="text" class="form-control" id="STete" name="STete" >
    				<input type="text" class="form-control" id="RTete" name="RTete" >
  				</div>
  				<div class="col-md-4">
    				<label  class="form-label"> Miroir pivotant</label>
    				<input type="text" class="form-control" id="SMiroir" name="SMiroir" >
    				<input type="text" class="form-control" id="RMiroir" name="RMiroir" >
  				</div>
  				<div class="col-md-4">
    				<label  class="form-label">Mémoire vive</label>
    				<input type="text" class="form-control" id="SmemV" name="SmemV" >
    				<input type="text" class="form-control" id="RmemV" name="RmemV" >
  				</div>
 				<div class="col-12">
    				<button type="submit" name="action" value="insertion" class="btn btn-outline-success" onclick="MacForm()">Enregistrer</button>
   					<button type="submit" class="btn btn-outline-danger">Annuler</button>
  				</div>
        </div>
    </div>
     <div id="addcmponduleur" style=" display :none" >
          <h2>Les composant de materiel</h2>
            <div id="popupForm" class="row g-3" >
  				<div class="col-md-4">
    				<label  class="form-label">Batterie</label>
    				<input type="text" class="form-control" id="Sbatt" name="Sbatt"  >
   	 				<input type="text" class="form-control" id="Rbatt" name="Rbatt"  >
  				</div>
  				<div class="col-12">
    				<button type="submit" name="action" value="insertion" class="btn btn-outline-success" onclick="MacForm()">Enregistrer</button>
   					<button type="submit" class="btn btn-outline-danger">Annuler</button>
  				</div>
        </div>
    </div>
      </form>
    </div>
		<!-- Ajout composant -->
        <div id="comp"  style=" display :none">
				<form action="Composant" class="row g-3" method = "GET">
  				<div class="col-md-4">
    				<label for="inputEmail4" class="form-label">N° Serie Composant</label>
   					<input type="text" class="form-control" name="N°SerC" id="N°SerC">
				</div>
  				<div class="col-md-4">
    				<label for="inputPassword4" class="form-label">References</label>
    				<input type="text" class="form-control" name="RefC" id="RefC">
 				</div>
  				<div class="col-md-4">
    				<label for="inputAddress" class="form-label">Designation</label>
   	 				<input type="text" class="form-control" name="DesC" id="DesC" >
  				</div>
  				<div class="col-md-4">
   		 			<label for="inputAddress2" class="form-label">Date Entree</label>
    				<input type="date" class="form-control" name="DateC" id="DateEC">
 	 			</div>
 				<div class="col-12">
    				<button type="submit" name="action" value="insertion" class="btn btn-primary">Enregistrer</button>
   					<a  class="btn btn-primary">Annuler</a>
  				</div>
				</form>
		</div>
		<!-- Ajout transfert -->
        <div id="trans"  style=" display :none">
				<form action="Transfert" class="row g-3" method = "GET">
  				<div class="col-md-4">
    				<label for="inputEmail4" class="form-label">Date transfert</label>
   					<input type="date" class="form-control" name="DateT" id="DateT" required>
				</div>
  				<div class="col-md-4">
    				<label for="inputPassword4" class="form-label">Materiel</label>
    				<select name="TMateriel" id="TMateriel" class="form-select">
         			 <%  String[] listemac = (String[]) request.getAttribute("lsmac");
          				if (listemac != null) {
            			  for (int j = 0; j < listemac.length; j++) {
                		  String option = listemac[j];
            				%>
              				<option><%= option %></option>
             				<%
         				   }}
         					%> 
    				</select> 
 				</div>
  				<div class="col-md-4">
  					<label for="inputAddress2" class="form-label">Motif</label>
  					<input type ="text" class="form-control" id="motifT" name="motifT" required>
 	 			</div>
  				<div class="col-md-4">
   		 			<label for="inputAddress2" class="form-label">Destination</label>
    				<select name="IDes" id="IDes" class="form-select">
        				  <%  if (agences != null) {
              					for (int j = 0; j < agences.length; j++) {
                  					String option = agences[j];
              						%>
              						<option><%= option %></option>
              						<%
              							}}
          							%> 
    				</select>
 	 			</div>
 				<div class="col-12">
    				<button type="submit"  name="action" value="insertion" class="btn btn-primary">Enregistrer</button>
   					<a  class="btn btn-primary">Annuler</a>
  				</div>
				</form>
		</div>
    <!-- Ajout reparation -->
       <div id="repa"  style=" display :none">
				<form action="Reparation" class="row g-3" method = "GET">
  				<div class="col-md-3">
    				<label for="inputEmail4" class="form-label">Date Arrivée</label>
   					<input type="date" class="form-control" name="DateAR" id="DateAR" required>
				</div>
				<div class="col-md-3">
    				<label for="inputEmail4" class="form-label">Debut reparation</label>
   					<input type="date" class="form-control" name="DebutR" id="DebutR" required>
				</div>
  				<div class="col-md-3">
    				<label for="inputPassword4" class="form-label">N°Serie Materiel</label>
					<select name="Materiel" id="Materiel" class="form-select">
         			 <% 
          				if (listemac != null) {
            			  for (int j = 0; j < listemac.length; j++) {
                		  String option = listemac[j];
            				%>
              				<option><%= option %></option>
             				<%
         				   }}
         					%> 
    				</select>   				
    			</div>
 				<div class="col-md-3">
    				<label for="inputAddress" class="form-label">Responsable</label>
   	 				<input type="text" class="form-control" name="Respbl" id="Respbl" required >
  				</div>
  				<div class="col-md-4">
    				<label for="inputAddress" class="form-label">Observation</label>
  					<textarea class="form-control" id="Pblm" name="Pblm" rows="2" required></textarea>  				
  				</div>
  				<div class="col-md-4">
    				<label for="inputAddress" class="form-label">Solution</label>
  					<textarea class="form-control" id="solt" name="solt" rows="2"></textarea>  				
  				</div>
  				<div class="col-md-3">
   		 			<label for="inputAddress2" class="form-label">Fin reparation</label>
    				<input type="date" class="form-control" name="FinR" id="FinR" disabled>
 	 			</div>
 				<div class="col-12">
    				<button type="submit" name="action" value="insertion" class="btn btn-primary">Enregistrer</button>
   					<a  class="btn btn-primary">Annuler</a>
  				</div>
				</form>
		</div>
		<!-- Update transfert -->
		<div id="popupT" class="popup" >
        <div class="popup-content">
            <span onclick="masquerPopupT()" class="close" id="closeButton">&times;</span>
            <h2>Modifier Machine</h2>
				<form id="trans" action="Transfert" class="row g-3" method = "GET">
  				<div class="col-md-4">
    				<label for="inputEmail4" class="form-label">Date transfert</label>
   					<input type="date" class="form-control" name="UDateT" id="UDateT" required>
   					<input type ="hidden" class ="form-control" name="UTid" id="UTid">
				</div>
  				<div class="col-md-4">
    				<label for="inputPassword4" class="form-label">Materiel</label>
    				<select name="UTMateriel" id="UTMateriel" class="form-select">
         			 <% 
          				if (listemac != null) {
            			  for (int j = 0; j < listemac.length; j++) {
                		  String option = listemac[j];
            				%>
              				<option><%= option %></option>
             				<%
         				   }}
         					%> 
    				</select> 
 				</div>
  				<div class="col-md-4">
    				<label for="inputAddress" class="form-label">Provenance</label>
   	 				<select name="UIExp" id="UIExp" class="form-select">
        				  <%  if (agences != null) {
              					for (int j = 0; j < agences.length; j++) {
                  					String option = agences[j];
              						%>
              						<option><%= option %></option>
              						<%
              							}}
          							%> 
    				</select>
  				</div>
  				<div class="col-md-4">
  					<label for="inputAddress2" class="form-label">Motif</label>
  					<input type ="text" class="form-control" id="UmotifT" name="UmotifT" required>
 	 			</div>
  				<div class="col-md-4">
   		 			<label for="inputAddress2" class="form-label">Destination</label>
    				<select name="UIDes" id="UIDes" class="form-select">
        				  <%  if (agences != null) {
              					for (int j = 0; j < agences.length; j++) {
                  					String option = agences[j];
              						%>
              						<option><%= option %></option>
              						<%
              							}}
          							%> 
    				</select>
 	 			</div>
 				<div class="col-12">
    				<button type="submit"  name="action" value="insertion" class="btn btn-primary">Enregistrer</button>
   					<a  class="btn btn-primary">Annuler</a>
  				</div>
			</form>
		</div>
	</div>
    </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="dynamic.js"></script>
</body>
</html>

<%
    }
%>