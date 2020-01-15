$(() => {
  chargeTableAziendeContent(checkIfExistTirocinio);

  $('#richiediDisponibilitaModal').on('show.bs.modal', (e) => {
    var email = $(e.relatedTarget).data('email');
    $("#nomeAzienda").html($(e.relatedTarget).data('nome'));
    $("#richiediDisponibilitaModal").attr("emailtarget", email);
  });

  $("#btnSendRequest").click(function (e) {
    let email = $("#richiediDisponibilitaModal").attr("emailtarget");
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
          $(".btn-open-req[data-email='" + email + "']").parent().html(
                "<button  class='btn btn-success' disabled"
                + ">Richiesta inviata</button>"
          );
          chargeAvailabilityRequest();
        } else {
          $("#messaggioErroreBody").html(response.description);
          $("#messaggioErrore").toast('show');
        }
      }
    });
  });

  chargeTableTirocini();

  chargeAvailabilityRequest();

  $('.page-wrapper').on('pageToggled', function () {
    updateValueSpinner()
  });

  $("#caricaScaricaPDFModal").on('show.bs.modal', (e) => {
    var tirocinio = $(e.relatedTarget).data('idtirocinio');
    $("#linkPDF").attr("href", "PdfServlet?tirocinio=" + tirocinio);
    $("#caricaScaricaPDFModal").attr("idtirocinio", tirocinio);
    $("#idtirocinio").val(tirocinio);
  });
});

function chargeTableAziendeContent(callback) {
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
        let riga = "<td>" + azienda.nome + "</td>" +
            "<td>" + azienda.partitaIva + "</td>" +
            "<td>" + azienda.indirizzo + "</td>" +
            "<td>" + azienda.numeroDipendenti + "</td>";
        if (richiesta == null) {
          riga += "<td class='text-center'>" +
              "<button  class='btn btn-success btn-open-req'"
              + " data-toggle=\"modal\" " +
              "data-target='#richiediDisponibilitaModal' " +
              "data-nome=\"" + azienda.nome + "\" data-email= \"" +
              azienda.email + "\">" + "Richiedi </button></td>";
        } else {
          riga += "<td class='text-center'>" +
              "<button  class='btn btn-success' disabled"
              + ">Richiesta inviata</button></td>";
        }
        $("#tableAziendePresenti > tbody:last-child")
        .append("<tr>" + riga + "</tr>");
      });
      callback();

    }
  });
}

function checkIfExistTirocinio() {
  $.ajax({
    url: 'TirocinioServlet',
    type: 'POST',
    data: {
      action: 'viewInternship'
    },
    success: (response) => {
      let exist = false;
      response.forEach((x) => {
        exist = true;
        let oreTotali = x.oreTotali;
        let oreSvolte = x.oreSvolte;
        let percentage = parseInt(oreSvolte / oreTotali * 100);
        $("#percentoOreSvolte").attr("data-value", percentage);
        //updateValueSpinner();
        $("#oreFatte").html(timeConvert(oreSvolte));
        $("#oreTotali").html(timeConvert(oreTotali));
        let email = x.azienda.email;
        $(".btn-open-req[data-email='" + email + "']")
        .parent()
        .parent()
        .remove();
        if (x.stato == "In corso" && x.oreSvolte != 0) {
          caricaAttivitaRegistro(x.id);
        } else {
          caricaAttivitaRegistro(null);
        }
      });
      if (!exist) {
        caricaAttivitaRegistro(null);
      }
      $("#tableAziendePresenti").fadeIn();
    }
  });
}

