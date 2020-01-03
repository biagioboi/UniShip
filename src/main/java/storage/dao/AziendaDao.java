package storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import storage.DatabaseManager;
import storage.beans.Azienda;
import storage.interfaces.AziendaInterface;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AziendaDao implements AziendaInterface {

  /**
   * Questo metodo si occupa di modificare un'azienda presente nel Database.
   *
   * @param azienda l'Azienda che si vuole aggiornare.
   * @return true se la modifica avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null.
   */
  @Override
  public boolean doChange(Azienda azienda) throws SQLException {

    if (azienda == null) {
      throw new IllegalArgumentException();
    }

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    int result = 0;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(CHANGE);

      preparedStatement.setString(1, azienda.getPartitaIva());
      preparedStatement.setString(2, azienda.getIndirizzo());
      preparedStatement.setString(3, azienda.getRappresentante());
      preparedStatement.setString(4, azienda.getCodAteco());
      preparedStatement.setInt(5, azienda.getNumeroDipendenti());
      preparedStatement.setString(6, azienda.getEmail().toLowerCase());

      result = preparedStatement.executeUpdate();

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

    return result != 0;
  }

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Azienda dal Database.
   *
   * @return ArrayList di oggetti di tipo Azienda.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  @Override
  public ArrayList<Azienda> doRetrieveAll() throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<Azienda> list = new ArrayList<Azienda>();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_ALL);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {

        Azienda azienda = new Azienda();
        azienda.setEmail(rs.getString("email"));
        azienda.setNome(rs.getString("nome"));
        azienda.setPartitaIva(rs.getString("partita_iva"));
        azienda.setIndirizzo(rs.getString("indirizzo"));
        azienda.setRappresentante(rs.getString("rappresentante"));
        azienda.setCodAteco(rs.getString("codice_ateco"));
        azienda.setNumeroDipendenti(rs.getInt("numero_dipendenti"));
        azienda.setTipo(rs.getString("tipo"));

        list.add(azienda);

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
   * Questo metodo si occupa di trovare l'azienda che ha associata l'email passata come parametro.
   *
   * @param email l'email di una determinata azinda.
   * @return l'Azienda che ha come email quella specificata nel parametro se esite nel Database,
   *     null altrimenti.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa email == null.
   */
  @Override
  public Azienda doRetrieveByKey(String email) throws SQLException {
    if (email == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Azienda azienda = new Azienda();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_BY_KEY);
      preparedStatement.setString(1, email);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next() == false) {
        return null;
      } else {
        azienda.setEmail(rs.getString("email"));
        azienda.setNome(rs.getString("nome"));
        azienda.setPartitaIva(rs.getString("partita_iva"));
        azienda.setIndirizzo(rs.getString("indirizzo"));
        azienda.setRappresentante(rs.getString("rappresentante"));
        azienda.setCodAteco(rs.getString("codice_ateco"));
        azienda.setNumeroDipendenti(rs.getInt("numero_dipendenti"));
        azienda.setTipo(rs.getString("tipo"));
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

    return azienda;
  }

  /**
   * Questo metodo si occupa di inserire nel Database una nuova azienda.
   *
   * @param azienda l'Azienda da inserire nel Database.
   * @return true se l'inserimento avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null.
   */
  @Override
  public boolean doSave(Azienda azienda) throws SQLException {
    if (azienda == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int rs = 0;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(SAVE);
      preparedStatement.setString(1, azienda.getEmail().toLowerCase());
      preparedStatement.setString(2, azienda.getPartitaIva());
      preparedStatement.setString(3, azienda.getIndirizzo());
      preparedStatement.setString(4, azienda.getRappresentante());
      preparedStatement.setString(5, azienda.getCodAteco());
      preparedStatement.setInt(6, azienda.getNumeroDipendenti());

      utenteDao.doSave(azienda); //Esecuzione possibile poiche' Azienda estende Utente

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

  //TODO: Implement doRetrieveByPiva and add to documentation
  public Azienda doRetrieveByPiva(String piva) throws SQLException {
    throw new NotImplementedException();
  }

  private static UtenteDao utenteDao = new UtenteDao();

  public static final String RETRIVE_BY_KEY =
      "SELECT * FROM azienda NATURAL JOIN utente WHERE email = ? ";
  public static final String RETRIVE_ALL = "SELECT * FROM azienda NATURAL JOIN utente;";

  public static final String SAVE =
      "INSERT INTO studente (email, partita_iva, indirizzo, rappresentante,codice_ateco,"
          + "numero_dipendenti) VALUES (?,?,?,?,?,?)";
  public static final String CHANGE =
      "UPDATE azienda SET partita_iva = ?, indirizzo = ?, rappresentante = ?, codice_ateco = ?"
          + "numero_dipendenti = ? WHERE email = ?";
}
