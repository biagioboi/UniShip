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
        <p> Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos,
          adipisci</p>
      </div>

    </div>

    <div id="progress-cicle" class="bg-white rounded-lg p-5 shadow m-3">
      <h2 class="h6 font-weight-bold text-center mb-4">Total overdue</h2>
      <!-- Progress bar 4 -->
      <div class="progress mx-auto" data-value='100'>
                <span class="progress-left">
                      <span class="progress-bar border-warning"></span>
                </span>
        <span class="progress-right">
                      <span class="progress-bar border-warning"></span>
                </span>

        <div
            class="progress-value w-100 h-100 rounded-circle d-flex align-items-center justify-content-center">
          <div class="h2 font-weight-bold">12<sup class="small">%</sup></div>
        </div>
      </div>
      <!-- END -->

      <!-- Demo info -->
      <div class="row text-center mt-4">
        <div class="col-6 border-right">
          <div class="h4 font-weight-bold mb-0">28%</div>
          <span class="small text-gray">Last week</span>
        </div>
        <div class="col-6">
          <div class="h4 font-weight-bold mb-0">60%</div>
          <span class="small text-gray">Last month</span>
        </div>
      </div>
      <!-- END -->
    </div>

    <div class="card info-card rounded-lg m-3 p-2">

      <!-- Background color -->
      <div class="card-up pr-1">
        <img src="images/uniLogo.png" alt="logo">
      </div>

      <!-- Content -->
      <div class="card-body">
        <!-- Name -->
        <h4 class="card-title text-center font-weight-bold">Mario Rossi</h4>
        <p class="card-text text-center font-italic">0512106666</p>
        <hr>
        <!-- Some Text -->
        <p> Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos,
          adipisci</p>
      </div>

    </div>


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
            <a id="pills-view-tirocinanti" class="nav-link" data-toggle="pill"
               href="#pills-tirocinanti"
               role="tab" aria-controls="pills-tirocinanti" aria-selected="false">
              <i class="fas fa-user-clock"></i>
              &nbsp;Tirocinanti
            </a>
          </li>
          <% } %>

          <li class="nav-item">
            <a id="pills-view-registro" class="nav-link" data-toggle="pill"
               href="#pills-registro"
               role="tab" aria-controls="pills-registro" aria-selected="false">
              <i class="fas fa-business-time"></i>
              &nbsp;Registro
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

          <!-- questo lo possono vedere gli studenti -->
          <li class="nav-item">
            <a id="pills-view-richieste" class="nav-link" data-toggle="pill"
               href="#pills-richieste"
               role="tab" aria-controls="pills-richieste" aria-selected="false">
              <i class="fas fa-tasks"></i>
              &nbsp;Richieste
            </a>
          </li>

        </ul>
        <div class="tab-content" id="pills-tabContent">
          <!-- qui dobbiamo mettere un solo grande if che a seconda di chi è loggato fa gli
          include, per il momento lascio così e poi si modifica -->
          <% if (tipo.equals("studente")) {%>
          <%@ include file="GUIStudent/viewCompanies.jsp" %>
          <% } else if (tipo.equals("azienda")) {%>
          <%@ include file="GUIAzienda/viewRequestsAvailability.jsp" %>
          <%@ include file="GUIAzienda/viewRegister.jsp" %>
          <% } %>

          <div class="tab-pane fade" id="pills-registro" role="tabpanel"
               aria-labelledby="pills-view-registro">
            <div class="table-responsive font-size-sm min-size-td">
              <table class="table table-hover mb-0 ">
                <thead>
                <tr>
                  <th>Data</th>
                  <th>ore svolte</th>
                  <th>attivita</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                  <td>AZIENDA 1</td>
                  <td>0515252</td>
                  <td>It is a long established fact that a reader will be distracted by the readable
                    content of a page when looking at its layout. The point of using Lorem Ipsum is
                    that it has a more-or-less normal distribution of letters, as opposed to using
                    'Content here, content here', making it look like readable English. Many desktop
                    publishing packages and web page editors now use Lorem Ipsum as their default
                    model text, and a search for 'lorem ipsum' will uncover many web sites still in
                    their infancy. Various versions have evolved over the years, sometimes by
                    accident, sometimes on purpose (injected humour and the like).
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="tab-pane fade" id="pills-richieste" role="tabpanel"
               aria-labelledby="pills-view-richieste">
            <div class="table-responsive font-size-sm">
              <table class="table table-hover mb-0 min-size-td">
                <thead>
                <tr>
                  <th>Studente</th>
                  <th>Azienda</th>
                  <th>stato</th>
                  <th>Motivazioni</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                  <td>051212222</td>
                  <td>Azienda 1</td>
                  <td><span class="addbadge badge ">Valutazione</span></td>
                  <td>Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                    Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
                    when an unknown printer took a galley of type and scrambled it to make a type
                    specimen book.
                  </td>
                </tr>
                <tr>
                  <td>051212222</td>
                  <td>Azienda 2</td>
                  <td><span class="addbadge badge ">Accettata</span></td>
                  <td>Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                    Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
                    when an unknown printer took a galley of type and scrambled it to make a type
                    specimen book.
                  </td>
                </tr>
                <tr>
                  <td>051212222</td>
                  <td>Azienda 3</td>
                  <td><span class="addbadge badge ">Rifiutata</span></td>
                  <td>Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="tab-pane fade" id="pills-tirocini" role="tabpanel"
               aria-labelledby="pills-view-richieste">
            <div class="table-responsive font-size-sm">
              <table class="table table-hover mb-0 ">
                <thead>
                <tr>
                  <th>Studente</th>
                  <th>Azienda</th>
                  <th>Tutor Esterno</th>
                  <th>Ore Svolte</th>
                  <th>Ore Totali</th>
                  <th>stato</th>
                </tr>
                </thead>
                <tbody>
                <tr data-toggle="collapse" data-target="#test">
                  <td>051212222</td>
                  <td>Azienda 1</td>
                  <td>Mario Rossi</td>
                  <td>5:00</td>
                  <td>60:00</td>
                  <td><span class="addbadge badge ">Non completo</span></td>
                </tr>
                <tr class="collapse" id="test">
                  <td>051212222</td>
                  <td>Azienda 1</td>
                  <td>Mario Rossi</td>
                </tr>
                <tr>
                  <td>051212222</td>
                  <td>Azienda 1</td>
                  <td>Mario Rossi</td>
                  <td>5:00</td>
                  <td>60:00</td>
                  <td><span class="addbadge badge ">Da valutare</span></td>
                </tr>
                <tr>
                  <td>051212222</td>
                  <td>Azienda 1</td>
                  <td>Mario Rossi</td>
                  <td>5:00</td>
                  <td>60:00</td>
                  <td><span class="addbadge badge ">Accettata</span></td>
                </tr>
                <tr>
                  <td>051212222</td>
                  <td>Azienda 1</td>
                  <td>Mario Rossi</td>
                  <td>5:00</td>
                  <td>60:00</td>
                  <td><span class="addbadge badge ">Rifiutata</span></td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>

        </div>
      </div>
    </div>
    <% if (tipo.equals("azienda")) { %>
      <%@ include file="GUIAzienda/formProgettoF.jsp" %>
    <% } %>

  </main>
</div>

<div class="toast-area" aria-live="polite" aria-atomic="true">
  <!-- Position it -->
  <div class="toast-box">

    <!-- Then put toasts within -->
    <div id="messaggioSuccesso" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-animation="true"
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
        Ciao, sono un messaggio di successo e scompariro' tra 5 secondi
      </div>
    </div>

    <div id="messaggioErrore" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-animation="true"
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
        Ciao, sono un messaggio di errore e scompariro' tra 5 secondi
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
<% } %>

</body>
</html>