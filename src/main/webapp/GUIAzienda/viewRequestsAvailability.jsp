<div class="tab-pane fade show active" id="pills-richiesteAzienda" role="tabpanel"
     aria-labelledby="pills-view-richiesteAzienda">
    <div class="table-responsive font-size-sm">
        <table id="tableRichiesteDisponibilita" class="table table-hover mb-0 min-size-td" style="display: none">
            <thead>
            <tr>
                <th>Matricola</th>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Messaggio</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<div emailTarget = "" class="modal fade" id="rispondiDisponibilitaModal" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="nomeStudenteRichiesta">Mario Rossi - matricola</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="motivazioni" class="col-form-label">Motivazioni:</label>
                        <textarea class="form-control" id="motivazioni" required></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" onclick="rispondiRichiesta('rifiutata');">Rifiuta</button>
                <button type="button" class="btn btn-success" onclick="rispondiRichiesta('accettata');">Accetta</button>
            </div>
        </div>
    </div>
</div>