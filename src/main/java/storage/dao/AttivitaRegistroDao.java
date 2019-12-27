package storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import storage.DatabaseManager;
import storage.beans.AttivitaRegistro;
import storage.beans.Tirocinio;
import storage.interfaces.AttivitaRegistroInterface;

/* nome classe cambiato aggiunde costanti alla classe , verificare dipendenza con Tirocinio
*  ATTENZIONE questa classe NON dipende da TirocinioDao */

public class AttivitaRegistroDao implements AttivitaRegistroInterface {

  /**
   * Questo metodo si occupa di prelevare tutte le attività svolte in un detirminato tirocinio.
   *
   * @param tirocinio il Tirocinio di cui si vogliono sapere le attività.
   * @return ritorna un ArrayList di oggetti di tipo AttivitaTirocinio contenente tutte le attività
   *     di un tirocinio
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa un tirocinio == null.
   */
  @Override
  public synchronized ArrayList<AttivitaRegistro> doRetrieveByTirocinio(Tirocinio tirocinio)
      throws SQLException {
    if (tirocinio == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ArrayList<AttivitaRegistro> list = null;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_BY_TIROCINIO);
      preparedStatement.setInt(1, tirocinio.getId());

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        AttivitaRegistro bean = new AttivitaRegistro();
        bean.setId(rs.getInt("id"));
        bean.setAttivita(rs.getString("attivita"));
        bean.setData(rs.getDate("data"));
        bean.setOreSvolte(rs.getTime("ore_svolte"));
        bean.setTirocinio(tirocinio);

        list.add(bean);

      }

    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    }
    return list;
  }

  /**
   * Questo metodo si occupa di salvare un'AttivitaRegistro nel Database.
   *
   * @param attivita l'AttivitaRegistro da inserire nel Database
   * @return true se l'inserimento avviene con successo, false altrimenti.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa un attivita == null.
   */
  @Override
  public synchronized boolean doSave(AttivitaRegistro attivita) throws SQLException {

    if (attivita == null) {
      throw new IllegalArgumentException();
    }

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int rs = 0;

    try {
      connection = DatabaseManager.getConnection();

      preparedStatement = connection.prepareStatement(INSERT);

      preparedStatement.setInt(1, attivita.getTirocinio().getId());
      preparedStatement.setDate(2, attivita.getData());
      preparedStatement.setString(3, attivita.getAttivita());
      preparedStatement.setTime(4, attivita.getOreSvolte());

      rs = preparedStatement.executeUpdate();


    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    }
    return (rs != 0);
  }


  public static final String INSERT =
      "INSERT INTO attivitaregistro (tirocinio, data, attivita, ore_svolte) VALUES (?, ?, ?, ?)";

  public static final String RETRIVE_BY_TIROCINIO =
      "SELECT * FROM attivita_registro WHERE tirocinio = ?";

}
