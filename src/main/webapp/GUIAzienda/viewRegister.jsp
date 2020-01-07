<div class="tab-pane fade" id="pills-tirocinanti" role="tabpanel"
     aria-labelledby="pills-view-aziende">
    <div class="table-responsive font-size-sm">
        <table id="tableStudentiTirocinio" class="table table-hover mb-0 ">
            <thead>
            <tr>
                <th>Matricola</th>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Codice Fiscale</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<div idtirocinio ="" class="modal fade" id="aggiungiOreModal" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="nomeStudenteAttRegistro"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="table-responsive font-size-sm">
                    <table id="tableOreSvolte" class="table table-hover mb-0 min-size-td">
                        <thead>
                        <tr>
                            <th>Data</th>
                            <th>ore svolte</th>
                            <th>attivita</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <hr>
                <form class="form-row" id="formAddActivity">
                    <div class="form-group  my-auto col-md-5 col-sm-12">
                  <textarea class="form-control" id="descrizione"
                            placeholder="Descrizione attivita'" rows="3" required></textarea>
                    </div>
                    <div class="form-group my-auto col-md-3 col-sm-12 row ">
                        <label for="giorno" class="col-4 col-form-label">Data : </label>
                        <input class="form-control col-7" type="date" value="" id="giorno" name="girono"
                               required>
                    </div>
                    <div class="form-group my-auto col-md-3 col-sm-12 row">
                        <label for="ore" class="col-3 col-form-label">Ore : </label>
                        <input class="form-control col-4" type="time" value="" id="ore" name="ore"
                               required>
                    </div>
                    <div class="form-group my-auto col-md-1 col-sm-12">
                        <button type="submit" class="btn btn-success m-2" id="btnAddActivity">Aggiungi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>