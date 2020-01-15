package storage.dao;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.TestingUtility;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;

class AziendaDaoTest {

  private Azienda azienda;
  private static AziendaDao dao = new AziendaDao();


  @BeforeEach
  public void setUp() throws SQLException {

    Utente aziendaData = new Utente("info@prova.it", "Prova", "password", "azienda");
    TestingUtility.createUtente(aziendaData);

    azienda = new Azienda("info@prova.it", "Prova", "password", "03944080652",
        "via prova 2", "pippo", "5485", 55);
    TestingUtility.createAzienda(azienda);



  }

  @AfterEach
  public void delete() {
    try {
      TestingUtility.deleteUtente(azienda.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void doChangeAziendaNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doChange(null);
    });
  }

  @Test
  void doChangeAzienda() throws SQLException{
    assertTrue(dao.doChange(azienda));
  }


  @Test
  void doRetrieveByKeyNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByKey(null);
    });
  }

  @Test
  void doSaveNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doSave(null);
    });
  }

}