function chargeTableTirocini() {
  $("#tableTirociniStudente > tbody:last-child").html("");
  $.ajax({
    url: 'TirocinioServlet',
    type: 'POST',
    data: {
      action: 'viewInternship'
    },
    success: (response) => {
      let exist = false;
      response.forEach((x) => {
        exist = true;
        let link = "";
        if (x.stato == "Non completo") {
          x.stato = "Carica/Scarica PDF";
          link = "data-idtirocinio = \"" + x.id + "\""
              + " data-toggle='modal' "
              + " data-target='#caricaScaricaPDFModal'";
        }
        if (x.motivazioni == null) {
          x.motivazioni = "Non disponibili.";
        }
        let riga = "<td>" + x.azienda.nome + "</td>"
            + `<td>${x.tutorEsterno}</td>`
            + `<td>${timeConvert(x.oreSvolte)}</td>`
            + `<td>${timeConvert(x.oreTotali)}</td>`
            + "<td><span class='addbadge badge pointer' " + link + " >"
            + x.stato + "</span></td>"
            + "<td>" + x.motivazioni + "</td>";
        $("#tableTirociniStudente > tbody:last-child").append(
            "<tr>" + riga + "</tr>");
        restyleBadge();
      });
      if (!exist) {
        $("#tableTirociniStudente")
        .html("<tr><td style='text-align: center;' class='mt-2'>" +
            "Non sono presenti richieste.</td></tr>");
      }
    }
  });
}

function caricaAllegato() {
  let tirocinio = $("#caricaScaricaPDFModal").attr("idtirocinio");
  let form = new FormData(document.getElementById("formUploadPDF"));
  $.ajax({
    url: 'PdfServlet',
    type: 'POST',
    enctype: 'multipart/form-data',
    processData: false,
    contentType: false,
    data: form,
    success: (response) => {
      if (response.status == 200) {
        $("#messaggioSuccessoBody").html(response.description);
        $("#messaggioSuccesso").toast('show');
        $("#caricaScaricaPDFModal").modal('toggle');
        chargeTableTirocini();
      } else {
        $("#messaggioErroreBody").html(response.description);
        $("#messaggioErrore").toast('show');
      }
    }
  });
}

function caricaAttivitaRegistro(what) {
  if (what == null) {
    $("#attivitaRegistroStudente")
    .html("<tr><td style='text-align: center;' class='mt-2'>" +
        "Non sono presenti tirocini in corso.</td></tr>");
    return;
  }
  $.ajax({
    url: 'RegistroServlet',
    type: 'POST',
    data: {
      action: 'viewRegister',
      tirocinio: what
    },
    success: (response) => {
      let exist = false;
      response.forEach((e) => {
        exist = true;
        let riga = "<td>" + e.data + "</td>" +
            "<td>" + timeConvert(e.oreSvolte) + "</td>" +
            "<td>" + e.attivita + "</td>";
        $("#attivitaRegistroStudente > tbody:last-child")
        .append("<tr>" + riga + "</tr>");
      });
      if (!exist) {
        $("#attivitaRegistroStudente")
        .html("<tr><td style='text-align: center;' class='mt-2'>" +
            "Non sono presenti attivit&agrave;.</td></tr>");
      }
    }
  })
}

function chargeAvailabilityRequest() {
  $("#richiesteDisponibilitaStudente > tbody:last-child").html("");
  $("#richiesteDisponibilitaStudente > thead").fadeIn();
  $.ajax({
    url: 'RichiestaDServlet',
    type: 'POST',
    data: {
      action: 'viewRequest'
    },
    success: (response) => {
      let exist = false;
      response.forEach((e) => {
        exist = true;
        let riga = `<td>${e.azienda.nome}</td>` +
                   `<td><span class='addbadge badge pointer'>${e.stato}</span></td>` +
                   `<td>${e.motivazioni}</td>`;
        $("#richiesteDisponibilitaStudente > tbody:last-child")
        .append("<tr>" + riga + "</tr>");
      });
      if (!exist) {
        $("#richiesteDisponibilitaStudente > thead").fadeOut();
        $("#richiesteDisponibilitaStudente > tbody:last-child")
        .html("<tr><td style='text-align: center;' class='mt-2'>" +
            "Non sono presenti richieste.</td></tr>");
        return;
      }
      restyleBadge();
    }
  })
}