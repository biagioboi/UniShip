$(function () {


  //toggle sidebar
  $("#toggle-sidebar").click(function () {
    $(".page-wrapper").toggleClass("toggled");
    $("#toggle-sidebar").toggleClass("toggled");
  });

  //toggle sidebar overlay
  $("#overlay").click(function () {
    $(".page-wrapper").toggleClass("toggled");
    $("#toggle-sidebar").toggleClass("toggled");
  });


  restyleBadge();

  checkForLogin();

});

function restyleBadge(){
  $(".addbadge").each(function () {
    var html = $(this).html();

    if (html == "Non completo") {
      $(this).addClass("badge-info");
    } else if (html == "Accettata") {
      $(this).addClass("badge-success");
    } else if (html =="Rifiutata"){
      $(this).addClass("badge-danger");
    } else {
      $(this).addClass("badge-warning");
    }
  });
}

function percentageToDegrees(percentage) {

  return percentage / 100 * 360

}


function checkForLogin() {
  $.ajax({
    url: 'SessionServlet',
    type: 'POST',
    data: {
      action: 'retrieveUserLogged'
    },
    success: (response) => {
      response = JSON.parse(response);
      if (response!=null) {
        if (!response.tipo.localeCompare("studente")) {
          let nome = response.nome;
          let cognome = response.cognome;
          let matricola = response.matricola;

          $("#numeroMatricola").html(matricola);
          $("#nomeUtente").html(`${nome} ${cognome}`);
        }else if (!response.tipo.localeCompare("azienda")){
          let nome = response.nome;
          let partitaIva = response.partitaIva;

          $("#numeroMatricola").html(partitaIva);
          $("#nomeUtente").html(`${nome}`);

        }
      } else {
        location.href = 'signin.html';
      }
    }
  });
}

$("#logoutBtn").click((e) => {
  $.ajax({
    url: 'SessionServlet',
    type: 'POST',
    data: {
      action: 'logOut',
    },
    success: (response) => {
      location.href = response.redirect;
    }

  })
});

function timeConvert(n) {
  let num = n;
  let hours = (num / 60);
  let rhours = Math.floor(hours);
  let minutes = (hours - rhours) * 60;
  let rminutes = Math.round(minutes);
  return rhours +":"+ rminutes;
}

function updateValueSpinner() {
  $(".progress").each(function () {

    let value = $(this).attr('data-value');
    let left = $(this).find('.progress-left .progress-bar');
    let right = $(this).find('.progress-right .progress-bar');
    $("#percentoTotale").html(value);

    if(value > 100){
      value = 100;
    }

    if (value > 0) {
      right.css("transition", "all linear 2s");
      if (value <= 50) {
        right.css('transform', 'rotate(' + percentageToDegrees(value) + 'deg)')
      } else {
        right.css('transform', 'rotate(180deg)');
        left.css('transform',
            'rotate(' + percentageToDegrees(value - 50) + 'deg)');
        left.css("transition", "all linear 1s");
        left.css("transition-delay", " 2s");
      }

    }

  });
}