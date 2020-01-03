<div class="tab-pane fade show active" id="pills-aziende" role="tabpanel"
     aria-labelledby="pills-view-aziende">
    <div class="table-responsive font-size-sm">
        <table id="tableAziendePresenti" class="table table-hover mb-0 ">
            <thead>
            <tr>
                <th>Nome</th>
                <th>Codice Ateco</th>
                <th>Indirizzo</th>
                <th>Numero dipendenti</th>
                <th class="text-center">Richiesta Disponibilit&agrave;</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<div emailTarget="" class="modal fade" id="richiediDisponibilitaModal" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="nomeAzienda">Azienda 1</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="messaggio" class="col-form-label">Messaggio:</label>
                    <textarea class="form-control" id="messaggio" required></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Annulla</button>
                <button type="button" class="btn btn-success" id="btnSendRequest">Richiedi</button>
            </div>
        </div>
    </div>
</div>