$(() => {

  caricaRichieste();

  caricaTirocini();

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
    var matricola = $(e.relatedTarget).data('matricolastudente');
    var nome = $(e.relatedTarget).data('nomestudente');
    $("#aggiungiOreModal").attr("idtirocinio", id);
    $("#nomeStudenteAttRegistro").html(nome + " - " + matricola);
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
          $("#tableOreSvolte > tbody:last-child").append(
              "<tr>" + riga + "</tr>");
        });
        $("#tableOreSvolte").fadeIn();
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
          $("#tableRichiesteDisponibilita").fadeOut();
          caricaRichieste();
          $("#messaggioSuccessoBody").html(response.description);
          $("#messaggioSuccesso").toast('show');
          caricaTirocini();

        } else {
          $("#messaggioErroreBody").html(response.description);
          $("#messaggioErrore").toast('show');
        }
      }
    });
  });

  $("#formAddActivity").submit((e) => {
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
          caricaTirocini();
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
  $("#tableRichiesteDisponibilita > tbody:last-child").html("");
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
          .append("<tr email='" + studente.email + "'>" + riga + "</tr>");
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
        let matricola = $(
            ".btn-respond[data-emailstudente='" + emailStudente + "']").attr(
            "data-matricolastudente");
        let nome = $(
            ".btn-respond[data-emailstudente='" + emailStudente + "']").attr(
            "data-nomestudente");
        if (how == "Accettata") {
          $(".btn-respond[data-emailstudente='" + emailStudente
              + "']").parent().html(
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
          caricaRichieste();
        }

      } else {
        $("#messaggioErroreBody").html(response.description);
        $("#messaggioErrore").toast('show');
      }
    }
  });
}

function caricaTirocini() {
  $.ajax({
    url: 'TirocinioServlet',
    type: 'POST',
    data: {
      action: 'viewInternship'
    },
    success: (response) => {
      $("#tableTirociniAzienda > thead").fadeIn();
      $("#tableTirociniAzienda > tbody:last-child").html("");
      let exist = false;
      response.forEach((e) => {
        exist = true;
        let nomeStudente = `${e.studente.nome} ${e.studente.cognome}`;
        let azienda = `${e.azienda.nome}`;
        let tutor = `${e.tutorEsterno}`;
        let oreSvolte = `${timeConvert(e.oreSvolte)}`;
        let oreTotali = `${timeConvert(e.oreTotali)}`;
        let stato = `${e.stato}`;
        let riga = `<td>${nomeStudente}</td><td>${azienda}</td>` +
            `<td>${tutor}</td><td>${oreSvolte}</td>` +
            `<td>${oreTotali}</td><td><span class="addbadge badge">${stato}</span></td>`;
        if (e.stato == "In corso") {
          riga += "<td class='text-center'>"
              + " <button class=\"btn btn-success btn-sm\" data-toggle=\"modal\" "
              + `         data-nomestudente = \"${nomeStudente}\" `
              + `         data-matricolastudente = \"${e.studente.matricola}\" `
              + `         data-idtirocinio=\"${e.id}\" `
              + "         data-target=\"#aggiungiOreModal\"> "
              + "         Aggiungi ore "
              + "</button>"
              + "</td>";
        } else {
          riga += "<td></td>";
        }
        $("#tableTirociniAzienda > tbody:last-child").append(
            `<tr>${riga}</tr>`);
      });
      if (!exist) {
        $("#tableTirociniAzienda > thead").fadeOut();
        $("#tableTirociniAzienda > tbody:last-child").append(
            `<tr><td colspan="6" class="text-center">Non sono presenti tirocini.</td></tr>`);
      } else {
        restyleBadge();
      }
    }
  });
}

function dateConverter(date) {
  let mesi = ["gen", "feb", "mar", "apr", "mag", "giu", "lug", "ago", "set",
    "ott", "nov", "dic"];
  let mese = parseInt(date.substr(5, 2)) - 1;
  let giorno = date.substr(8, 2);
  let anno = date.substr(0, 4);
  return mesi[mese] + " " + giorno + ", " + anno;
}

