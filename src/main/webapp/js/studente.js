$(() => {
  chargeTableAziendeContent();

  $('#richiediDisponibilitaModal').on('show.bs.modal', (e) => {
    var email = $(e.relatedTarget).data('email');
    $("#nomeAzienda").html($(e.relatedTarget).data('nome'));
    $("#richiediDisponibilitaModal").attr("emailTarget", email);
  });

  $("#btnSendRequest").click(function(e){
    let email = $("#richiediDisponibilitaModal").attr("emailTarget");
    let motivazioni = $("#messaggio").val();
    $.ajax({
      url: 'RichiestaDServlet',
      type: 'POST',
      data: {
        azienda: email,
        messaggio: motivazioni,
        action: 'sendRequest'
      },
      success: (response) => {
        $("#richiediDisponibilitaModal").modal('toggle');
        $("#messaggioSuccessoBody").html("");
        if (response.status == 200) {
          $("#messaggioSuccessoBody").html(response.description);
          $("#messaggioSuccesso").toast('show');
          $(".btn-open-req[data-email='" + email + "']").fadeOut('500');
        } else {
          $("#messaggioErroreBody").html(response.description);
          $("#messaggioErrore").toast('show');
        }
      }
    });
  });


});

function chargeTableAziendeContent() {
  $.ajax({
    url: 'HandleUserServlet',
    type: 'POST',
    data: {
      action: 'retrieveCompanies'
    },
    success: (response) => {
      response.forEach((x) => {
        let azienda = x.key;
        let richiesta = x.value;
        let riga =  "<td>" + azienda.nome + "</td>" +
            "<td>" + azienda.partitaIva + "</td>" +
            "<td>" + azienda.indirizzo + "</td>" +
            "<td>" + azienda.numeroDipendenti + "</td>"
        if (richiesta == null) {
          riga += "<td class='text-center'>" +
              "<button  class='btn btn-success btn-open-req' data-toggle='modal' " +
              "data-target='#richiediDisponibilitaModal'" +
              "data-nome=\"" + azienda.nome + "\" data-email= \"" +
               azienda.email + "\">" + "Richiedi </button>";
        }
        $("#tableAziendePresenti > tbody:last-child")
        .append("<tr>" + riga + "</tr>");
      });

      }
  });
}