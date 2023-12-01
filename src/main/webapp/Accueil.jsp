<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("username") == null) {
        response.sendRedirect("Authetification.jsp");
    } else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link rel=stylesheet href="testaj.css">
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<title>ACCUEIL</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark static-top">
  <div class="container">
    <a class="navbar-brand" href="#">
      <img src="assets/img/logo.png" style="width: 20%;  height: auto; max-width: 100%;  border-radius: 50%;" alt="..." height="36">
    </a>
    <form action="Connexion" class="d-grid gap-2 d-md-flex ">
      <button  class="btn btn-danger btn-lg"><i class="bi bi-box-arrow-right"></i>Déconnexion</button>
   </form>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <h2 class="navbar-nav ms-auto" ><span style="color:red;">
      NY HAVANA ASSURANCES
      </span></h2>
    </div>
  </div>
</nav>
 <form id="hero" class="d-flex align-items-center justify-content-center" action="Redirect">
    <div class="container" data-aos="fade-up">
      <div class="row justify-content-center" data-aos="fade-up" data-aos-delay="150">
        <div class="col-xl-6 col-lg-8">
          <h2>Gestion des matériels informatiques</h2>
        </div>
      </div>
      <div class="row gy-4 mt-5 justify-content-center" data-aos="zoom-in" data-aos-delay="250">
        <div class="col-xl-2 col-md-4">
          <div class="icon-box">
            <i class="ri-store-line"></i>
            <h3><button class="button-size" value="materiel" name="test"> Liste des matériels &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</button></h3>
          </div>
        </div>
        <div class="col-xl-2 col-md-4">
          <div class="icon-box">
            <i class="ri-bar-chart-box-line"></i>
            <h3><button class="button-size" value="composant" name="test">Liste des composants &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</button></h3>
          </div>
        </div>
        <div class="col-xl-2 col-md-4">
          <div class="icon-box">
            <i class="ri-calendar-todo-line"></i>
            <h3><button class="button-size" value="reparation" name="test">Liste des matériels en réparation</button></h3>
          </div>
        </div>
        <div class="col-xl-2 col-md-4">
          <div class="icon-box">
            <i class="ri-calendar-todo-line"></i>
            <h3><button class="button-size" value="agences" name="test">Liste des Agences &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button></h3>
          </div>
        </div>
      </div>
    </div>
 </form>
</body>
</html>
<%
    }
%>