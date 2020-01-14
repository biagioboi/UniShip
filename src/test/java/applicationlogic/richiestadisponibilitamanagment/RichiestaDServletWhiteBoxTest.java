package applicationlogic.richiestadisponibilitamanagment;

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
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.RichiestaDisponibilitaDao;


public class RichiestaDServletWhiteBoxTest extends Mockito {

  private static Utente ufficioCarriere;
  private static Utente admin;
  private static Studente studente;
  private static Azienda azienda;
  private static RichiestaDisponibilita richiesta;
  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static RichiestaDServlet servlet = new RichiestaDServlet();
  private RichiestaDisponibilitaDao dao;


  @BeforeEach
  public void load() {

    try {
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

      ufficioCarriere = new Utente("carrieroffice@unisa.it", "Ufficio Carriere", "password",
          "ufficio_carriere");
      TestingUtility.createUtente(ufficioCarriere);

      admin = new Utente("admin@unisa.it", "Admin", "password",
          "admin");
      TestingUtility.createUtente(admin);


    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @AfterEach
  public void delete() {
    try {
      TestingUtility.deleteUtente(azienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente(studente.getEmail().toLowerCase());
      TestingUtility.deleteUtente(admin.getEmail().toLowerCase());
      TestingUtility.deleteUtente(ufficioCarriere.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @BeforeEach
  public void setUp() throws Exception {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    dao = spy(RichiestaDisponibilitaDao.class);
    response = new MockHttpServletResponse();
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("richiestaDao"), dao);

  }

  @AfterEach
  public void clean() throws Exception {
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("richiestaDao"),
        new RichiestaDisponibilitaDao());
  }


  @Test
  public void loginNull() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(null);
    when(session.getAttribute("login")).thenReturn(null);
    servlet.doPost(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }

  @Test
  public void sendRequestFalse() throws ServletException, IOException, SQLException {

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("sendRequest");
    when(request.getParameter("azienda")).thenReturn("info@clarotech.it");
    when(request.getParameter("messaggio")).thenReturn(
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");

    doReturn(false).when(dao).doSave(any(RichiestaDisponibilita.class));
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Errore generico.\",\"status\":\"400\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void sendExistRequest() throws ServletException, IOException, SQLException {

    richiesta = new RichiestaDisponibilita("none", RichiestaDisponibilita.VALUTAZIONE, azienda,
        studente);
    TestingUtility.createRichiestaDisponibilita(richiesta);

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("sendRequest");
    when(request.getParameter("azienda")).thenReturn(azienda.getEmail());
    when(request.getParameter("messaggio")).thenReturn(
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"E' gia' presente una richiesta.\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));

    TestingUtility.deleteRicDisp(richiesta);
  }

  @Test
  public void sendRequestError() throws ServletException, IOException, SQLException {

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("sendRequest");
    when(request.getParameter("azienda")).thenReturn(azienda.getEmail());
    when(request.getParameter("messaggio")).thenReturn(
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");

    doThrow(SQLException.class).when(dao).doSave(any(RichiestaDisponibilita.class));
    servlet.doGet(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }

  @Test
  public void viewAllRequestEmpty() throws ServletException, IOException, SQLException {

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(admin);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("viewAllRequest");

    servlet.doPost(request, response);
    assertEquals("[]", response.getContentAsString().trim());
  }

  @Test
  public void viewAllRequest2() throws ServletException, IOException, SQLException {

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("viewAllRequest");

    servlet.doPost(request, response);
    assertEquals(
        "{\"description\":\"Non hai l'autorizzazione per accedere a questi dati\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void viewRequestStudente() throws ServletException, IOException {

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("viewRequest");
    servlet.doPost(request, response);
    assertEquals("[]", response.getContentAsString().trim());

  }

  @Test
  public void viewRequestAzienda() throws ServletException, IOException {

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("viewRequest");

    servlet.doPost(request, response);
    assertEquals("[]", response.getContentAsString().trim());
  }

  @Test
  public void respondToRequest() throws ServletException, IOException {

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("studente")).thenReturn(null);
    when(request.getParameter("messaggio")).thenReturn(null);
    when(request.getParameter("risposta")).thenReturn(null);
    when(request.getParameter("action")).thenReturn("respondRequest");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"La risposta non puo' essere vuota\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void respondToRequest2() throws ServletException, IOException {

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("studente")).thenReturn(null);
    when(request.getParameter("messaggio")).thenReturn(null);
    when(request.getParameter("risposta")).thenReturn(RichiestaDisponibilita.ACCETTATA);
    when(request.getParameter("action")).thenReturn("respondRequest");

    servlet.doPost(request, response);
    assertEquals(
        "{\"description\":\"Email dello studente non puo' essere vuota\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void respondToRequest3() throws ServletException, IOException {

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("studente")).thenReturn(studente.getEmail());
    when(request.getParameter("messaggio")).thenReturn(null);
    when(request.getParameter("risposta")).thenReturn(RichiestaDisponibilita.ACCETTATA);
    when(request.getParameter("action")).thenReturn("respondRequest");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"le motivazioni non possono essere vuote\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void respondToRequest4() throws ServletException, IOException {

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("studente")).thenReturn(studente.getEmail() + "jdfjgd");
    when(request.getParameter("messaggio")).thenReturn("motivazioni");
    when(request.getParameter("risposta")).thenReturn(RichiestaDisponibilita.ACCETTATA);
    when(request.getParameter("action")).thenReturn("respondRequest");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email studente errata\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void respondToRequest5() throws ServletException, IOException, SQLException {

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("studente")).thenReturn(studente.getEmail());
    when(request.getParameter("messaggio")).thenReturn("motivazioni");
    when(request.getParameter("risposta")).thenReturn(RichiestaDisponibilita.ACCETTATA);
    when(request.getParameter("action")).thenReturn("respondRequest");

    doReturn(null).when(dao).doRetrieveByKey(any(Studente.class), any(Azienda.class));

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Richiesta non trovata.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void respondToRequest6() throws ServletException, IOException, SQLException {

    richiesta = new RichiestaDisponibilita("none", RichiestaDisponibilita.VALUTAZIONE, azienda,
        studente);
    TestingUtility.createRichiestaDisponibilita(richiesta);

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("studente")).thenReturn(studente.getEmail());
    when(request.getParameter("messaggio")).thenReturn("motivazioni");
    when(request.getParameter("risposta")).thenReturn(RichiestaDisponibilita.ACCETTATA);
    when(request.getParameter("action")).thenReturn("respondRequest");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Richiesta risposta.\",\"status\":\"200\"}",
        response.getContentAsString().trim());

    TestingUtility.deleteRicDisp(richiesta);
  }

  @Test
  public void respondToRequest7() throws ServletException, IOException, SQLException {

    richiesta = new RichiestaDisponibilita("none", RichiestaDisponibilita.VALUTAZIONE, azienda,
        studente);
    TestingUtility.createRichiestaDisponibilita(richiesta);

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("studente")).thenReturn(studente.getEmail());
    when(request.getParameter("messaggio")).thenReturn("motivazioni");
    when(request.getParameter("risposta")).thenReturn(RichiestaDisponibilita.RIFIUTATA);
    when(request.getParameter("action")).thenReturn("respondRequest");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Richiesta risposta.\",\"status\":\"200\"}",
        response.getContentAsString().trim());

    TestingUtility.deleteRicDisp(richiesta);
  }

  @Test
  public void respondToRequest8() throws ServletException, IOException, SQLException {

    richiesta = new RichiestaDisponibilita("none", RichiestaDisponibilita.VALUTAZIONE, azienda,
        studente);
    TestingUtility.createRichiestaDisponibilita(richiesta);

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("studente")).thenReturn(studente.getEmail());
    when(request.getParameter("messaggio")).thenReturn("motivazioni");
    when(request.getParameter("risposta")).thenReturn(RichiestaDisponibilita.RIFIUTATA);
    when(request.getParameter("action")).thenReturn("respondRequest");

    doThrow(SQLException.class).when(dao).doChange(any(RichiestaDisponibilita.class));

    servlet.doPost(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());

    TestingUtility.deleteRicDisp(richiesta);
  }

  @Test
  public void viewAllRequestEmpty2() throws ServletException, IOException, SQLException {

    when(request.getSession()).thenReturn(session);

    when(session.getAttribute("utente")).thenReturn(admin);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("viewAllRequest");

    doThrow(SQLException.class).when(dao).doRetrieveAll();
    servlet.doPost(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }

  @Test
  public void viewRequestStudente2() throws ServletException, IOException, SQLException {

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("viewRequest");
    servlet.doPost(request, response);

    doThrow(SQLException.class).when(dao).doRetrieveByStudente(any(Studente.class));
    servlet.doPost(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());

  }

  @Test
  public void viewRequestAzienda2() throws ServletException, IOException, SQLException {

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("viewRequest");
    servlet.doPost(request, response);

    doThrow(SQLException.class).when(dao).doRetrieveByAzienda(any(Azienda.class));
    servlet.doGet(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());

  }



}