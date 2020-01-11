<div class="tab-pane fade" id="pills-aggiungiazienda" role="tabpanel"
     aria-labelledby="pills-view-aggiungiazienda">
    <div class="mt-3 col-12">
        <h3 class="text-center">Registrazione Azienda</h3>
        <hr>
        <div class="container-fluid my-4 mx-2">
            <form id="formAddAzienda" class="form-row">
                <div class="form-group col-md-6 col-sm-12">
                    <input type="text" class="form-control" name="nome" id="nomeAzienda"
                           placeholder="Nome Azienda" required>
                </div>
                <div class="form-group col-md-6 col-sm-12">
                    <input type="text" class="form-control" name="partitaIva" id="partitaIva"
                           placeholder="Partita IVA" required>
                </div>
                <div class="form-group col-md-6 col-sm-12">
                    <input type="email" class="form-control" name="email" id="email"
                           placeholder="Email" required>
                </div>
                <div class="form-group col-md-6 col-sm-12">
                    <input type="text" class="form-control" name="indirizzo" id="indirizzo"
                           placeholder="Indirizzo" required>
                </div>
                <div class="form-group col-md-6 col-sm-12">
                    <input type="text" class="form-control" name="nomeRappresentante"
                           id="nomeRappresentante" placeholder="Nome Rappresentante" required>
                </div>
                <div class="form-group col-md-3 col-sm-6">
                    <input type="text" class="form-control" name="codiceAteco" id="codiceAteco"
                           placeholder="Codice ATECO" required>
                </div>
                <div class="form-group col-md-3 col-sm-6">
                    <input type="number" class="form-control" name="numDipendenti"
                           id="numDipendenti"
                           placeholder="Numero Dipendenti" required>
                </div>
                <div class="col-12">
                    <div class="float-right">
                        <button class="btn btn-success">Aggiungi</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
