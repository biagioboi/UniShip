<div class="tab-pane fade show active" id="pills-richiesteufficio" role="tabpanel"
     aria-labelledby="pills-view-richiesteufficio">

    <div class="d-flex justify-content-end pb-3">
        <form id ="formShowintership" class="form-inline my-2">
            <label class="mr-3" for="stato">Stato : </label>
            <select class="form-control custom-select mr-3" id="stato">
                <option>Tutti</option>
                <option>Non completo</option>
                <option>Da valutare</option>
                <option>In corso</option>
                <option>Da convalidare</option>
                <option>Rifiutata</option>
                <option>Accettata</option>
            </select>
            <input class="form-control mr-3" type="text" placeholder="Email azienda" id="azienda">
            <input class="form-control mr-3" type="text" placeholder="Email studente" id="studente">
            <input type="submit" value="filtra" class="btn btn-success">
        </form>
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
                    <label for="motivazioniValidazioneTirocinio"
                           class="col-form-label">Motivazioni:</label>
                    <textarea class="form-control" id="motivazioniValidazioneTirocinio"></textarea>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-success"
                        onclick="respondTirocinio('Accettata');">Accetta
                </button>
                <button type="button" class="btn btn-danger"
                        onclick="respondTirocinio('Rifiutata');">Rifiuta
                </button>
            </div>
        </div>
    </div>
</div>
