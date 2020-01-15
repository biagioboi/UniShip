package applicationlogic.usersmanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.TestingUtility;
import com.google.gson.Gson;
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
import storage.dao.AziendaDao;
import storage.dao.RichiestaDisponibilitaDao;
import storage.dao.StudenteDao;
import storage.dao.UtenteDao;


public class SessionServletWhiteBoxTest extends Mockito {

  private static Utente studente;
  private static Utente azienda;
  private static Utente ufficioCarriere;

  private static Azienda realAzienda;
  private static Studente realStudente;

  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static SessionServlet servlet = new SessionServlet();
  private StudenteDao studenteDao;
  private AziendaDao aziendaDao;
  private UtenteDao utenteDao;


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

      ufficioCarriere = new Utente("carrieroffice@unisa.it", "Ufficio Carriere", "password",
          "ufficio_carriere");
      TestingUtility.createUtente(ufficioCarriere);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @AfterEach
  public void delete() {
    try {
      TestingUtility.deleteUtente(azienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente(studente.getEmail().toLowerCase());
      TestingUtility.deleteUtente(ufficioCarriere.getEmail());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @BeforeEach
  public void setUp() throws Exception {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    studenteDao = spy(StudenteDao.class);
    aziendaDao = spy(AziendaDao.class);
    utenteDao = spy(UtenteDao.class);
    response = new MockHttpServletResponse();
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("studenteDao"), studenteDao);
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("aziendaDao"), aziendaDao);
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("utenteDao"), utenteDao);

  }

  @AfterEach
  public void clean() throws Exception {
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("studenteDao"),
        new StudenteDao());
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("aziendaDao"),
        new AziendaDao());
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("utenteDao"),
        new UtenteDao());
  }


  @Test
  public void loggedAsAzienda() throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("retrieveUserLogged");
    doReturn(realAzienda).when(aziendaDao).doRetrieveByKey(anyString());
    servlet.doGet(request, response);
    Gson obj = new Gson();
    assertEquals(obj.toJson(realAzienda), response.getContentAsString().trim());
  }

  @Test
  public void loggedAsStudente() throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("retrieveUserLogged");
    doReturn(realStudente).when(studenteDao).doRetrieveByKey(anyString());
    servlet.doPost(request, response);
    Gson obj = new Gson();
    assertEquals(obj.toJson(realStudente), response.getContentAsString().trim());
  }

  @Test
  public void loggedAsUfficio() throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(ufficioCarriere);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("retrieveUserLogged");

    servlet.doPost(request, response);
    Gson obj = new Gson();
    assertEquals(obj.toJson(ufficioCarriere), response.getContentAsString().trim());
  }

  @Test
  public void tryLogInNull() throws ServletException, IOException, SQLException {

    when(request.getParameter("action")).thenReturn("logIn");
    when(request.getParameter("email")).thenReturn(realStudente.getEmail());
    when(request.getParameter("password")).thenReturn(realStudente.getPassword());

    doThrow(new SQLException()).when(utenteDao).doRetrieveByKey(anyString());

    servlet.doPost(request, response);

    assertEquals("{\"description\":\"Generic error.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void retrieveWithoutLoggedUser() throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(null);
    when(session.getAttribute("login")).thenReturn(null);
    when(request.getParameter("action")).thenReturn("retrieveUserLogged");

    servlet.doPost(request, response);

    assertEquals("null", response.getContentAsString().trim());
  }


  @Test
  public void logOut() throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(null);
    when(session.getAttribute("login")).thenReturn("no");
    when(request.getParameter("action")).thenReturn("logOut");

    servlet.doPost(request, response);

    assertEquals("{\"redirect\":\"signin.html\",\"status\":\"302\"}",
        response.getContentAsString().trim());
  }


}