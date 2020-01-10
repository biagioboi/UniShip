<div class="tab-pane fade" id="pills-aggiungiazienda" role="tabpanel"
     aria-labelledby="pills-view-aggiungiazienda">
    <div class="mt-3 col-12">
        <h3 class="text-center">Registrazione Azienda</h3>
        <hr>
        <form id="formAddAzienda">
            <div class="row mt-3">
                <div class="form-group col-md-4">
                    <input type="text" class="form-control" name="nome" id="nomeAzienda" placeholder="Nome Azienda" required>
                </div>
                <div class="form-group col-md-4">
                    <input type="text" class="form-control" name="partitaIva" id="partitaIva" placeholder="Partita IVA" required>
                </div>
                <div class="form-group col-md-4">
                    <input type="email" class="form-control" name="email" id="email" placeholder="Email" required>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-5">
                    <input type="text" class="form-control" name="indirizzo" id="indirizzo" placeholder="Indirizzo" required>
                </div>
                <div class="form-group col-md-3">
                    <input type="text" class="form-control" name="nomeRappresentante" id="nomeRappresentante" placeholder="Nome Rappresentante" required>
                </div>
                <div class="form-group col-md-2">
                    <input type="text" class="form-control" name="codiceAteco" id="codiceAteco" placeholder="Codice ATECO" required>
                </div>
                <div class="form-group col-md-2">
                    <input type="number" class="form-control" name="numDipendenti" id="numDipendenti" placeholder="Numero Dipendenti" required>
                </div>
            </div>
            <div class="row col-md-12 text-center">
                <button class="btn btn-success">Aggiungi</button>
            </div>
        </form>
    </div>
</div>
