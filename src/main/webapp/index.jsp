<%@include file="securety.jsp" %>
<%
String tipo = (String) session.getAttribute("tipo");
%>
<!doctype html>
<html lang="en">
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
        crossorigin="anonymous">
  <link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="css/style.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.0/css/all.css"
        integrity="sha384-REHJTs1r2ErKBuJB0fCK99gCYsVjwxHrSU0N7I1zl9vZbggVJXRMsv/sLlOAGb4M"
        crossorigin="anonymous">
  <link rel="icon" type="image/png" sizes="32x32" href="images/favicon-32x32.png">
  <title>UniShip</title>
</head>
<body>
<div class="page-wrapper default-theme sidebar-bg bg1">
  <nav id="sidebar" class="sidebar-wrapper overflow-auto custom-scrollbar">
    <div class="overlay-slider"></div>

    <div class="card info-card rounded-lg m-3 p-2">

      <!-- Background color -->
      <div class="card-up pr-1">
        <img src="images/uniLogo.png" alt="logo">
      </div>

      <!-- Content -->
      <div class="card-body">
        <!-- Name -->
        <h4 id="nomeUtente" class="card-title text-center font-weight-bold">Mario Rossi</h4>
        <p id="numeroMatricola" class="card-text text-center font-italic">0512106666</p>
        <hr>
        <!-- Some Text -->
        <p class="text-centr"></p>
      </div>

    </div>

    <% if (tipo.equals("studente")) { %>
    <div id="progress-cicle" class="bg-white rounded-lg p-5 shadow m-3">
      <h2 class="h6 font-weight-bold text-center mb-4">Overview</h2>
      <!-- Progress bar 4 -->
      <div class="progress mx-auto" data-value='0' id="percentoOreSvolte">
                <span class="progress-left">
                      <span class="progress-bar border-warning"></span>
                </span>
        <span class="progress-right">
                      <span class="progress-bar border-warning"></span>
                </span>

        <div
            class="progress-value w-100 h-100 rounded-circle d-flex align-items-center justify-content-center">
          <div class="h2 font-weight-bold"><span id="percentoTotale">0</span><sup class="small">%</sup></div>
        </div>
      </div>

      <!-- Demo info -->
      <div class="row text-center mt-4">
        <div class="col-6 border-right">
          <div class="h4 font-weight-bold mb-0" id="oreFatte">0</div>
          <span class="small text-gray">Ore svolte</span>
        </div>
        <div class="col-6">
          <div class="h4 font-weight-bold mb-0" id="oreTotali">0</div>
          <span class="small text-gray">Ore totali</span>
        </div>
      </div>
      <!-- END -->
    </div>
    <% } %>

  </nav>
  <!-- page-content  -->
  <main class="page-content">
    <div id="overlay" class="overlay"></div>


    <nav id="navbar" class="navbar navbar-expand-md navbar-light bg-white">
      <button id="toggle-sidebar" class="toggle-btn" type="button">
        <span></span>
        <span></span>
        <span></span>
      </button>
      <a class="navbar-brand px-3 font-weight-bold primary-color h4" href="#">UniShip</a>
      <ul class="navbar-nav ml-auto">
        <li class="nav-item active">
          <a class="nav-link primary-color" id="logoutBtn" href="#">Logout <i class="fas fa-sign-out-alt"></i></a>
        </li>
      </ul>
    </nav>


    <div class="container-fluid ">
      <div class="table-pills m-3 p-3 rounded-lg">
        <ul class="nav nav-tabs nav-pills" role="tablist">

          <% if (tipo.equals("studente")) { %>
          <li class="nav-item">
            <a id="pills-view-aziende" class="nav-link active" data-toggle="pill"
               href="#pills-aziende" role="tab" aria-controls="pills-aziende" aria-selected="true">
              <i class="far fa-building"></i>
              Lista Aziende
            </a>
          </li>
          <li class="nav-item">
            <a id="pills-view-richieste" class="nav-link" data-toggle="pill"
               href="#pills-richieste"
               role="tab" aria-controls="pills-richieste" aria-selected="false">
              <i class="fas fa-tasks"></i>
              &nbsp;Richieste
            </a>
          </li>
          <li class="nav-item">
            <a id="pills-view-registro" class="nav-link" data-toggle="pill"
               href="#pills-registro"
               role="tab" aria-controls="pills-registro" aria-selected="false">
              <i class="fas fa-business-time"></i>
              &nbsp;Registro
            </a>
          </li>
          <li class="nav-item">
            <a id="pills-view-tirociniStudente" class="nav-link" data-toggle="pill"
               href="#pills-tirociniStudente"
               role="tab" aria-controls="pills-tirociniStudente" aria-selected="false">
              <i class="fas fa-file"></i>
              &nbsp;Tirocini
            </a>
          </li>
          <% } else if (tipo.equals("azienda")) { %>
          <li class="nav-item">
            <a id="pills-view-richiesteAzienda" class="nav-link active" data-toggle="pill"
               href="#pills-richiesteAzienda"
               role="tab" aria-controls="pills-richiesteAzienda" aria-selected="true">
              <i class="fas fa-tasks"></i>
              &nbsp;Richieste
            </a>
          </li>
          <li class="nav-item">
            <a id="pills-view-tirocini" class="nav-link" data-toggle="pill"
               href="#pills-tirocini"
               role="tab" aria-controls="pills-tirocini" aria-selected="false">
              <i class="fas fa-file"></i>
              &nbsp;Tirocini
            </a>
          </li>
          <% } else if (tipo.equals("ufficio_carriere")) { %>
          <li class="nav-item">
            <a id="pills-view-richiesteufficio" class="nav-link active" data-toggle="pill"
               href="#pills-richiesteufficio"
               role="tab" aria-controls="pills-richiesteufficio" aria-selected="true">
              <i class="fas fa-tasks"></i>
              &nbsp;Richieste
            </a>
          </li>
          <li class="nav-item">
            <a id="pills-view-aggiungiazienda" class="nav-link" data-toggle="pill"
               href="#pills-aggiungiazienda"
               role="tab" aria-controls="pills-aggiungiazienda" aria-selected="false">
              <i class="fas fa-user-plus"></i>
              &nbsp;Nuova Azienda
            </a>
          </li>
          <% } else if (tipo.equals("admin")) { %>
          <li class="nav-item">
            <a id="pills-view-richiesteadmin" class="nav-link active" data-toggle="pill"
               href="#pills-richiesteadmin"
               role="tab" aria-controls="pills-richiesteadmin" aria-selected="true">
              <i class="fas fa-tasks"></i>
              &nbsp;Richieste
            </a>
          </li>
          <% } %>



        </ul>
        <div class="tab-content" id="pills-tabContent">

          <% if (tipo.equals("studente")) {%>
          <%@ include file="GUIStudent/viewCompanies.jsp" %>
          <%@ include file="GUIStudent/viewRequest.jsp" %>
          <%@ include file="GUIStudent/viewRegister.jsp" %>
          <%@ include file="GUIStudent/viewTirocini.jsp" %>
          <% } else if (tipo.equals("azienda")) {%>
          <%@ include file="GUIAzienda/viewRequestsAvailability.jsp" %>
          <%@ include file="GUIAzienda/viewTirocini.jsp" %>
          <% } else if (tipo.equals("ufficio_carriere")) {%>
          <%@ include file="GUICarrierOffice/viewRequestTirocini.jsp" %>
          <%@ include file="GUICarrierOffice/formAddCompany.jsp" %>
          <% } else if (tipo.equals("admin")) {%>
          <%@ include file="GUIAdmin/viewRequestTirocini.jsp" %>
          <% } %>

        </div>
      </div>
    </div>

    <% if (tipo.equals("azienda")) { %>
    <%@ include file="GUIAzienda/formProgettoF.jsp" %>
    <% } else if (tipo.equals("ufficio_carriere")) { %>
    <%@ include file="GUICarrierOffice/viewRegister.jsp" %>
    <% } else if (tipo.equals("admin")) { %>
    <%@ include file="GUIAdmin/viewRegister.jsp" %>
    <% } %>

  </main>
