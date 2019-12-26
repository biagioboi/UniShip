package storage.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.AttivitaRegistro;
import storage.beans.Tirocinio;

/* il retrieve prende come parametro un tirocinio, non l'identificativo e il do save
restituisce un bool */

public interface AttivitaRegistroInterface {

  /**
   * Questo metodo si occupa di prelevare tutte le attività svolte in un detirminato tirocinio.
   * @param tirocinio il Tirocinio di cui si vogliono sapere le attività.
   * @return ArrayList di oggetti di tipo  AttivitaTirocinio contenente tutte le attività di un
   *         tirocinio.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @precondition tirocinio != null e presente nel database.
   */
  ArrayList<AttivitaRegistro> doRetrieveByTirocinio(Tirocinio tirocinio) throws SQLException;

  /**
   * Questo metodo si occupa di salvare un'AttivitaRegistro nel Database.
   * @param attivita l'AttivitaRegistro da inserire nel Database
   * @return true se l'inserimento avviene con successo, false altrimenti.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @precondition attivita != null
   */
  boolean doSave(AttivitaRegistro attivita) throws SQLException;

}
