package applicationlogic.richiestadisponibilitamanagment;

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
import storage.PasswordManager;
import storage.beans.Studente;
import storage.beans.Utente;

public class RichiestaDServletTest extends Mockito {

  private RichiestaDServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;

  private static Utente utente;

  @BeforeAll
  static void setUtente() {
    try {
      utente = new Utente("f.ruocco@studenti.unisa.it", "Frank", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "studente");
      TestingUtility.createUtente(utente);

      Date d = Date.valueOf("1998-06-01");
      Studente studente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184",
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(studente);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @BeforeEach
  void setUp() throws IOException {
    servlet = new RichiestaDServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    request.getSession().setAttribute("utente", utente);
    request.getSession().setAttribute("login", "si");
  }

  @AfterAll
  static void cancellaUtente() {
    try {
      TestingUtility.deleteUtente(utente.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void TC_3_01() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "");
    request.setParameter("messaggio",
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");

    servlet.doPost(request, response);
    assertEquals(
        "{\"description\":\"Email della azienda non puo\\u0027 essere vuota\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void TC_3_02() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "info@prova.it");
    request.setParameter("messaggio",
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email azienda errata\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_3_03() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "info@clarotech.it");
    request.setParameter("messaggio", "");

    servlet.doPost(request, response);
    assertEquals(
        "{\"description\":\"Il messaggio non puo\\u0027 essere vuoto\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_3_04() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "info@clarotech.it");
    request.setParameter("messaggio",
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti ma purtroppo non riesco ad inserire tutte le motivazioni qui perchè sono inutili ulteriori parole.");

    servlet.doPost(request, response);
    assertEquals(
        "{\"description\":\"Il messaggio non puo\\u0027 superare 200 caratteri\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void TC_3_05() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "info@clarotech.it");
    request.setParameter("messaggio",
        "Sono§§§§interessato @ svolgere il Tirocinio fo_rmativo presso di voi in quanto#sono_motivato dai vostri progetti.");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Messaggio non valido\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_3_06() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "info@clarotech.it");
    request.setParameter("messaggio",
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Richiesta inviata.\",\"status\":\"200\"}",
        response.getContentAsString().trim());
  }


}