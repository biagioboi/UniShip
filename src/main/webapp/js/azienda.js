$(() => {

  caricaRichieste();

  caricaStudenti();


  $('#rispondiDisponibilitaModal').on('show.bs.modal', (e) => {
    var matricola = $(e.relatedTarget).data('matricolastudente');
    var nome = $(e.relatedTarget).data('nomestudente');
    var email = $(e.relatedTarget).data('emailstudente');
    $("#nomeStudenteRichiesta").html(nome + " - " + matricola);
    $("#rispondiDisponibilitaModal").attr("emailtarget", email);
  });

  $('#compilaProgettoFormativoModal').on('show.bs.modal', (e) => {
    var matricola = $(e.relatedTarget).data('matricolastudente');
    var nome = $(e.relatedTarget).data('nomestudente');
    var email = $(e.relatedTarget).data('emailstudente');
    $("#nomeStudenteProgettoF").html(nome + " - " + matricola);
    $("#compilaProgettoFormativoModal").attr("emailtarget", email);
  });

  $('#aggiungiOreModal').on('show.bs.modal', (e) => {
    var id = $(e.relatedTarget).data('idtirocinio');
    $("#aggiungiOreModal").attr("idtirocinio", id);
    $("#tableOreSvolte > tbody").html("");
    $.ajax({
      url: 'RegistroServlet',
      type: 'POST',
      data: {
        action: 'viewRegister',
        tirocinio: id
      },
      success: (response) => {
        let exist = false;
        response.forEach((e) => {
          exist = true;
          let data = e.data;
          let oreSvolte = timeConvert(e.oreSvolte);
          let attivita = e.attivita;
          let riga = "<td>" + data + "</td>"
              + "<td>" + oreSvolte + "</td>"
              + "<td>" + attivita + "</td>";
          $("#tableOreSvolte > tbody:last-child").append("<tr>" + riga + "</tr>");
        });
      }
    });
  });



  $("#btnInviaProgettoF").click(() => {
    let emailStudente = $("#compilaProgettoFormativoModal").attr("emailtarget");
    let cfu = $("#numeroCfu").val();
    let sede = $("#sedeSvolgimento").val();
    let obiettivi = $("#obiettivi").val();
    let competenze = $("#competenze").val();
    let attivita = $("#attivita").val();
    let modalita = $("#modalita").val();
    let orario = $("#orarioLavorativo").val();
    let numeroRc = $("#numeroRc").val();
    let polizza = $("#polizza").val();
    let dataInizio = $("#dataInizio").val();
    let dataFine = $("#dataFine").val();

    $.ajax({
      url: 'PdfServlet',
      type: 'POST',
      data: {
        studente: emailStudente,
        cfu: cfu,
        sede: sede,
        obiettivi: obiettivi,
        competenze: competenze,
        attivita: attivita,
        modalita: modalita,
        orario: orario,
        numeroRc: numeroRc,
        polizza: polizza,
        dataInizio: dataInizio,
        dataFine: dataFine,
        action: 'createPdf'
      },
      success: (response) => {
        $("#compilaProgettoFormativoModal").modal('toggle');

        if (response.status == 200) {
          $("#richiesta" + emailStudente).remove();
          $("#messaggioSuccessoBody").html(response.description);
          $("#messaggioSuccesso").toast('show');

        } else {
          $("#messaggioErroreBody").html(response.description);
          $("#messaggioErrore").toast('show');
        }
      }
    });
  });

  $("#formAddActivity").submit((e)=> {
    e.preventDefault();
    let ore = $("#ore").val();
    let data = $("#giorno").val();
    let descrizione = $("#descrizione").val();
    let tirocinio = $("#aggiungiOreModal").attr("idtirocinio");
    $.ajax({
      url: 'RegistroServlet',
      type: 'POST',
      data: {
        action: 'addActivity',
        tirocinio: tirocinio,
        oreSvolte: ore,
        data: data,
        attivita: descrizione
      },
      success: (response) => {
        $("#aggiungiOreModal").modal('toggle');
        if (response.status == 200) {
          document.getElementById("formAddActivity").reset();
          $("#messaggioSuccessoBody").html(response.description);
          $("#messaggioSuccesso").toast('show');
        } else {
          $("#messaggioErroreBody").html(response.description);
          $("#messaggioErrore").toast('show');
        }
      }
    });
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
      let exist = false;
      response.forEach((x) => {
        if (x.stato == "Valutazione" || x.stato == "Accettata") {
          exist = true;
          let motivazioni = x.motivazioni;
          let studente = x.studente;
          let riga = "<td>" + studente.matricola + "</td>" +
              "<td>" + studente.nome + "</td>" +
              "<td>" + studente.cognome + "</td>" +
              "<td>" + motivazioni + "</td>";
          if (x.stato == "Valutazione") {
            riga += "<td class=\"text-center\">" +
                "<button class=\"btn btn-success btn-respond\" " +
                "data-nomestudente = \"" +
                studente.nome + " " + studente.cognome + "\" " +
                "data-matricolastudente = \"" + studente.matricola + "\" " +
                "data-emailstudente = \"" + studente.email + "\" " +
                "data-toggle=\"modal\" " +
                "data-target=\"#rispondiDisponibilitaModal\">Rispondi" +
                "</button>" +
                "</td>";
          } else {
            riga += "<td class=\"text-center\">" +
                "<button class=\"btn btn-success btn-respond\" " +
                "data-nomestudente = \"" +
                studente.nome + " " + studente.cognome + "\" " +
                "data-matricolastudente = \"" + studente.matricola + "\" " +
                "data-emailstudente = \"" + studente.email + "\" " +
                "data-toggle=\"modal\" " +
                "data-target=\"#compilaProgettoFormativoModal\">Compila Progetto F."
                +
                "</button>" +
                "</td>";
          }
          $("#tableRichiesteDisponibilita > tbody:last-child")
          .append("<tr id='richiesta" + studente.email + "'>" + riga + "</tr>");
        }
      });
      if (!exist) {
        $("#tableRichiesteDisponibilita")
        .html("<tr><td style='text-align: center;' class='mt-2'>" +
            "Non sono presenti richieste di disponibilit&agrave;.</td></tr>");
      }
      $("#tableRichiesteDisponibilita").fadeIn();
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
        let email = emailStudente;
        let matricola = $(".btn-respond[data-emailstudente='" + emailStudente + "']").attr("data-matricolastudente");
        let nome = $(".btn-respond[data-emailstudente='" + emailStudente + "']").attr("data-nomestudente");
        $(".btn-respond[data-emailstudente='" + emailStudente + "']").parent().html(
            "<button class=\"btn btn-success btn-respond\" " +
            "data-nomestudente = \"" + nome + "\" " +
            "data-matricolastudente = \"" + matricola + "\" " +
            "data-emailstudente = \"" + email + "\" " +
            "data-toggle=\"modal\" " +
            "data-target=\"#compilaProgettoFormativoModal\">Compila Progetto F."
            +
            "</button>"
        );

      } else {
        $("#messaggioErroreBody").html(response.description);
        $("#messaggioErrore").toast('show');
      }
    }
  });
}

