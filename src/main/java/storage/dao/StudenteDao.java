package storage.dao;

/* nome classe cambiato */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import storage.DatabaseManager;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.interfaces.StudenteInterface;

/*nome classe cambiato,aggiunte constanti, verifiacare che StudenteDao sia dipendete con UtenteDao
* e da Utente*/

public class StudenteDao implements StudenteInterface {

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti Studente dal Database.
   *
   * @return ArrayList di oggetti di tipo Studente
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  @Override
  public ArrayList<Studente> doRetrieveAll() throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<Studente> list = new ArrayList<Studente>();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_ALL);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {

        Studente studente = new Studente();
        studente.setEmail(rs.getString("email"));
        studente.setNome(rs.getString("nome"));
        studente.setCognome(rs.getString("cognome"));
        studente.setCodiceFiscale(rs.getString("codice_fiscale"));
        studente.setMatricola(rs.getString("matricola"));
        studente.setDataDiNascita(rs.getDate("data_di_nascita"));
        studente.setCittadinanza(rs.getString("cittadinanza"));
        studente.setResidenza(rs.getString("residenza"));
        studente.setNumero(rs.getString("numero"));
        studente.setTipo(rs.getString("tipo"));

        list.add(studente);

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
   * @param email un ogggetto di dito String che rapprensenta Email di una determinato
   *     studente.
   * @return Oggetto di tipo Studente
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa email == null
   */
  @Override
  public Studente doRetrieveByKey(String email) throws SQLException {
    if (email == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Studente studente = new Studente();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_BY_KEY);
      preparedStatement.setString(1, email);

      ResultSet rs = preparedStatement.executeQuery();
      rs.next();
      studente.setEmail(rs.getString("email"));
      studente.setNome(rs.getString("nome"));
      studente.setCognome(rs.getString("cognome"));
      studente.setCodiceFiscale(rs.getString("codice_fiscale"));
      studente.setMatricola(rs.getString("matricola"));
      studente.setDataDiNascita(rs.getDate("data_di_nascita"));
      studente.setCittadinanza(rs.getString("cittadinanza"));
      studente.setResidenza(rs.getString("residenza"));
      studente.setNumero(rs.getString("numero"));
      studente.setTipo(rs.getString("tipo"));

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

    return studente;
  }

  /**
   * Questo metodo si occupa di inserire nel Database una entry nella tabella studente.
   *
   * @param studente un oggetto di tipo Studente
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa studente == null
   */
  @Override
  public boolean doSave(Studente studente) throws SQLException {
    if (studente == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int rs = 0;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(SAVE);
      preparedStatement.setString(1, studente.getEmail().toLowerCase());
      preparedStatement.setString(2, studente.getCognome());
      preparedStatement.setString(3, studente.getCodiceFiscale());
      preparedStatement.setString(4, studente.getMatricola());
      preparedStatement.setDate(5, studente.getDataDiNascita());
      preparedStatement.setString(6, studente.getCittadinanza());
      preparedStatement.setString(7, studente.getResidenza());
      preparedStatement.setString(8, studente.getNumero());

      utenteDao.doSave(studente); //Esecuzione possibile poiche' Studente estende Utente

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

  private static UtenteDao utenteDao = new UtenteDao();

  public static final String RETRIVE_BY_KEY =
      "SELECT * FROM studente NATURAL JOIN utente WHERE email = ? ";
  public static final String RETRIVE_ALL = "SELECT * FROM studente NATURAL JOIN utente;";

  public static final String SAVE =
      "INSERT INTO studente (email, cognome, codice_fiscale, matricola,data_di_nascita,"
          + "cittadinanza,residenza,numero) VALUES (?,?,?,?,?,?,?,?)";

}
