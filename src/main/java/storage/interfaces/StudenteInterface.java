package storage.interfaces;

import java.util.ArrayList;
import storage.beans.Studente;

/*cambiata signature del metodo save, restituisce boolean e non int*/

/* TODO:dobbiamo capire se va bene qui il changeAzienda o è un errore */

public interface StudenteInterface {

  boolean doChangeAzienda(String email, Studente studente);

  ArrayList<Studente> doRetrieveAll();

  Studente doRetrieveByKey(String email);

  boolean doSave(Studente studente);
}
