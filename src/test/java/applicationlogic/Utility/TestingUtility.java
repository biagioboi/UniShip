package applicationlogic.Utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import storage.DatabaseManager;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;

public class TestingUtility {

  static Connection connection = null;

  public static void createUtente(Utente utente) throws SQLException {

    PreparedStatement preparedStatement = null;
    int rs;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection
          .prepareStatement("INSERT INTO utente (nome, tipo, password, email) VALUES (?,?,?,?)");
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

  }

  public static void createStudente(Studente studente) throws SQLException {

    PreparedStatement preparedStatement = null;
    int rs;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(
          "INSERT INTO studente (email, cognome, codice_fiscale, matricola,data_di_nascita, cittadinanza,residenza,numero) VALUES (?,?,?,?,?,?,?,?)");
      ;
      preparedStatement.setString(1, studente.getEmail());
      preparedStatement.setString(2, studente.getCognome());
      preparedStatement.setString(3, studente.getCodiceFiscale());
      preparedStatement.setString(4, studente.getMatricola());
      preparedStatement.setDate(5, studente.getDataDiNascita());
      preparedStatement.setString(6, studente.getCittadinanza());
      preparedStatement.setString(7, studente.getResidenza());
      preparedStatement.setString(8, studente.getNumero());

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

  }

  public static void createTirocinio(Tirocinio tirocinio) throws SQLException {
    PreparedStatement preparedStatement = null;
    int rs;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection
          .prepareStatement("INSERT INTO tirocinio (ore_totali, tutor_esterno, ore_svolte,"
                  + " path, stato, azienda, studente,motivazioni) VALUES (?, ?, ?, ?, ?, ? , ?, ?)",
              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setDouble(1, tirocinio.getOreTotali());
      preparedStatement.setString(2, tirocinio.getTurorEsterno());
      preparedStatement.setDouble(3, tirocinio.getOreSvolte());
      preparedStatement.setString(4, tirocinio.getPath());
      preparedStatement.setString(5, tirocinio.getStato());
      preparedStatement.setString(6, tirocinio.getAzienda().getEmail());
      preparedStatement.setString(7, tirocinio.getStudente().getEmail());
      preparedStatement.setString(8, tirocinio.getMotivazioni());

      rs = preparedStatement.executeUpdate();

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          tirocinio.setId(generatedKeys.getInt(1));
        } else {
          throw new SQLException("Creating user failed, no ID obtained.");
        }
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
  }

  public static void createAzienda(Azienda azienda) throws SQLException {

    PreparedStatement preparedStatement = null;
    int rs = 0;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(
          "INSERT INTO azienda (email, partita_iva, indirizzo, rappresentante,codice_ateco, numero_dipendenti) VALUES (?,?,?,?,?,?)");
      preparedStatement.setString(1, azienda.getEmail().toLowerCase());
      preparedStatement.setString(2, azienda.getPartitaIva());
      preparedStatement.setString(3, azienda.getIndirizzo());
      preparedStatement.setString(4, azienda.getRappresentante());
      preparedStatement.setString(5, azienda.getCodAteco());
      preparedStatement.setInt(6, azienda.getNumeroDipendenti());

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
  }

  public static void createRichiestaDisponibilita(RichiestaDisponibilita richiesta)
      throws SQLException {

    PreparedStatement preparedStatement = null;
    int rs = 0;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(
          "INSERT INTO richiestadisponibilita (motivazioni, stato, azienda, studente) VALUES (?, ?, ?, ?)");
      preparedStatement.setString(1, richiesta.getMotivazioni());
      preparedStatement.setString(2, richiesta.getStato());
      preparedStatement.setString(3, richiesta.getAzienda().getEmail().toLowerCase());
      preparedStatement.setString(4, richiesta.getStudente().getEmail().toLowerCase());

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
  }

  public static void deleteUtente(String email) throws SQLException {

    PreparedStatement preparedStatement = null;
    int rs;
    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement("DELETE FROM utente WHERE email = ?");
      preparedStatement.setString(1, email);

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

  }


  public static void deleteRicDisp(RichiestaDisponibilita richiesta) throws SQLException {

    PreparedStatement preparedStatement = null;
    int rs;
    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(
          "DELETE FROM richiestadisponibilita WHERE studente = ? and azienda = ?");
      preparedStatement.setString(1, richiesta.getStudente().getEmail().toLowerCase());
      preparedStatement.setString(2, richiesta.getAzienda().getEmail().toLowerCase());

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

  }


  public static void deleteTirocinio(Tirocinio tirocinio) throws SQLException {
    PreparedStatement preparedStatement = null;
    int rs;
    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement("DELETE FROM tirocinio WHERE id = ?");
      preparedStatement.setInt(1, tirocinio.getId());

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

  }

  public static void setFinalStatic(Field field, Object newValue) throws Exception {
    field.setAccessible(true);

    // remove final modifier from field
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

    field.set(null, newValue);
  }

}
