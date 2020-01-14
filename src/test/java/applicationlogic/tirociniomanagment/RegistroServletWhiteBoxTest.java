package applicationlogic.tirociniomanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.TestingUtility;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;
import storage.dao.AttivitaRegistroDao;


public class RegistroServletWhiteBoxTest extends Mockito {

  private static Utente studente;
  private static Utente fakeStudente;
  private static Utente azienda;

  private static Azienda realAzienda;
  private static Studente realStudente;
  private static Tirocinio tirocinio;

  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static RegistroServlet servlet = new RegistroServlet();

  private AttivitaRegistroDao dao;


  @BeforeEach
  public void load() {

    try {
      azienda = new Utente("info@prova.it", "Prova", "password", "azienda");
      TestingUtility.createUtente(azienda);

      realAzienda = new Azienda("info@prova.it", "Prova", "password", "03944080652",
          "via prova 2", "pippo", "5485", 55);
      TestingUtility.createAzienda(realAzienda);

      studente = new Utente("f.ruocco@studenti.unisa.it", "Frank", "password",
          "studente");
      TestingUtility.createUtente(studente);

      Date d = Date.valueOf("1998-06-01");
      realStudente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password",
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(realStudente);

      fakeStudente = new Utente("f.ruocco1@studenti.unisa.it", "Frank", "password",
          "studente");
      TestingUtility.createUtente(fakeStudente);

      d = Date.valueOf("1998-06-01");
      Studente secondStudente = new Studente("f.ruocco1@studenti.unisa.it", "Frank", "password",
          "RCCFNC98H01H501R", "1234567890", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(secondStudente);

      tirocinio = new Tirocinio(0, Tirocinio.NON_COMPLETO, 7000, "pippo", 0, "not extis",
          realStudente, realAzienda, "not extist");
      TestingUtility.createTirocinio(tirocinio);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @AfterEach
  public void delete() {
    try {
      TestingUtility.deleteUtente(azienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente(studente.getEmail().toLowerCase());
      TestingUtility.deleteUtente(fakeStudente.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @BeforeEach
  public void setUp() throws Exception {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    response = new MockHttpServletResponse();
    dao = spy(AttivitaRegistroDao.class);
    response = new MockHttpServletResponse();
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("attivitaDao"), dao);

  }


  @AfterEach
  public void clean() throws Exception {
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("attivitaDao"),
        new AttivitaRegistroDao());
  }


  @Test
  public void requestWithoutLoggedUser() throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(null);
    when(session.getAttribute("login")).thenReturn(null);

    servlet.doPost(request, response);

    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }


  @Test
  public void addActivityWithoutLogin() throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(null);
    when(session.getAttribute("login")).thenReturn(null);

    servlet.doPost(request, response);

    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }


  @Test
  public void addActivityLoggedAsStudente() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("addActivity");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));
    when(request.getParameter("oreSvolte")).thenReturn("06:00");
    when(request.getParameter("data")).thenReturn("2020-01-15");
    when(request.getParameter("attivita")).thenReturn("Inserimento collegamento al DB.");

    servlet.doPost(request, response);

    assertEquals(
        "{\"description\":\"Non hai permessi per aggiungere questa attivita.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void viewRegisterWithNullTirocinio() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("viewRegister");
    when(request.getParameter("tirocinio")).thenReturn(null);

    servlet.doPost(request, response);

    assertEquals("{\"description\":\"Tirocinio non valido\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void viewRegisterWithLoggedStudentDifferentFromRealStudent()
      throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(fakeStudente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("viewRegister");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));

    servlet.doPost(request, response);

    assertEquals(
        "{\"description\":\"Non puoi accedere a queste informazioni.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void viewRegisterWithRegistroException()
      throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("viewRegister");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));

    doThrow(SQLException.class).when(dao).doRetrieveByTirocinio(any(Tirocinio.class));

    servlet.doPost(request, response);

    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }


}