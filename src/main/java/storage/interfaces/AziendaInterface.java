package storage.interfaces;

import java.util.ArrayList;
import storage.beans.Azienda;

public interface AziendaInterface {

  public void doChange(Azienda azienda);

  public ArrayList<Azienda> doRetrieveAll();

  public Azienda doRetrieveByKey(String email);

  public int doSave(Azienda azienda);
}
