package storage.dao;

/* nome classe cambiato */

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Studente;
import storage.interfaces.StudenteInterface;

/*nome classe cambiato*/

public class StudenteDao implements StudenteInterface {

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Studente dal Database.
   *
   * @return ArrayList di oggetti di tipo Studente
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  @Override
  public ArrayList<Studente> doRetrieveAll() throws SQLException {
    return null;
  }

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Utente dal Database.
   *
   * @param email un ogggetto di dito String che rapprensenta Email di una determinato
   *     studente.
   * @return Oggetto di tipo Studente
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa email == null
   */
  @Override
  public Studente doRetrieveByKey(String email) throws SQLException {
    return null;
  }

  /**
   * Questo metodo si occupa di inserire nel Database una entry nella tabella studente.
   *
   * @param studente un oggetto di tipo Studente
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa studente == null
   */
  @Override
  public boolean doSave(Studente studente) throws SQLException {
    return false;
  }
}
