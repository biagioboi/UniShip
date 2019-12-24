package storage.interfaces;

import java.util.ArrayList;
import storage.beans.Studente;

/*cambiata signature del metodo save, restituisce boolean e non int*/

/* TODO:dobbiamo capire se va bene qui il changeAzienda o è un errore */

public interface StudenteInterface {

  public boolean doChangeAzienda(String email, Studente studente);

  public ArrayList<Studente> doRetrieveAll();

  public Studente doRetrieveByKey(String email);

  public boolean doSave(Studente studente);
}
