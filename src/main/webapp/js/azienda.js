$(() => {

  caricaRichieste();

  $('#rispondiDisponibilitaModal').on('show.bs.modal', (e) => {
    var matricola = $(e.relatedTarget).data('matricolastudente');
    var nome = $(e.relatedTarget).data('nomestudente');
    var email = $(e.relatedTarget).data('emailstudente');
    $("#nomeStudenteRichiesta").html(nome + " - " + matricola);
    $("#rispondiDisponibilitaModal").attr("emailtarget", email);
  });


});

function caricaRichieste() {
  $.ajax({
    url: 'RichiestaDServlet',
    type: 'POST',
    data: {
      action: 'viewRequest'
    },
    success: (response) => {
      response.forEach((x) => {
        if (x.stato == "Valutazione") {
          let motivazioni = x.motivazioni;
          let studente = x.studente;
          let riga = "<td>" + studente.matricola + "</td>" +
              "<td>" + studente.nome + "</td>" +
              "<td>" + studente.cognome + "</td>" +
              "<td>" + motivazioni + "</td>"
          riga += "<td class=\"text-center\">" +
              "<button class=\"btn btn-primary btn-respond\" " +
              "data-nomestudente = \"" +
              studente.nome + " " + studente.cognome + "\" " +
              "data-matricolastudente = \"" + studente.matricola + "\" " +
              "data-emailstudente = \"" + studente.email + "\" " +
              "data-toggle=\"modal\" " +
              "data-target=\"#rispondiDisponibilitaModal\">Rispondi" +
              "</button>" +
              "</td>";
          $("#tableRichiesteDisponibilita > tbody:last-child")
          .append("<tr>" + riga + "</tr>");
        }
      });
    }
  });
}

function rispondiRichiesta(how) {
  let emailStudente = $("#rispondiDisponibilitaModal").attr("emailtarget");
  let motivazioni = $("#motivazioni").val();

  $.ajax({
    url: 'RichiestaDServlet',
    type: 'POST',
    data: {
      studente: emailStudente,
      messaggio: motivazioni,
      risposta: how,
      action: 'respondRequest'
    },
    success: (response) => {
      $("#rispondiDisponibilitaModal").modal('toggle');
      $("#messaggioSuccessoBody").html("");
      if (response.status == 200) {
        $("#messaggioSuccessoBody").html(response.description);
        $("#messaggioSuccesso").toast('show');
        $(".btn-respond[data-emailstudente='" + emailStudente + "']").fadeOut('500');
      } else {
        $("#messaggioErroreBody").html(response.description);
        $("#messaggioErrore").toast('show');
      }
    }
  });
}