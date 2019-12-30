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
        numero: telefono
      },
      success: (response) => {

        if (response.status != 302) {
          $("#toastRegistrazioneFallitaBody").html(response.description);
          $("#toastRegistrazioneFallita").toast('show');
        } else {
          location.href = response.redirect;
        }

      }
    });
  }
});