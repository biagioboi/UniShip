$(() => {
  chargeTableTirocini();

  $("#valutaTirocinio").on('show.bs.modal', (e) => {
    var tirocinio = $(e.relatedTarget).data('idtirocinio');
    var nome = $(e.relatedTarget).data('nome');
    $("#linkPDF").attr("href", "PdfServlet?tirocinio=" + tirocinio);
    $("#valutaTirocinio").attr("idtirocinio", tirocinio);
    $("#nomeStudenteUfficio").html(nome);

  });

  $('#visualizzaOreModal').on('show.bs.modal', (e) => {
    var id = $(e.relatedTarget).data('idtirocinio');
    var matricola = $(e.relatedTarget).data('matricolastudente');
    var nome = $(e.relatedTarget).data('nomestudente');
    $("#visualizzaOreModal").attr("idtirocinio", id);
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

});

function chargeTableTirocini() {
  $("#richiesteTirocinioUfficio > tbody:last-child").html("");
  $.ajax({
    url: 'TirocinioServlet',
    type: 'POST',
    data: {
      action: 'viewInternship'
    },
    success: (response) => {
      let exist = false;
      response.forEach((x) => {
        let link = "";
        exist = true;
        if (x.stato == "Da Valutare") {
          x.stato = "Valuta";
          link = "data-idtirocinio = \"" + x.id + "\""
              + " data-nome='" + x.studente.matricola + " - " + x.studente.nome
              + " " + x.studente.cognome + "' "
              + " data-toggle='modal' "
              + " data-target='#valutaTirocinio'";
        }
        let btn = "<td></td>";
        if (x.stato == "Da Valutare" || x.stato == "Accettata") {
          btn = `<td class='text-center'><button class='btn btn-success btn-sm' ` +
              `data-toggle='modal' ` +
              `data-nomestudente='${x.studente.nome} ${x.studente.cognome}' ` +
              `data-matricolastudente='${x.studente.matricola}' ` +
              `data-idtirocinio='${x.id}' ` +
              `data-target='#visualizzaOreModal'>`+
              `Visualizza ore</button></td>`;
        }
        let riga = "<td>" + x.studente.matricola + "</td>"
            + "<td>" + x.azienda.nome + "</td>"
            + `<td>${x.tutorEsterno}</td>`
            + `<td>${timeConvert(x.oreSvolte)}</td>`
            + `<td>${timeConvert(x.oreTotali)}</td>`
            + "<td><span class='addbadge badge pointer' " + link + " >"
            + x.stato + "</span></td>"
            + btn ;
        $("#richiesteTirocinioUfficio > tbody:last-child").append(
            "<tr>" + riga + "</tr>");
        restyleBadge();
      });
      if (!exist) {
        $("#richiesteTirocinioUfficio > tbody:last-child")
        .html("<tr><td style='text-align: center;' class='mt-2'>" +
            "Non sono presenti richieste.</td></tr>");
      }

    }
  });
}

function respondTirocinio(how) {
  let motivazioni = $("#motivazioniValidazioneTirocinio").html();
  let tirocinio = $("#valutaTirocinio").attr("idtirocinio");
  $.ajax({
    url: 'TirocinioServlet',
    type: 'POST',
    data: {
      motivazioni: motivazioni,
      tirocinio: tirocinio,
      risposta: how,
      action: 'validateInternship'
    },
    success: (response) => {
      if (response.status == 200) {
        $("#messaggioSuccessoBody").html(response.description);
        $("#messaggioSuccesso").toast('show');
        $("#valutaTirocinio").modal('toggle');
        chargeTableTirocini();
      } else {
        $("#messaggioErroreBody").html(response.description);
        $("#messaggioErrore").toast('show');
      }
    }

  });
}