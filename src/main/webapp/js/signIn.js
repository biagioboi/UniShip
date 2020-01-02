$("#formAccesso").submit((e) => {
  e.preventDefault();
  let email = $("#email").val();
  let password = $("#password").val();
  $.ajax({
    url: 'SessionServlet',
    type: 'POST',
    data: {
      email: email,
      password: password,
      action: 'logIn'
    },
    succcess: (response) => {
      if (response.status != 302) {
        $("#toastAccessoFallitoBody").html(response.description);
        $("#toastAccessoFallito").toast('show');
      } else {
        location.href = response.redirect;
      }
    }
  });
});