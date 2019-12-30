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

  function percentageToDegrees(percentage) {

    return percentage / 100 * 360

  }

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
  $('.toast').toast('show')
});

$("#formSingUp").submit(function(e) {
  e.preventDefault();
  var nome = $("#nome").val();
  var cognome = $("#cognome").val();
  var email = $("#email").val();
  var matricola = $("#matricola").val();
  var codiceFiscale = $("#codiceFiscale").val();
  var cittadinanza = $("#cittadinanza").val();
  var residenza = $("#residenza").val();
  var dataDiNascita = $("#dataDiNascita").val();
  var password = $("#password").val();
  var confPassword = $("#rePassword").val();
  var telefono = $("#numeroTelefono").val();
  if (password.localeCompare(confPassword) == -1) {
    $("#toastRegistrazioneFallitaBody").html("Controlla che le due password corrispondano");
    $("#toastRegistrazioneFallita").toast('show');
  } else {
    $.ajax({
      url: 'SignUpServlet',
      type: 'POST',
      data: {
        action: 'signup',
        nome : nome,
        cognome: cognome,
        codiceFiscale: codiceFiscale,
        email: email,
        matricola: matricola,
        password: password,
        cittadinanza: cittadinanza,
        residenza: residenza,
        dataDiNascita: dataDiNascita,
        telefono: telefono
      },
      success: (response) => {
        if (response.status != 200) {
          //si e' verificato un erorre
          $("#toastRegistrazioneFallitaBody").html(response.description);
          $("#toastRegistrazioneFallita").toast('show');
        } else {
          //effettuare redirect
        }
      }
    });
  }
});