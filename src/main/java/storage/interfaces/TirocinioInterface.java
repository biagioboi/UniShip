package storage.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;

public interface TirocinioInterface {

  /**
   * Questo metodo si occupa di cambiare gli attribuiti di un oggetto Tirocinio presente nel
   * database.
   *
   * @param tirocinio lo richiesta di cui si vogliono cambiare i valori degli attributi.
   * @return true se la modifica avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa tirocinio == null.
   */
  boolean doChange(Tirocinio tirocinio);

  /**
   * Questo metodo si occupa di trovare i tirocini associate allo studente passata come parametro.
   *
   * @param studente l'Azienda di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo Tirocinio
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa studente == null.
   */

  ArrayList<Tirocinio> doRetrieveByStudente(Studente studente);

  /**
   * Questo metodo si occupa di trovare i tirocini associate all'azienda passata come parametro.
   *
   * @param azienda l'Azienda di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo Tirocinio
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null.
   */
  ArrayList<Tirocinio> doRetrieveByAzienda(Azienda azienda);

  /**
   * Questo metodo si occupa di trovare il tirocinio che ha associata id passato per parametro.
   *
   * @param id un valore di tipo intero
   * @return il tirocinio che ha come id quello specificato nel parametro.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa tirocinio == null
   */
  Tirocinio doRetrieveByKey(int id);

  /**
   * Questo metodo si occupa di inserire nel Database una oggetto di tipo Tirocinio.
   *
   * @param tirocinio un oggetto di tipo Tirocinio
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa tirocinio == null
   */
  boolean doSave(Tirocinio tirocinio) throws SQLException;
}
