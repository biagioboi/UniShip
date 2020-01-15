package storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import storage.DatabaseManager;
import storage.PasswordManager;
import storage.beans.Utente;
import storage.interfaces.UtenteInterface;

public class UtenteDao implements UtenteInterface {

  /**
   * Questo metodo si occupa di verificare la correttezza dei dati di accesso.
   *
   * @param email un ogggetto di dito String che rapprensenta l'email di una determinato
   *     utente.
   * @param password un ogggetto di dito String che rapprensenta la password di una determinato
   *     utente.
   * @return true se le informazioni combaciano, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa uno email == null oppure password
   *     == null.
   */
  @Override
  public synchronized boolean doCheckLogin(String email, String password) throws SQLException {

    if (email == null || password == null) {
      throw new IllegalArgumentException();
    }

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    boolean result = false;
    email = email.toLowerCase();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(LOGIN);
      preparedStatement.setString(1, email);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        if (rs.getString("password").equals(password) || PasswordManager
            .validatePassword(password, rs.getString("password"))) {
          result = true;
        } else {
          result = false;
        }
      }
    } catch (Exception e) {
      result = false;
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

    return result;
  }

  /**
   * Questo metodo si occupa di verficare data una email se essa e' gia' presente nel Database.
   *
   * @param email un ogggetto di dito String che rapprensenta l'email di una determinato
   *     utente.
   * @return true se e' prensente, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa uno email == null
   */
  @Override
  public synchronized boolean doCheckRegister(String email) throws SQLException {

    if (email == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    boolean result = true;
    email = email.toLowerCase();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(REGISTER);
      preparedStatement.setString(1, email);

      ResultSet rs = preparedStatement.executeQuery();

      rs.next();
      if (rs.getInt("esisteUtente") == 0) {
        result = false;
      } else {
        result = true;
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
    return result;
  }

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Utente dal Database.
   *
   * @return ArrayList di oggetti di tipo Utente
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  @Override
  public synchronized ArrayList<Utente> doRetrieveAll() throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<Utente> list = new ArrayList<Utente>();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_ALL);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {

        Utente utente = new Utente();
        utente.setTipo(rs.getString("tipo"));
        utente.setEmail(rs.getString("email"));
        utente.setNome(rs.getString("nome"));
        utente.setPassword(rs.getString("password"));

        list.add(utente);

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
   * Questo metodo si occupa di prelevare tutti gli oggetti Utente dal Database.
   *
   * @param email un ogggetto di dito String che rapprensenta Email di una determinato utente.
   * @return Oggetto di tipo Utente se esite nel Database, null altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa email == null
   */
  @Override
  public synchronized Utente doRetrieveByKey(String email) throws SQLException {
    if (email == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Utente utente = new Utente();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_BY_KEY);
      preparedStatement.setString(1, email);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next() == false) {
        return null;
      } else {
        utente.setTipo(rs.getString("tipo"));
        utente.setEmail(rs.getString("email"));
        utente.setNome(rs.getString("nome"));
        utente.setPassword(rs.getString("password"));
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

    return utente;
  }

  /**
   * Questo metodo si occupa di inserire nel Database una entry nella tabella utente.
   *
   * @param utente un oggetto di tipo Utente
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa utente == null
   */
  @Override
  public boolean doSave(Utente utente) throws SQLException {

    if (utente == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int rs = 0;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(SAVE);
      preparedStatement.setString(1, utente.getNome());
      preparedStatement.setString(2, utente.getTipo());
      preparedStatement.setString(3, utente.getPassword());
      preparedStatement.setString(4, utente.getEmail().toLowerCase());

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

    return rs != 0;
  }

  public static final String LOGIN = "SELECT Email,Password FROM utente WHERE email = ?";
  public static final String REGISTER =
      "SELECT count(*) AS esisteUtente FROM utente WHERE email = ?";

  public static final String RETRIVE_ALL = "SELECT * FROM utente ";
  public static final String RETRIVE_BY_KEY = "SELECT * FROM utente WHERE Email = ? ";

  public static final String SAVE =
      "INSERT INTO utente (nome, tipo, password, email) VALUES (?,?,?,?)";
}
