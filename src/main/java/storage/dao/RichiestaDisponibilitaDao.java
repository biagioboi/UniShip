package storage.dao;
/* da aggiungere il metodo doretrivebykey alla documentazione e aggiungere la constante */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import storage.DatabaseManager;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.interfaces.RichiestaDisponibilitaInterface;

public class RichiestaDisponibilitaDao implements RichiestaDisponibilitaInterface {

  /**
   * Questo metodo si occupa di trovare la richiesta disponibilita che ha associata le email passate
   * come parametro.
   *
   * @param azienda l'Azienda di cui si vogliono sapere le richieste di disponibilità..
   * @param studente lo studente di cui si vogliono sapere le richieste di disponibilità.
   * @return la RichiestaDisponibilita che ha come chiave le due email specificatae nei parametri se
   *     esite nel Database, null altrimenti.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null oppure studente
   *     == null.
   */
  @Override
  public RichiestaDisponibilita doRetrieveByKey(Studente studente, Azienda azienda)
      throws SQLException {
    if (azienda == null || studente == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    RichiestaDisponibilita richiestaDisponibilita = new RichiestaDisponibilita();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_BY_KEY);
      preparedStatement.setString(1, studente.getEmail());
      preparedStatement.setString(2, azienda.getEmail());
      ResultSet rs = preparedStatement.executeQuery();

      if (!rs.next()) {
        return null;
      } else {
        richiestaDisponibilita.setMotivazioni(rs.getString("motivazioni"));
        richiestaDisponibilita.setStato(rs.getString("stato"));
        richiestaDisponibilita.setAzienda(azienda);
        richiestaDisponibilita.setStudente(studente);
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

    return richiestaDisponibilita;
  }

  /**
   * Questo metodo si occupa di trovare le richieste di disponibilità associate all'azienda passata
   * come parametro.
   *
   * @param azienda l'Azienda di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo RichiestaDisponibilita
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null.
   */
  @Override
  public ArrayList<RichiestaDisponibilita> doRetrieveByAzienda(Azienda azienda)
      throws SQLException {
    if (azienda == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<RichiestaDisponibilita> list = new ArrayList<RichiestaDisponibilita>();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_BY_AZIENDA);

      preparedStatement.setString(1, azienda.getEmail());

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {

        RichiestaDisponibilita richiestaDisponibilita = new RichiestaDisponibilita();
        richiestaDisponibilita.setMotivazioni(rs.getString("motivazioni"));
        richiestaDisponibilita.setStato(rs.getString("stato"));
        richiestaDisponibilita.setAzienda(azienda);//evito di prendere sempre gli stessi dati

        richiestaDisponibilita.setStudente(
            studenteDao.doRetrieveByKey(rs.getString("studente")));

        list.add(richiestaDisponibilita);

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
   * Questo metodo si occupa di trovare le richieste di disponibilità associate allo studente
   * passato come parametro.
   *
   * @param studente lo studente di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo RichiestaDisponibilita
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa studente == null.
   */
  @Override
  public ArrayList<RichiestaDisponibilita> doRetrieveByStudente(Studente studente)
      throws SQLException {

    if (studente == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<RichiestaDisponibilita> list = new ArrayList<RichiestaDisponibilita>();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_BY_STUDENTE);

      preparedStatement.setString(1, studente.getEmail());

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {

        RichiestaDisponibilita richiestaDisponibilita = new RichiestaDisponibilita();
        richiestaDisponibilita.setMotivazioni(rs.getString("motivazioni"));
        richiestaDisponibilita.setStato(rs.getString("stato"));
        richiestaDisponibilita.setAzienda(aziendaDao.doRetrieveByKey(rs.getString("azienda")));
        richiestaDisponibilita.setStudente(studente); //evito di prendere sempre gli stessi dati

        list.add(richiestaDisponibilita);

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
   * Questo metodo si occupa di cambiare gli attribuiti di un oggetto RichiestaDisponibilita
   * presente nel database.
   *
   * @param richiesta lo richiesta di cui si vogliono cambiare i valori degli attributi.
   * @return true se la modifica avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa richiesta == null.
   */
  @Override
  public boolean doChange(RichiestaDisponibilita richiesta) throws SQLException {
    if (richiesta == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    int result;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(CHANGE);
      preparedStatement.setString(1, richiesta.getMotivazioni());
      preparedStatement.setString(2, richiesta.getStato());
      preparedStatement.setString(3, richiesta.getAzienda().getEmail());
      preparedStatement.setString(4, richiesta.getStudente().getEmail());

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
   * Questo metodo occupa di inserire nel Database una nuova RichiestaDisponibilita.
   *
   * @param richiesta lo richiesta di cui si vogliono cambiare i valori degli attributi.
   * @return true se l'inserimento avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa richiesta == null.
   */
  @Override
  public boolean doSave(RichiestaDisponibilita richiesta) throws SQLException {
    if (richiesta == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int rs;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(SAVE);
      preparedStatement.setString(1, richiesta.getMotivazioni());
      preparedStatement.setString(2, richiesta.getStato());
      preparedStatement.setString(3, richiesta.getAzienda().getEmail());
      preparedStatement.setString(4, richiesta.getStudente().getEmail());

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

  /**
   * Questo metodo si occupa di prelevare tutti gli oggetti RichiestaDisponibilita dal Database.
   *
   * @return ArrayList di oggetti di tipo RichiestaDisponibilita.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  @Override
  public ArrayList<RichiestaDisponibilita> doRetrieveAll() throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<RichiestaDisponibilita> list = new ArrayList<RichiestaDisponibilita>();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_ALL);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        RichiestaDisponibilita richiestaDisponibilita = new RichiestaDisponibilita();
        richiestaDisponibilita.setMotivazioni(rs.getString("motivazioni"));
        richiestaDisponibilita.setStato(rs.getString("stato"));
        richiestaDisponibilita.setAzienda(aziendaDao.doRetrieveByKey(rs.getString("azienda")));
        richiestaDisponibilita.setStudente(studenteDao.doRetrieveByKey(rs.getString("studente")));

        list.add(richiestaDisponibilita);

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
   * Questo metodo si occupa di cancellare una RichiestaDisponibilita dal Database.
   *
   * @param richiesta lo richiesta che si deve cancellare.
   * @return true se la cancellazione avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  @Override
  public boolean doDelete(RichiestaDisponibilita richiesta) throws SQLException {
    if (richiesta == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    int result;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(DELETE);
      preparedStatement.setString(1, richiesta.getStudente().getEmail());
      preparedStatement.setString(2, richiesta.getAzienda().getEmail());

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

  private static StudenteDao studenteDao = new StudenteDao();
  private static AziendaDao aziendaDao = new AziendaDao();

  public static final String RETRIVE_BY_STUDENTE =
      "SELECT * FROM richiestadisponibilita WHERE studente = ? ";
  public static final String RETRIVE_BY_AZIENDA =
      "SELECT * FROM richiestadisponibilita WHERE azienda = ? ";
  public static final String RETRIVE_ALL = "SELECT * FROM richiestadisponibilita";

  public static final String CHANGE = "UPDATE richiestadisponibilita SET motivazioni = ?, "
      + "stato = ? WHERE azienda = ? AND studente = ?";

  public static final String SAVE = "INSERT INTO richiestadisponibilita (motivazioni, stato,"
      + " azienda, studente) VALUES (?, ?, ?, ?)";

  public static final String RETRIVE_BY_KEY =
      "SELECT * FROM richiestadisponibilita WHERE studente = ?  and azienda = ?";

  public static final String DELETE =
      "DELETE FROM richiestadisponibilita WHERE studente = ? and azienda = ?";
}
