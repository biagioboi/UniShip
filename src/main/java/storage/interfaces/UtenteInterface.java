package storage.interfaces;

import java.util.ArrayList;
import storage.beans.Utente;


/*cambiata signature del metodo save, restituisce boolean e non int*/

public interface UtenteInterface {

  public boolean doCheckLogin(String email, String password);

  public boolean doCheckRegister(String email);

  public ArrayList<Utente> doRetrieveAll();

  public Utente doRetrieveByKey(String email);

  public boolean doSave(Utente utente);
}
