package storage.dao;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.TestingUtility;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import storage.beans.AttivitaRegistro;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;

public class AttivitaRegistroDaoTest extends Mockito {

  private static AttivitaRegistroDao dao = new AttivitaRegistroDao();

  private Studente studente;
  private Azienda azienda;
  private Tirocinio tirocinio;
  private AttivitaRegistro attivita;


  @BeforeEach
  public void setUp() throws SQLException {

    Utente aziendaData = new Utente("info@prova.it", "Prova", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "azienda");
    TestingUtility.createUtente(aziendaData);

    azienda = new Azienda("info@prova.it", "Prova", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080652",
        "via prova 2", "pippo", "5485", 55);
    TestingUtility.createAzienda(azienda);

    Utente studenteData = new Utente("f.ruocco@studenti.unisa.it", "Frank", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184",
        "studente");
    TestingUtility.createUtente(studenteData);

    Date d = Date.valueOf("1998-06-01");
    studente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184",
        "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
    TestingUtility.createStudente(studente);

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource("prova.pdf");
    tirocinio = new Tirocinio(999, Tirocinio.NON_COMPLETO, 7000, "pippo", 500, resource.getPath(),
        studente, azienda, "not extist");
    TestingUtility.createTirocinio(tirocinio);

    attivita = new AttivitaRegistro(999,tirocinio,new Date(126123),"nuova attivita",10);
    TestingUtility.createAttivita(attivita);

  }

  @AfterEach
  public void delete() {
    try {
      TestingUtility.deleteUtente(azienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente(studente.getEmail().toLowerCase());
      TestingUtility.deleteTirocinio(tirocinio);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @Test
  public void doRetrieveByTirocinioTirocinioNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByTirocinio(null);
    });
  }
  @Test
  public void doRetrieveByTirocinio() throws SQLException{
    assertEquals(1,dao.doRetrieveByTirocinio(tirocinio).size());
  }

  @Test
  public void doSaveNull(){
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doSave(null);
    });
  }
}