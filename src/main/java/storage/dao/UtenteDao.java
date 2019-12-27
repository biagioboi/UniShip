package storage.dao;
/* nome classe cambiato */

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Utente;
import storage.interfaces.UtenteInterface;

/*nome classe cambiato*/

public class UtenteDao implements UtenteInterface {

  /**
   * Questo metodo si occupa di verificare la correttezza dei dati di accesso.
   *
   * @param email un ogggetto di dito String che rapprensenta l'email di una determinato
   *     utente.
   * @param password un ogggetto di dito String che rapprensenta la password di una determinato
   *     utente.
   * @return true se le informazioni combaciano, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa uno email == null oppure password
   *     == null.
   */
  @Override
  public boolean doCheckLogin(String email, String password) throws SQLException {
    return false;
  }

  /**
   * Questo metodo si occupa di verficare data una email se essa e' gia' presente nel Database.
   *
   * @param email un ogggetto di dito String che rapprensenta l'email di una determinato
   *     utente.
   * @return true se e' prensente, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa uno email == null
   */
  @Override
  public boolean doCheckRegister(String email) throws SQLException {
    return false;
  }

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Utente dal Database.
   *
   * @return ArrayList di oggetti di tipo Utente
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  @Override
  public ArrayList<Utente> doRetrieveAll() throws SQLException {
    return null;
  }

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Utente dal Database.
   *
   * @param email un ogggetto di dito String che rapprensenta Email di una determinato utente.
   * @return Oggetto di tipo Utente
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa email == null
   */
  @Override
  public Utente doRetrieveByKey(String email) throws SQLException {
    return null;
  }

  /**
   * Questo metodo si occupa di inserire nel Database una entry nella tabella utente.
   *
   * @param utente un oggetto di tipo Utente
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa utente == null
   */
  @Override
  public boolean doSave(Utente utente) throws SQLException {
    return false;
  }
}
