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
    <div class="modal-dialog " role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Carica PDF</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <h6> Puoi scaricare il file da <a id="linkPDF" href="">qui</a></h6>
                <hr>
                <div class="form-row">
                    <form id="formUploadPDF">
                        <input type="hidden" name="action" value="uploadPdf">
                        <input type="hidden" id="idtirocinio" name="tirocinio" value="">
                        <div class="form-group">
                            <label for="custom-file-input">Carica il file :</label>
                            <input type="file" class="form-control-file" id="custom-file-input">
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" onclick="caricaAllegato();">Accetta
                </button>
            </div>
        </div>
    </div>
</div>