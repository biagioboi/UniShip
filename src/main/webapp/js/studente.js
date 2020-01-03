$(() => {
  chargeTableAziendeContent();
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
              "<button  class='btn btn-success' data-toggle='modal' " +
              "data-target='#richiediDisponibilitaModal'>" +
              "Richiedi </button>";
        }
        $("#tableAziendePresenti > tbody:last-child")
        .append("<tr>" + riga + "</tr>");
      });

      }
  });
}