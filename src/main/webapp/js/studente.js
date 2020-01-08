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
          $(".btn-open-req[data-email='" + email + "']").fadeOut('500');
        } else {
          $("#messaggioErroreBody").html(response.description);
          $("#messaggioErrore").toast('show');
        }
      }
    });
  });


  chargeTableTirocini();

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
              "<button  class='btn btn-success btn-open-req' data-toggle=\"modal\" "
              +
              "data-target='#richiediDisponibilitaModal' " +
              "data-nome=\"" + azienda.nome + "\" data-email= \"" +
              azienda.email + "\">" + "Richiedi </button></td>";
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
      response.forEach((x) => {
        let oreTotali = x.oreTotali;
        let oreSvolte = x.oreSvolte;
        let percentage = parseInt(oreSvolte / oreTotali * 100);
        $("#percentoOreSvolte").attr("data-value", percentage);
        updateValueSpinner();
        $("#oreFatte").html(timeConvert(oreSvolte));
        $("#oreTotali").html(timeConvert(oreTotali));
        let email = x.azienda.email;
        $(".btn-open-req[data-email='" + email + "']")
        .parent()
        .parent()
        .remove();
      });
      $("#tableAziendePresenti").fadeIn();
    }
  });
}

function chargeTableTirocini() {
  $("#richiesteTirocinioStudente > tbody:last-child").html("");
  $.ajax({
    url: 'TirocinioServlet',
    type: 'POST',
    data: {
      action: 'viewInternship'
    },
    success: (response) => {
      response.forEach((x) => {
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
        let riga = "<td>" + x.studente.matricola + "</td>"
            + "<td>" + x.azienda.nome + "</td>"
            + "<td><span class='addbadge badge pointer' " + link + " >"
            + x.stato + "</span></td>"
            + "<td>" + x.motivazioni + "</td>";
        $("#richiesteTirocinioStudente > tbody:last-child").append(
            "<tr>" + riga + "</tr>");
        restyleBadge();
      });
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