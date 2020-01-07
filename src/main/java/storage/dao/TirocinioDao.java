package storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import storage.DatabaseManager;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.interfaces.TirocinioInterface;


public class TirocinioDao implements TirocinioInterface {

  /**
   * Questo metodo si occupa di cambiare gli attribuiti di un oggetto Tirocinio presente nel
   * database.
   *
   * @param tirocinio lo richiesta di cui si vogliono cambiare i valori degli attributi.
   * @return true se la modifica avviene con successo, false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa tirocinio == null.
   */
  @Override
  public boolean doChange(Tirocinio tirocinio) throws SQLException {
    if (tirocinio == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    int result = 0;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(CHANGE);
      preparedStatement.setDouble(1, tirocinio.getOreTotali());
      preparedStatement.setString(2, tirocinio.getTurorEsterno());
      preparedStatement.setDouble(3, tirocinio.getOreSvolte());
      preparedStatement.setString(4, tirocinio.getPath());
      preparedStatement.setString(5, tirocinio.getStato());
      preparedStatement.setString(6, tirocinio.getAzienda().getEmail());
      preparedStatement.setString(7, tirocinio.getStudente().getEmail());
      preparedStatement.setString(8, tirocinio.getMotivazioni());
      preparedStatement.setInt(9, tirocinio.getId());

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
   * Questo metodo si occupa di trovare i tirocini associate allo studente passata come parametro.
   *
   * @param studente lo Studente di cui si vogliono sapere i Tirocini associati.
   * @return ArrayList di oggetti di tipo Tirocinio
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa studente == null.
   */
  @Override
  public ArrayList<Tirocinio> doRetrieveByStudente(Studente studente) throws SQLException {
    if (studente == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<Tirocinio> list = new ArrayList<Tirocinio>();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_BY_STUDENTE);

      preparedStatement.setString(1, studente.getEmail());

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {

        Tirocinio tirocinio = new Tirocinio();
        tirocinio.setId(rs.getInt("id"));
        tirocinio.setOreTotali(rs.getDouble("ore_totali"));
        tirocinio.setTurorEsterno(rs.getString("tutor_esterno"));
        tirocinio.setOreSvolte(rs.getDouble("ore_svolte"));
        tirocinio.setPath(rs.getString("path"));
        tirocinio.setStato(rs.getString("stato"));
        tirocinio.setMotivazioni(rs.getString("motivazioni"));
        tirocinio.setAzienda(aziendaDao.doRetrieveByKey(rs.getString("azienda")));
        tirocinio.setStudente(studente); //evito di prendere sempre gli stessi dati

        list.add(tirocinio);

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
   * Questo metodo si occupa di trovare i tirocini associate all'azienda passata come parametro.
   *
   * @param azienda l'Azienda di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo Tirocinio
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa azienda == null.
   */
  @Override
  public ArrayList<Tirocinio> doRetrieveByAzienda(Azienda azienda) throws SQLException {
    if (azienda == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<Tirocinio> list = new ArrayList<Tirocinio>();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_BY_AZIENDA);

      preparedStatement.setString(1, azienda.getEmail());

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {

        Tirocinio tirocinio = new Tirocinio();
        tirocinio.setId(rs.getInt("id"));
        tirocinio.setOreTotali(rs.getDouble("ore_totali"));
        tirocinio.setTurorEsterno(rs.getString("tutor_esterno"));
        tirocinio.setOreSvolte(rs.getDouble("ore_svolte"));
        tirocinio.setPath(rs.getString("path"));
        tirocinio.setStato(rs.getString("stato"));
        tirocinio.setMotivazioni(rs.getString("motivazioni"));
        tirocinio.setAzienda(azienda); //evito di prendere sempre gli stessi dati
        tirocinio.setStudente(studenteDao.doRetrieveByKey(rs.getString("studente")));

        list.add(tirocinio);

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
   * Questo metodo si occupa di trovare il tirocinio che ha associata id passato per parametro.
   *
   * @param id un valore di tipo intero
   * @return il tirocinio che ha come id quello specificato nel parametro se esite nel Database,
   *     null altrimenti.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  @Override
  public Tirocinio doRetrieveByKey(int id) throws SQLException {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Tirocinio tirocinio = new Tirocinio();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_BY_KEY);
      preparedStatement.setInt(1, id);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next() == false) {
        return null;
      } else {
        tirocinio.setId(rs.getInt("id"));
        tirocinio.setOreTotali(rs.getDouble("ore_totali"));
        tirocinio.setTurorEsterno(rs.getString("tutor_esterno"));
        tirocinio.setOreSvolte(rs.getDouble("ore_svolte"));
        tirocinio.setPath(rs.getString("path"));
        tirocinio.setStato(rs.getString("stato"));
        tirocinio.setMotivazioni(rs.getString("motivazioni"));
        tirocinio.setAzienda(aziendaDao.doRetrieveByKey(rs.getString("azienda")));
        tirocinio.setStudente(studenteDao.doRetrieveByKey(rs.getString("studente")));
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

    return tirocinio;
  }

  /**
   * Questo metodo si occupa di inserire nel Database una oggetto di tipo Tirocinio.
   *
   * @param tirocinio un oggetto di tipo Tirocinio
   * @return true se l'inserimento avviene con successo , false altrimenti
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa tirocinio == null
   */
  @Override
  public boolean doSave(Tirocinio tirocinio) throws SQLException {
    if (tirocinio == null) {
      throw new IllegalArgumentException();
    }
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int rs = 0;

    try {
      connection = DatabaseManager.getConnection();
      SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
      preparedStatement = connection.prepareStatement(SAVE);
      preparedStatement.setDouble(1, tirocinio.getOreTotali());
      preparedStatement.setString(2, tirocinio.getTurorEsterno());
      preparedStatement.setDouble(3, tirocinio.getOreSvolte());
      preparedStatement.setString(4, tirocinio.getPath());
      preparedStatement.setString(5, tirocinio.getStato());
      preparedStatement.setString(6, tirocinio.getAzienda().getEmail());
      preparedStatement.setString(7, tirocinio.getStudente().getEmail());
      preparedStatement.setString(8, tirocinio.getMotivazioni());

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
   * Questo metodo si occupa di prelevare tutti gli oggetti Tirocinio dal Database.
   *
   * @return ArrayList di oggetti di tipo Tirocinio.
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   */
  @Override
  public ArrayList<Tirocinio> doRetrieveAll() throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<Tirocinio> list = new ArrayList<Tirocinio>();

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(RETRIVE_ALL);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        Tirocinio tirocinio = new Tirocinio();
        tirocinio.setId(rs.getInt("id"));
        tirocinio.setOreTotali(rs.getDouble("ore_totali"));
        tirocinio.setTurorEsterno(rs.getString("tutor_esterno"));
        tirocinio.setOreSvolte(rs.getDouble("ore_svolte"));
        tirocinio.setPath(rs.getString("path"));
        tirocinio.setStato(rs.getString("stato"));
        tirocinio.setMotivazioni(rs.getString("motivazioni"));
        tirocinio.setAzienda(aziendaDao.doRetrieveByKey(rs.getString("azienda")));
        tirocinio.setStudente(studenteDao.doRetrieveByKey(rs.getString("studente")));

        list.add(tirocinio);

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

  private static StudenteDao studenteDao = new StudenteDao();
  private static AziendaDao aziendaDao = new AziendaDao();

  public static final String RETRIVE_BY_STUDENTE = "SELECT * FROM tirocinio WHERE studente = ? ";
  public static final String RETRIVE_BY_AZIENDA = "SELECT * FROM tirocinio WHERE azienda = ? ";
  public static final String RETRIVE_BY_KEY = "SELECT * FROM tirocinio WHERE id = ? ";
  public static final String RETRIVE_ALL = "SELECT * FROM tirocinio";

  public static final String CHANGE = "UPDATE tirocinio SET ore_totali = ?, tutor_esterno = ?, "
      + "ore_svolte = ? , path = ? , stato = ?, azienda = ?, studente = ? ,"
      + "motivazioni = ? WHERE id = ?";

  public static final String SAVE = "INSERT INTO tirocinio (ore_totali, tutor_esterno, ore_svolte,"
      + " path, stato, azienda, studente,motivazioni) VALUES (?, ?, ?, ?, ?, ? , ?, ?)";

}
