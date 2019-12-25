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

public class AttivitaRegistroDAO implements AttivitaRegistroInterface {


  /**
   * Questo metodo si occupa di prelevare tutte le attivita svolte in un detirminato tirocinio.
   *
   * @param tirocinio un oggetto di tipo Tirocinio.
   * @return ArrayList di oggetti di tipo  AttivitaTirocinio.
   * @precondition tirocinio != null e corrisponde ad un tirocinio presente nel database.
   */
  @Override
  public synchronized ArrayList<AttivitaRegistro> doRetrieveByTirocinio(Tirocinio tirocinio)
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ArrayList<AttivitaRegistro> list = new ArrayList<AttivitaRegistro>();


    String selectSQL = "SELECT * FROM attivita_registro WHERE tirocinio = ?";

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(selectSQL);
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
        if (preparedStatement != null)
          preparedStatement.close();
      } finally {
        if (connection != null)
          connection.close();
      }
    }
    return list;
  }

  /**
   * Questo metodo si occupa di inserire nel Database una entry nella tabella attivita_registro.
   *
   * @param attivita un oggetto di tipo AttivitaRegistro
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @precondition attivita != null
   */
  @Override
  public synchronized boolean doSave(AttivitaRegistro attivita) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int rs = 0;

    /* TODO : Da valutare se convine lasciare la stringa nel metodo oppure se renderla statica.
    migliori prestazioni ma la specifica?
     */
    String insertSql = "INSERT INTO attivitaregistro (tirocinio, data, attivita, ore_svolte) "
        + "VALUES (?, ?, ?, ?)";

    try {
      connection = DatabaseManager.getConnection();

      preparedStatement = connection.prepareStatement(insertSql);

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
}
