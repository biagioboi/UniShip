package applicationlogic.tirociniomanagment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import applicationlogic.TestingUtility;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;
import storage.dao.TirocinioDao;

public class TirocinioServletWhiteBoxTest extends Mockito {


  private static Utente ufficioCarriere;
  private static Utente admin;
  private static Studente studente;
  private static Azienda azienda;
  private static Tirocinio tirocinio;
  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static TirocinioServlet servlet = new TirocinioServlet();
  private TirocinioDao dao;

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

      ClassLoader classLoader = getClass().getClassLoader();
      URL resource = classLoader.getResource("prova.pdf");
      tirocinio = new Tirocinio(999, Tirocinio.NON_COMPLETO, 7000, "pippo", 500, resource.getPath(),
          studente, azienda, "not extist");
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
      TestingUtility.deleteUtente(admin.getEmail().toLowerCase());
      TestingUtility.deleteUtente(ufficioCarriere.getEmail().toLowerCase());
      TestingUtility.deleteTirocinio(tirocinio);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @BeforeEach
  public void setUp() throws Exception {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    dao = spy(TirocinioDao.class);
    response = new MockHttpServletResponse();
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("tirocinioDao"), dao);

  }

  @AfterEach
  public void clean() throws Exception {
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("tirocinioDao"),
        new TirocinioDao());
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
  public void loginNull2() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(null);
    when(session.getAttribute("login")).thenReturn("si");
    servlet.doPost(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }

  @Test
  public void loginNull3() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(null);
    when(session.getAttribute("login")).thenReturn("no");
    servlet.doPost(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }

  @Test
  public void loginNull4() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    servlet.doGet(request, response);
    assertEquals("", response.getContentAsString());
  }

  @Test
  public void validateInternshipStudente() throws IOException, ServletException {

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("validateInternship");

    servlet.doPost(request,response);

    assertEquals("{\"description\":\"Non puoi validare questo tirocinio.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void validateInternship() throws IOException, ServletException{

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(ufficioCarriere);
    when(session.getAttribute("login")).thenReturn("si");

    when(request.getParameter("action")).thenReturn("validateInternship");
    when(request.getParameter("tirocinio")).thenReturn(null);
    servlet.doPost(request,response);
    assertEquals("{\"description\":\"id non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());

  }

  @Test
  public void validateInternship2() throws IOException,ServletException{

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(ufficioCarriere);
    when(session.getAttribute("login")).thenReturn("si");

    when(request.getParameter("action")).thenReturn("validateInternship");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(-1));
    servlet.doPost(request,response);
    assertEquals("{\"description\":\"La risposta non puo' essere vuota\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));

  }

  @Test
  public void validateInternship3() throws IOException, ServletException{

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(ufficioCarriere);
    when(session.getAttribute("login")).thenReturn("si");

    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));
    when(request.getParameter("action")).thenReturn("validateInternship");

    when(request.getParameter("risposta")).thenReturn(null);
    servlet.doPost(request,response);

    assertEquals("{\"description\":\"La risposta non puo' essere vuota\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void validateInternship4() throws IOException, ServletException{

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(ufficioCarriere);
    when(session.getAttribute("login")).thenReturn("si");

    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));
    when(request.getParameter("action")).thenReturn("validateInternship");

    when(request.getParameter("risposta")).thenReturn("");
    servlet.doPost(request,response);
    assertEquals("{\"description\":\"La risposta non puo' essere vuota\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void validateInternship5() throws IOException, ServletException{

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(ufficioCarriere);
    when(session.getAttribute("login")).thenReturn("si");

    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));
    when(request.getParameter("action")).thenReturn("validateInternship");

    when(request.getParameter("risposta")).thenReturn(RichiestaDisponibilita.ACCETTATA);
    servlet.doPost(request,response);
    assertEquals("{\"description\":\"Risposta inviata.\",\"status\":\"200\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void validateInternship6() throws IOException, ServletException{

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(admin);
    when(session.getAttribute("login")).thenReturn("si");

    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));
    when(request.getParameter("action")).thenReturn("validateInternship");

    when(request.getParameter("risposta")).thenReturn(RichiestaDisponibilita.ACCETTATA);
    servlet.doPost(request,response);
    assertEquals("{\"description\":\"Risposta inviata.\",\"status\":\"200\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void validateInternship7() throws IOException, ServletException,SQLException{

    TestingUtility.deleteTirocinio(tirocinio);
    tirocinio.setOreSvolte(tirocinio.getOreTotali());
    TestingUtility.createTirocinio(tirocinio);

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(admin);
    when(session.getAttribute("login")).thenReturn("si");

    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));
    when(request.getParameter("action")).thenReturn("validateInternship");

    when(request.getParameter("risposta")).thenReturn(RichiestaDisponibilita.ACCETTATA);
    servlet.doPost(request,response);
    assertEquals("{\"description\":\"Risposta inviata.\",\"status\":\"200\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }




}