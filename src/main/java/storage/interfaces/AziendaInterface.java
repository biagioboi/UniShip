package storage.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Azienda;

/*cambiata signature del metodo save e change, restituisce boolean e non int e void*/

public interface AziendaInterface {

  /**
   * Questo metodo si occupa di modificare un'azienda presente nel Database.
   * @param azienda l'Azienda che si vuole aggiornare.
   * @return true se la modifica avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @precodition azienda != null
   */
  public boolean doChange(Azienda azienda) throws SQLException;

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Azienda dal Database.
   * @return ArrayList di oggetti di tipo Azienda.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  public ArrayList<Azienda> doRetrieveAll() throws SQLException;

  /**
   * Questo metodo si occupa di trovare l'azienda che ha associata l'email passata come parametro.
   * @param email l'email di una determinata azinda.
   * @return l'Azienda che ha come email quella specificata nel parametro.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @precondiction email != null e che è associata ad un'Azienda nel Database.
   */
  public Azienda doRetrieveByKey(String email) throws SQLException;

  /**
   * Questo metodo si occupa di inserire nel Database una nuova azienda.
   * @param azienda l'Azienda da inserire nel Database.
   * @return true se l'inserimento avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @precondition azienda != null e che non esiste già nel Database.
   */
  public boolean doSave(Azienda azienda) throws SQLException;
}
