<div class="tab-pane fade show active" id="pills-richiesteadmin" role="tabpanel"
     aria-labelledby="pills-view-richiesteadmin">
    <div class="table-responsive font-size-sm">
        <table id="richiesteTirocinioAdmin" class="table table-hover mb-0 min-size-td">
            <thead>
            <tr>
                <th>Studente</th>
                <th>Azienda</th>
                <th>stato</th>
                <th>Motivazioni</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>



<div idtirocinio="" class="modal fade" id="valutaTirocinio" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog " role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="nomeStudenteAdmin"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <h6> Puoi scaricare il file da <a id="linkPDF" href="">qui</a></h6>
                <div class="form-group">
                    <label for="motivazioniValidazioneTirocinio" class="col-form-label">Motivazioni:</label>
                    <textarea class="form-control" id="motivazioniValidazioneTirocinio"></textarea>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-success" onclick="respondTirocinio('Accettata');">Accetta</button>
                <button type="button" class="btn btn-danger" onclick="respondTirocinio('Rifiutata');">Rifiuta</button>
            </div>
        </div>
    </div>
</div>
