package storage.interfaces;

import java.util.ArrayList;
import storage.beans.Azienda;

/*cambiata signature del metodo save e change, restituisce boolean e non int e void*/

public interface AziendaInterface {

  public boolean doChange(Azienda azienda);

  public ArrayList<Azienda> doRetrieveAll();

  public Azienda doRetrieveByKey(String email);

  public boolean doSave(Azienda azienda);
}
