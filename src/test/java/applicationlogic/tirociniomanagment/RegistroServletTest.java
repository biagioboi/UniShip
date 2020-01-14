package applicationlogic.tirociniomanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.TestingUtility;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.PasswordHash;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;

class RegistroServletTest extends Mockito {

  private MockHttpServletResponse response;
  private MockHttpServletRequest request;
  private RegistroServlet servlet;

  private static Utente azienda;

  private static Utente studente;

  private static Tirocinio tirocinio;

  @BeforeAll
  static void setUp() {

    try {
      azienda = new Utente("info@prova.it", "Prova", PasswordHash.createHash("password"), "azienda");
      TestingUtility.createUtente(azienda);

      Azienda prova = new Azienda("info@prova.it", "Prova", PasswordHash.createHash("password"), "03944080652",
          "via prova 2", "pippo", "5485", 55);
      TestingUtility.createAzienda(prova);

      studente = new Utente("f.ruocco@studenti.unisa.it", "Frank", PasswordHash.createHash("password"), "studente");
      TestingUtility.createUtente(studente);

      Date d = Date.valueOf("1998-06-01");
      Studente ruocco = new Studente("f.ruocco@studenti.unisa.it", "Frank", PasswordHash.createHash("password"),
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(ruocco);

      tirocinio = new Tirocinio(0, Tirocinio.NON_COMPLETO, 7000, "pippo", 0, "not extis",
          ruocco, prova, "not extist");
      TestingUtility.createTirocinio(tirocinio);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }


  @AfterAll
  static void delete() {
    try {
      TestingUtility.deleteUtente(azienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente(studente.getEmail().toLowerCase());
      TestingUtility.deleteTirocinio(tirocinio);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @BeforeEach
  void carica() {
    servlet = new RegistroServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();

    request.getSession().setAttribute("utente", azienda);
    request.getSession().setAttribute("login", "si");

  }

  @Test
  public void TC_19_01() throws ServletException, IOException {
    request.setParameter("action", "addActivity");
    request.setParameter("tirocinio", "cinque");
    request.setParameter("data", "2019-11-20");
    request.setParameter("oreSvolte", "06:00");
    request.setParameter("attivita", "Sviluppo portale");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Formato del tirocinio non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_19_02() throws ServletException, IOException {
    request.setParameter("action", "addActivity");
    request.setParameter("tirocinio", "2");
    request.setParameter("data", "2019-11-20");
    request.setParameter("oreSvolte", "06:00");
    request.setParameter("attivita", "Sviluppo portale");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Tirocinio non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_19_03() throws ServletException, IOException {
    request.setParameter("action", "addActivity");
    request.setParameter("tirocinio", String.valueOf(tirocinio.getId()));
    request.setParameter("data", "2019-11-20");
    request.setParameter("oreSvolte", "06:00");
    request.setParameter("attivita", "");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"La descrizione non puo' essere vuota\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void TC_19_04() throws ServletException, IOException {
    request.setParameter("action", "addActivity");
    request.setParameter("tirocinio", String.valueOf(tirocinio.getId()));
    request.setParameter("data", "2019-11-20");
    request.setParameter("oreSvolte", "06:00");
    request.setParameter("attivita", "Il nome di un’attività davvero molto lunga che non può essere inserita, dovrebbe essere riassunta e poi inserita.");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Descrizione troppo lunga\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void TC_19_05() throws ServletException, IOException {
    request.setParameter("action", "addActivity");
    request.setParameter("tirocinio", String.valueOf(tirocinio.getId()));
    request.setParameter("data", "2019-11-20");
    request.setParameter("oreSvolte", "06:00");
    request.setParameter("attivita", "Sviluppo_portale_nonAmmettec@ratteriSpeciali");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Formato della descrizione non valido\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void TC_19_06() throws ServletException, IOException {
    request.setParameter("action", "addActivity");
    request.setParameter("tirocinio", String.valueOf(tirocinio.getId()));
    request.setParameter("data", "2019-13-20");
    request.setParameter("oreSvolte", "06:00");
    request.setParameter("attivita", "Sviluppo portale");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Formato della data non valido\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_19_07() throws ServletException, IOException {
    request.setParameter("action", "addActivity");
    request.setParameter("tirocinio", String.valueOf(tirocinio.getId()));
    request.setParameter("data", "2019-11-20");
    request.setParameter("oreSvolte", "");
    request.setParameter("attivita", "Sviluppo portale");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Le ore non possono essere vuote\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_19_08() throws ServletException, IOException {
    request.setParameter("action", "addActivity");
    request.setParameter("tirocinio", String.valueOf(tirocinio.getId()));
    request.setParameter("data", "2019-11-20");
    request.setParameter("oreSvolte", "06:00");
    request.setParameter("attivita", "Sviluppo portale");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Attivita' aggiunta.\",\"status\":\"200\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }


}