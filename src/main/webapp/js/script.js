$(function () {

  $(".progress").each(function () {

    var value = $(this).attr('data-value');
    var left = $(this).find('.progress-left .progress-bar');
    var right = $(this).find('.progress-right .progress-bar');

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

  /*disabilito perchè volevo provare il toast della registrazione fallita*/
  $('.toast').toast('show');

  checkForLogin();

});

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
        let cognome = "";
        let nome = response.nome;
        if (!response.tipo.localeCompare("studente")) {
          cognome = response.cognome;
          $("#numeroMatricola").html(response.matricola);
        }
        $("#nomeUtente").html(nome + " " + cognome);
      } else {
        location.href = 'signin.html';
      }
    }
  });
}


