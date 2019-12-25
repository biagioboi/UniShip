package storage.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Utente;


/*cambiata signature del metodo save, restituisce boolean e non int*/

public interface UtenteInterface {

  /**
   * Questo metodo si occupa di verificare la correttezza dei dati di accesso.
   * @param email un ogggetto di dito String che rapprensenta l'email di una determinato utente.
   * @param password un ogggetto di dito String che rapprensenta la password di una determinato utente.
   * @return true se le informazioni combaciano, false altrimenti
   * @throws SQLException
   *
   * @precondiction email != null e deve esistere nel database e password != null
   */

  boolean doCheckLogin(String email, String password) throws SQLException;

  /**
   * Questo metodo si occupa di verficare data una email se essa e' gia' presente nel Database.
   * @param email un ogggetto di dito String che rapprensenta l'email di una determinato utente.
   * @return true se e' prensente, false altrimenti
   * @throws SQLException
   */
  boolean doCheckRegister(String email) throws SQLException;

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Utente dal Database.
   * @return ArrayList di oggetti di tipo  Utente
   * @throws SQLException
   */
  ArrayList<Utente> doRetrieveAll() throws SQLException;

  /**
   * @param email un ogggetto di dito String che rapprensenta Email di una determinato utente.
   * @return Oggetto di tipo Utente
   * @throws SQLException
   *
   * @precondiction email != null e deve esitere nel Database
   */
  Utente doRetrieveByKey(String email) throws SQLException;

  /**
   * Questo metodo si occupa di inserire nel Database una entry nella tabella utente.
   *
   * @param utente un oggetto di tipo Utente
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @throws SQLException
   *
   * @precondition utente != null
   */
  boolean doSave(Utente utente) throws  SQLException;
}
