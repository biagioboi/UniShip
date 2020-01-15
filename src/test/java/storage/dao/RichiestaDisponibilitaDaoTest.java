package storage.dao;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.TestingUtility;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.beans.AttivitaRegistro;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;

class RichiestaDisponibilitaDaoTest {

  private Studente studente;
  private Azienda azienda;
  private RichiestaDisponibilita richiesta;
  private static RichiestaDisponibilitaDao dao = new RichiestaDisponibilitaDao();


  @BeforeEach
  public void setUp() throws SQLException {

    Utente aziendaData = new Utente("info@prova.it", "Prova", "password", "azienda");
    TestingUtility.createUtente(aziendaData);

    azienda = new Azienda("info@prova.it", "Prova", "password", "03944080652",
        "via prova 2", "pippo", "5485", 55);
    TestingUtility.createAzienda(azienda);

    Utente studenteData = new Utente("f.ruocco@studenti.unisa.it", "Frank", "password",
        "studente");
    TestingUtility.createUtente(studenteData);

    Date d = Date.valueOf("1998-06-01");
    studente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password",
        "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
    TestingUtility.createStudente(studente);

    richiesta = new RichiestaDisponibilita("none", RichiestaDisponibilita.ACCETTATA, azienda, studente);
    TestingUtility.createRichiestaDisponibilita(richiesta);

  }

  @AfterEach
  public void delete() {
    try {
      TestingUtility.deleteUtente(azienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente(studente.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void doRetrieveByKey() throws SQLException{

    assertEquals(1,dao.doRetrieveByAzienda(azienda).size());
  }

  @Test
  void doRetrieveByKeyNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByKey(null,null);
    });
  }

  @Test
  void doRetrieveByKeyNull2() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByKey(studente,null);
    });
  }

  @Test
  void doRetrieveByKeyNull3() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByKey(null,azienda);
    });
  }

  @Test
  void doRetrieveByAziendaNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByAzienda(null);
    });
  }

  @Test
  void doRetrieveByStudenteNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByStudente(null);
    });
  }

  @Test
  void doChangeNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doChange(null);
    });
  }

  @Test
  void doSaveNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doSave(null);
    });
  }

  @Test
  void doDeleteNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doDelete(null);
    });
  }
}