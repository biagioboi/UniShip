package applicationlogic.tirociniomanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.TestingUtility;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpServletRequest;
import storage.PasswordHash;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;

public class TirocinioServletTest extends Mockito {

  private MockHttpServletResponse response;
  private MockHttpServletRequest request;
  private TirocinioServlet servlet;

  private static Tirocinio firstTirocinio;
  private static Tirocinio secondTirocinio;

  private static Utente firstStudente;
  private static Utente secondStudente;

  private static Utente firstAzienda;

  private static Utente carrierOffice;


  @BeforeAll
  static void setUp() {

    try {
      firstAzienda = new Utente("info@prova.it", "Prova", PasswordHash.createHash("password"), "azienda");
      TestingUtility.createUtente(firstAzienda);

      Azienda prova = new Azienda("info@prova.it", "Prova", PasswordHash.createHash("password"), "03944080652",
          "via prova 2", "pippo", "5485", 55);
      TestingUtility.createAzienda(prova);

      firstStudente = new Utente("f.ruocco@studenti.unisa.it", "Frank", PasswordHash.createHash("password"), "studente");
      TestingUtility.createUtente(firstStudente);

      Date d = Date.valueOf("1998-06-01");
      Studente ruocco = new Studente("f.ruocco@studenti.unisa.it", "Frank", PasswordHash.createHash("password"),
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(ruocco);

      secondStudente = new Utente("m.rossi@studenti.unisa.it", "Mario", PasswordHash.createHash("password"), "studente");
      TestingUtility.createUtente(secondStudente);

      d = Date.valueOf("1998-01-05");
      Studente rossi = new Studente("m.rossi@studenti.unisa.it", "Mario", PasswordHash.createHash("password"),
          "MRORSS98A05H703Q", "1234567891", d, "Italia", "Rofrano", "3485813158", "Rossi");
      TestingUtility.createStudente(rossi);

      firstTirocinio = new Tirocinio(0, Tirocinio.NON_COMPLETO, 7000, "pippo", 0, "not extis",
          ruocco, prova, "not extist");
      TestingUtility.createTirocinio(firstTirocinio);

      secondTirocinio = new Tirocinio(0, Tirocinio.IN_CORSO, 7000, "pippo", 500, "not extis", rossi,
          prova, "not extist");
      TestingUtility.createTirocinio(secondTirocinio);

      carrierOffice = new Utente("carrieroffice@unisa.it", "Ufficio Carriere", PasswordHash.createHash("password"),
          "ufficio_carriere");
      TestingUtility.createUtente(carrierOffice);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @AfterAll
  static void delete() {
    try {
      TestingUtility.deleteUtente(firstAzienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente(firstStudente.getEmail().toLowerCase());
      TestingUtility.deleteUtente(secondStudente.getEmail().toLowerCase());
      TestingUtility.deleteTirocinio(firstTirocinio);
      TestingUtility.deleteTirocinio(secondTirocinio);
      TestingUtility.deleteUtente(carrierOffice.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @BeforeEach
  void carica() {
    servlet = new TirocinioServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();

    request.getSession().setAttribute("utente", carrierOffice);
    request.getSession().setAttribute("login", "si");

  }

  @Test
  public void TC_9_01() throws ServletException, IOException {
    request.setParameter("action", "viewInternshipByFilter");
    request.setParameter("azienda", "info@nonregistrato.it");
    request.setParameter("studente", "m.rossi@studenti.unisa.it");
    request.setParameter("stato", "Rifiutata");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Azienda non prensente nel sistema.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_9_02() throws ServletException, IOException {
    request.setParameter("action", "viewInternshipByFilter");
    request.setParameter("azienda", "info@prova.it");
    request.setParameter("studente", "b.boi@studenti.unisa.it");
    request.setParameter("stato", "Rifiutata");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Studente non prensente nel sistema.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_9_03() throws ServletException, IOException {
    request.setParameter("action", "viewInternshipByFilter");
    request.setParameter("azienda", "info@prova.it");
    request.setParameter("studente", "m.rossi@studenti.unisa.it");
    request.setParameter("stato", "Non accettata");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Inserire uno stato valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_9_04() throws ServletException, IOException {
    request.setParameter("action", "viewInternshipByFilter");
    request.setParameter("azienda", "info@prova.it");
    request.setParameter("studente", "m.rossi@studenti.unisa.it");
    request.setParameter("stato", "In corso");
    servlet.doPost(request, response);
    Tirocinio tir[] = new Gson().fromJson(response.getContentAsString().trim(), Tirocinio[].class);
    for (int x = 0; x < tir.length; x++) {
      assertEquals(secondTirocinio.equals(tir[x]), true);
    }
  }

}