<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*"%>

<!doctype html>
<html >
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Authentification</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    
     <script>
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
        });
    </script>
    
  </head>
  <style>
     .boxLogin{background: hsla(212, 93%, 49%, 1);

      background: linear-gradient(90deg, hsla(212, 93%, 49%, 1) 0%, hsla(210, 90%, 80%, 1) 100%);

      background: -moz-linear-gradient(90deg, hsla(212, 93%, 49%, 1) 0%, hsla(210, 90%, 80%, 1) 100%);

       background: -webkit-linear-gradient(90deg, hsla(212, 93%, 49%, 1) 0%, hsla(210, 90%, 80%, 1) 100%);

      filter: progid: DXImageTransform.Microsoft.gradient( startColorstr="#0974F1", endColorstr="#9FCCFA", GradientType=1 );}
  </style>
  <body>
  <section class="vh-100 gradient-custom" style="background-image: url('https://img.freepik.com/free-photo/abstract-wavy-shapes_1048-4740.jpg?size=626&ext=jpg&ga=GA1.2.548815526.1695381939&semt=ais');backgroud-repeat:no-repeat;background-size:cover;background-positrion:center;">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-12 col-md-8 col-lg-6 col-xl-5">
        <div class="boxLogin card bg-secondary text-white" style="border-radius: 1rem;">
          <div class=" card-body p-5 text-center">
             <div class="text-center">
                  <img class="rounded-circle img-fluid" style="max-width:100%; height:auto;" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8meQUqYctQMgZY2_Q3VzrqU0zbpSzqTCKb1khplgCQA&s"
                    style="width: 185px;" alt="logo">
                </div>
              <p class="text-white-50 mt-2">pour vous connectez</p>
             <form action="Connexion" method="POST">
                <div class="form-outline form-white mb-4">
                  <label class="form-label" for="typeUser">Utilisateur</label>
                  <input type="text" id="username" name="username"  class="form-control form-control-lg" required/>
                </div>
               <div class="form-white mb-4">
                 <label class="form-label" for="typePasswordX">Mot de passe</label>
                <div class="input-group">
                   <input type="password" id="password" name="password" class="form-control form-control-lg" required />
                    <button type="button" id="togglePassword" class="btn btn-light">
                    <i id="passwordIcon" class="bi bi-eye-slash"></i>
                    </button>
               </div>
             </div>
        <script>
              const passwordInput = document.getElementById("password");
              const togglePasswordButton = document.getElementById("togglePassword");
              const passwordIcon = document.getElementById("passwordIcon");

              togglePasswordButton.addEventListener("click", function () {
                if (passwordInput.type === "password") {
                  passwordInput.type = "text";
                  passwordIcon.classList.remove("bi-eye-slash");
                  passwordIcon.classList.add("bi-eye");
             } else {
                  passwordInput.type = "password";
                  passwordIcon.classList.remove("bi-eye");
                  passwordIcon.classList.add("bi-eye-slash");
            }
       });
    </script>
             

                 <button class="btn btn-outline-light btn-lg "type="submit">Se connecter</button>
             </form>
             <%-- Afficher un message d'erreur en cas d'échec de l'authentification --%>
               <% if (request.getParameter("error") != null) { %>
                     <p class="text-danger">Nom d'utilisateur ou mot de passe incorrect.</p>
               <% } %>
             <%-- Afficher un message d'erreur en cas d'échec de l'authentification --%>
          
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>  
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
 </body>
</html>