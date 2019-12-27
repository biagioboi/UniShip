package storage.dao;
/* nome classe cambiato */

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.interfaces.TirocinioInterface;

/*nome classe cambiato*/

public class TirocinioDao implements TirocinioInterface {

  /**
   * Questo metodo si occupa di cambiare gli attribuiti di un oggetto Tirocinio presente nel
   * database.
   *
   * @param tirocinio lo richiesta di cui si vogliono cambiare i valori degli attributi.
   * @return true se la modifica avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa tirocinio == null.
   */
  @Override
  public boolean doChange(Tirocinio tirocinio) {
    return false;
  }

  /**
   * Questo metodo si occupa di trovare i tirocini associate allo studente passata come parametro.
   *
   * @param studente l'Azienda di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo Tirocinio
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa studente == null.
   */
  @Override
  public ArrayList<Tirocinio> doRetrieveByStudente(Studente studente) {
    return null;
  }

  /**
   * Questo metodo si occupa di trovare i tirocini associate all'azienda passata come parametro.
   *
   * @param azienda l'Azienda di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo Tirocinio
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null.
   */
  @Override
  public ArrayList<Tirocinio> doRetrieveByAzienda(Azienda azienda) {
    return null;
  }

  /**
   * Questo metodo si occupa di trovare il tirocinio che ha associata id passato per parametro.
   *
   * @param id un valore di tipo intero
   * @return il tirocinio che ha come id quello specificato nel parametro.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa tirocinio == null
   */
  @Override
  public Tirocinio doRetrieveByKey(int id) {
    return null;
  }

  /**
   * Questo metodo si occupa di inserire nel Database una oggetto di tipo Tirocinio.
   *
   * @param tirocinio un oggetto di tipo Tirocinio
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa tirocinio == null
   */
  @Override
  public boolean doSave(Tirocinio tirocinio) throws SQLException {
    return false;
  }
}
