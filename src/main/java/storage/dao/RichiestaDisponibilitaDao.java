package storage.dao;

/* nome classe cambiato */

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.interfaces.RichiestaDisponibilitaInterface;

/*nome classe cambiato*/

public class RichiestaDisponibilitaDao implements RichiestaDisponibilitaInterface {

  /**
   * Questo metodo si occupa di trovare le richieste di disponibilità associate all'azienda passata
   * come parametro.
   *
   * @param azienda l'Azienda di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo RichiestaDisponibilita
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null.
   */
  @Override
  public ArrayList<RichiestaDisponibilita> doRetrieveByAzienda(Azienda azienda)
      throws SQLException {
    return null;
  }

  /**
   * Questo metodo si occupa di trovare le richieste di disponibilità associate allo studente
   * passato come parametro.
   *
   * @param studente lo studente di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo RichiestaDisponibilita
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa studente == null.
   */
  @Override
  public ArrayList<RichiestaDisponibilita> doRetrieveByStudente(Studente studente)
      throws SQLException {
    return null;
  }

  /**
   * Questo metodo si occupa di cambiare gli attribuiti di un oggetto RichiestaDisponibilita
   * presente nel database.
   *
   * @param richiesta lo richiesta di cui si vogliono cambiare i valori degli attributi.
   * @return true se la modifica avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa richiesta == null.
   */
  @Override
  public boolean doChange(RichiestaDisponibilita richiesta) throws SQLException {
    return false;
  }

  /**
   * Questo metodo occupa di inserire nel Database una nuova RichiestaDisponibilita.
   *
   * @param richiesta lo richiesta di cui si vogliono cambiare i valori degli attributi.
   * @return true se l'inserimento avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa richiesta == null.
   */
  @Override
  public boolean doSave(RichiestaDisponibilita richiesta) throws SQLException {
    return false;
  }
}
