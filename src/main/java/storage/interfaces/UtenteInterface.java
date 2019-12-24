package storage.interfaces;

import java.util.ArrayList;
import storage.beans.Utente;


/*cambiata signature del metodo save, restituisce boolean e non int*/

public interface UtenteInterface {

  boolean doCheckLogin(String email, String password);

  boolean doCheckRegister(String email);

  ArrayList<Utente> doRetrieveAll();

  Utente doRetrieveByKey(String email);

  boolean doSave(Utente utente);
}
