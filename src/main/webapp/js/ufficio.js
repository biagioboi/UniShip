$(() => {
  chargeTableTirocini();

  $("#valutaTirocinio").on('show.bs.modal', (e) => {
    var tirocinio = $(e.relatedTarget).data('idtirocinio');
    var nome = $(e.relatedTarget).data('nome');
    $("#linkPDF").attr("href", "PdfServlet?tirocinio=" + tirocinio);
    $("#valutaTirocinio").attr("idtirocinio", tirocinio);
    $("#nomeStudenteUfficio").html(nome);

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
      response.forEach((x) => {
        let link = "";
        if (x.stato == "Da Valutare") {
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
        $("#richiesteTirocinioUfficio > tbody:last-child").append(
            "<tr>" + riga + "</tr>");
        restyleBadge();
      });
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