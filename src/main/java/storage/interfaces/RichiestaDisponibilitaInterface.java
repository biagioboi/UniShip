package storage.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;

public interface RichiestaDisponibilitaInterface {


  /**
   * Questo metodo si occupa di trovare le richieste di disponibilità associate all'azienda passata
   * come parametro.
   *
   * @param azienda l'Azienda di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo RichiestaDisponibilita
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null.
   */
  ArrayList<RichiestaDisponibilita> doRetrieveByAzienda(Azienda azienda) throws SQLException;

  /**
   * Questo metodo si occupa di trovare le richieste di disponibilità associate allo studente
   * passato come parametro.
   *
   * @param studente lo studente di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo RichiestaDisponibilita
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa studente == null.
   */
  ArrayList<RichiestaDisponibilita> doRetrieveByStudente(Studente studente) throws SQLException;

  /**
   * Questo metodo si occupa di cambiare gli attribuiti di un oggetto RichiestaDisponibilita
   * presente nel database.
   *
   * @param richiesta lo richiesta di cui si vogliono cambiare i valori degli attributi.
   * @return true se la modifica avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa richiesta == null.
   */
  boolean doChange(RichiestaDisponibilita richiesta) throws SQLException;

  /**
   * Questo metodo occupa di inserire nel Database una nuova RichiestaDisponibilita.
   *
   * @param richiesta lo richiesta di cui si vogliono cambiare i valori degli attributi.
   * @return true se l'inserimento avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa richiesta == null.
   */
  boolean doSave(RichiestaDisponibilita richiesta) throws SQLException;

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti RichiestaDisponibilita dal Database.
   *
   * @return ArrayList di oggetti di tipo RichiestaDisponibilita.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  ArrayList<RichiestaDisponibilita> doRetrieveAll() throws SQLException;
}