function caricaStudenti() {
  $.ajax({
    url: 'TirocinioServlet',
    type: 'POST',
    data: {
      action: 'viewInternship'
    },
    success: (response) => {
      let exist = false;
      response.forEach((e) => {
        let studente = e.studente;
        let riga = "<td>" + studente.matricola + "</td>"
            + "<td>" + studente.nome + "</td>"
            + "<td>" + studente.cognome + "</td>"
            + "<td>" + studente.codiceFiscale + "</td>"
            + "<td class='text-center'>"
            + " <button class=\"btn btn-success\" data-toggle=\"modal\" "
            + "         data-idtirocinio=\"" + e.id + "\" "
            + "         data-target=\"#aggiungiOreModal\"> "
            + "         Aggiungi ore "
            + "</button>"
            + "</td>";
        $("#tableStudentiTirocinio > tbody:last-child").append("<tr>" + riga + "</tr>");
      })
    }
  });
}

function timeConvert(n) {
  let num = n;
  let hours = (num / 60);
  let rhours = Math.floor(hours);
  let minutes = (hours - rhours) * 60;
  let rminutes = Math.round(minutes);
  return rhours +":"+ rminutes;
}

function dateConverter(date) {
  let mesi = ["gen", "feb", "mar", "apr", "mag", "giu", "lug", "ago", "set", "ott", "nov", "dic"];
  let mese = parseInt(date.substr(5, 2))-1;
  let giorno = date.substr(8, 2);
  let anno = date.substr(0, 4);
  return mesi[mese] + " " + giorno + ", " + anno;
}