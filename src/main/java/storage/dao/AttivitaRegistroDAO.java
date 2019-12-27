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
   * Questo metodo si occupa di prelevare tutte le attività svolte in un detirminato tirocinio.
   *
   * @param tirocinio il Tirocinio di cui si vogliono sapere le attività.
   * @return ritorna un ArrayList di oggetti di tipo AttivitaTirocinio contenente tutte le attività
   * di un tirocinio.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa un tirocinio == null.
   */
  @Override
  public synchronized ArrayList<AttivitaRegistro> doRetrieveByTirocinio(Tirocinio tirocinio)
      throws SQLException, IllegalArgumentException {
    if (tirocinio == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ArrayList<AttivitaRegistro> list = null;

    String selectSql = "SELECT * FROM attivita_registro WHERE tirocinio = ?";

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(selectSql);
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
  public synchronized boolean doSave(AttivitaRegistro attivita)
      throws SQLException, IllegalArgumentException {

    if (attivita == null) {
      throw new IllegalArgumentException();
    }

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
