package storage.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Azienda;

public interface AziendaInterface {

  /**
   * Questo metodo si occupa di modificare un'azienda presente nel Database.
   *
   * @param azienda l'Azienda che si vuole aggiornare.
   * @return true se la modifica avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null.
   */
  boolean doChange(Azienda azienda) throws SQLException;

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Azienda dal Database.
   *
   * @return ArrayList di oggetti di tipo Azienda.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  ArrayList<Azienda> doRetrieveAll() throws SQLException;

  /**
   * Questo metodo si occupa di trovare l'azienda che ha associata l'email passata come parametro.
   *
   * @param email l'email di una determinata azinda.
   * @return l'Azienda che ha come email quella specificata nel parametro.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa email == null.
   */
  Azienda doRetrieveByKey(String email) throws SQLException;

  /**
   * Questo metodo si occupa di inserire nel Database una nuova azienda.
   *
   * @param azienda l'Azienda da inserire nel Database.
   * @return true se l'inserimento avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null.
   */
  boolean doSave(Azienda azienda) throws SQLException;
}
