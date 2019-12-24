package storage.interfaces;

import java.util.ArrayList;
import storage.beans.AttivitaRegistro;
import storage.beans.Tirocinio;

/* il retrieve prende come parametro un tirocinio, non l'identificativo e il do save
restituisce un bool */

public interface AttivitaRegistroInterface {

  public ArrayList<AttivitaRegistro> doRetrieveByTirocinio(Tirocinio tirocinio);

  public boolean doSave(AttivitaRegistro attivita);

}
