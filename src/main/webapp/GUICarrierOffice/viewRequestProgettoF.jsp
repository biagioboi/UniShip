
<div class="tab-pane fade show active" id="pills-richiesteufficio" role="tabpanel"
     aria-labelledby="pills-view-richiesteufficio">

    <div class="col-12 mt-2 mb-2">
        <div class="row">
            <div class="col-md-5 form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password"
                       placeholder="Password" required>

            </div>
            <div class="col-md-5">
                ciao
            </div>
            <div class="col-md-2">
                ciao
            </div>

        </div>
    </div>

    <div class="table-responsive font-size-sm">
        <table class="table table-hover mb-0 " id="richiesteTirocinioUfficio">
            <thead>
            <tr>
                <th>Studente</th>
                <th>Azienda</th>
                <th>Tutor Esterno</th>
                <th>Ore Svolte</th>
                <th>Ore Totali</th>
                <th>Stato</th>
                <th class="text-center">#</th>
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
                <h5 class="modal-title" id="nomeStudenteUfficio"></h5>
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
