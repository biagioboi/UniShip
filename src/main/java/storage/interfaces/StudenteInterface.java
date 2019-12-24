package storage.interfaces;

import java.util.ArrayList;
import storage.beans.Studente;

public interface StudenteInterface {

  public void doChangeAzienda(String email, Studente studente);

  public ArrayList<Studente> doRetrieveAll();

  public Studente doRetrieveByKey(String email);

  public int doSave(Studente studente);
}
