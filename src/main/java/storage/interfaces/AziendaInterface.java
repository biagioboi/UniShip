package storage.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Azienda;

/*cambiata signature del metodo save e change, restituisce boolean e non int e void*/

public interface AziendaInterface {

  /**
   * Questo metodo si occupa di modificare gli attributi di un entry nella tabella azienda.
   * @param azienda un oggetto di tipo azienda
   * @return true se la modifica avviene con successo , false altrimenti
   * @throws SQLException
   *
   * @precodition azienda != null
   */
  public boolean doChange(Azienda azienda) throws SQLException;

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Azienda dal Database.
   * @return ArrayList di oggetti di tipo  Azienda.
   * @throws SQLException
   */
  public ArrayList<Azienda> doRetrieveAll() throws SQLException;

  /**
   * @param email un ogggetto di dito String che rapprensenta Email di una determinata azienda.
   * @return Oggetto di tipo Azienda
   * @throws SQLException
   *
   * @precondiction email != null e deve esitere nel Database
   */
  public Azienda doRetrieveByKey(String email) throws SQLException;

  /**
   * Questo metodo si occupa di inserire nel Database una entry nella tabella azienda.
   *
   * @param azienda un oggetto di tipo Azienda
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @throws SQLException
   *
   * @precondition azienda != null
   */
  public boolean doSave(Azienda azienda) throws SQLException;
}
