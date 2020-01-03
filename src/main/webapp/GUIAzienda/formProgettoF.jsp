<div class="modal fade" id="compilaProgettoFormativoModal" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="nomeAlunno">Mario Rossi - matricola</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <h6>Compila il progetto formativo</h6>
                <form class="m-3">
                    <div class="form-group">
                        <input class="form-control" type="number" placeholder="cfu" id="numeroCfu" required>
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="text" placeholder="Sede Svolgimento"
                               id="sedeSvolgimento" required>
                    </div>
                    <div class="form-group">
                <textarea class="form-control" id="obiettivi" placeholder="Obiettivi"
                          rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                <textarea class="form-control" id="competenze" placeholder="Competenze da acquisire"
                          rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                <textarea class="form-control" id="attivita" placeholder="Attvit&agrave;"
                          rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                <textarea class="form-control" id="modalita" placeholder="Modalita di svolgimento"
                          rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="text" placeholder="Orario Lavorativo"
                               id="orarioLavorativo" required>
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="text" placeholder="Numero RC"
                               id="numeroRc" required>
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="text" placeholder="Polizza assicurativa infortuni"
                               id="polizza" required>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="dataInizio" class="col-4 col-form-label">Data inizio :</label>
                            <div class="col-8">
                                <input class="form-control" type="date" value="2011-08-19" id="dataInizio"
                                       required>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="dataFine" class="col-4 col-form-label">Data fine :</label>
                            <div class="col-8">
                                <input class="form-control" type="date" value="2011-08-19" id="dataFine"
                                       required>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Annulla</button>
                <button type="button" class="btn btn-success">Invia</button>
            </div>
        </div>
    </div>
</div>
