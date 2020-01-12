package applicationlogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import storage.DatabaseManager;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.StudenteDao;

public class DBOperation {

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

      Date d = Date.valueOf("1998-06-01");
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

  public static void deleteUtente(String email)  throws SQLException {

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

}
