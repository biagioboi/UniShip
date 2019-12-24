package storage.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.AttivitaRegistro;
import storage.beans.Tirocinio;

/* il retrieve prende come parametro un tirocinio, non l'identificativo e il do save
restituisce un bool */

public interface AttivitaRegistroInterface {

  /**
   * Questo metodo si occupa di prelevare tutte le attivita svolte in un detirminato tirocinio.
   *
   * @param tirocinio un oggetto di tipo Tirocinio.
   * @return ArrayList di oggetti di tipo  AttivitaTirocinio.
   * @throws SQLException
   *
   * @precondition tirocinio != null e corrisponde ad un tirocinio presente nel database.
   */
  ArrayList<AttivitaRegistro> doRetrieveByTirocinio(Tirocinio tirocinio) throws SQLException;

  /**
   * Questo metodo si occupa di inserire nel Database una entry nella tabella attivita_registro.
   *
   * @param attivita un oggetto di tipo AttivitaRegistro
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @throws SQLException
   *
   * @precondition attivita != null
   */
  boolean doSave(AttivitaRegistro attivita) throws SQLException;

}