</div>

<div class="toast-area" aria-live="polite" aria-atomic="true">
  <!-- Position it -->
  <div class="toast-box">

    <!-- Then put toasts within -->
    <div id="messaggioSuccesso" class="toast fade hide" role="alert" aria-live="assertive" aria-atomic="true" data-animation="true"
         data-delay="5000">
      <div class="toast-header">
        <span class="circle bg-success"></span>
        <strong class="mr-auto">UniShip</strong>
        <small class="text-muted">just now</small>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div id="messaggioSuccessoBody" class="toast-body">
      </div>
    </div>

    <div id="messaggioErrore" class="toast fade hide" role="alert" aria-live="assertive" aria-atomic="true" data-animation="true"
         data-delay="5000">
      <div class="toast-header">
        <span class="circle bg-danger"></span>
        <strong class="mr-auto">UniShip</strong>
        <small class="text-muted">just now</small>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div id="messaggioErroreBody" class="toast-body">
      </div>
    </div>
  </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script src="js/script.js"></script>


<% if (tipo.equals("studente")) { %>
<script src="js/studente.js"></script>
<% } else if (tipo.equals("azienda")) { %>
<script src="js/azienda.js"></script>
<% } else if (tipo.equals("ufficio_carriere")) { %>
<script src="js/ufficio.js"></script>
<% } else if (tipo.equals("admin")) { %>
<script src="js/admin.js"></script>
<% } %>

</body>
</html>