<div class="tab-pane fade" id="pills-richieste" role="tabpanel"
     aria-labelledby="pills-view-richieste">
    <div class="table-responsive font-size-sm">
        <table id="richiesteTirocinioStudente" class="table table-hover mb-0 min-size-td">
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


<div idtirocinio="" class="modal fade" id="caricaScaricaPDFModal" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Carica PDF</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <a style="cursor: pointer;" id="linkPDF" href="post.php">Scarica PDF</a>
                    </div>
                    <div class="form-group">
                        <label for="motivazioni" class="col-form-label">Motivazioni:</label>
                        <textarea class="form-control" id="motivazioni" required></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" onclick="rispondiRichiesta('Rifiutata');">Rifiuta</button>
                <button type="button" class="btn btn-success" onclick="rispondiRichiesta('Accettata');">Accetta</button>
            </div>
        </div>
    </div>
</div>