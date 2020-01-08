$(() => {
  chargeTableTirocini();

  $("#valutaTirocinio").on('show.bs.modal', (e) => {
    var tirocinio = $(e.relatedTarget).data('idtirocinio');
    var nome = $(e.relatedTarget).data('nome');
    $("#linkPDF").attr("href", "PdfServlet?tirocinio=" + tirocinio);
    $("#valutaTirocinio").attr("idtirocinio", tirocinio);
    $("#nomeStudenteAdmin").html(nome);

  });

});

function chargeTableTirocini() {
  $("#richiesteTirocinioAdmin > tbody:last-child").html("");
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
        if (x.stato == "Da Convalidare") {
          x.stato = "Valuta";
          link = "data-idtirocinio = \"" + x.id + "\""
              + " data-nome='" + x.studente.matricola + " - " + x.studente.nome + " " + x.studente.cognome + "' "
              + " data-toggle='modal' "
              + " data-target='#valutaTirocinio'";
        }
        if (x.motivazioni == null) {
          x.motivazioni = "Non disponibili.";
        }
        let riga = "<td>" + x.studente.matricola + "</td>"
            + "<td>" + x.azienda.nome + "</td>"
            + "<td><span class='addbadge badge pointer' " + link + " >"
            + x.stato + "</span></td>"
            + "<td>" + x.motivazioni + "</td>";
        $("#richiesteTirocinioAdmin > tbody:last-child").append(
            "<tr>" + riga + "</tr>");
        restyleBadge();
      });
      if (!exist) {
        $("#richiesteTirocinioAdmin > tbody:last-child")
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
      motivazioni : motivazioni,
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