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
import org.mockito.internal.matchers.Any;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.AziendaDao;
import storage.dao.RichiestaDisponibilitaDao;
import storage.dao.StudenteDao;
import storage.dao.UtenteDao;
import storage.interfaces.AziendaInterface;
import storage.interfaces.RichiestaDisponibilitaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.UtenteInterface;

public class RichiestaDServletTest extends Mockito {

  private RichiestaDServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;
  private static StudenteInterface studenteDao;
  private static AziendaInterface aziendaDao;
  private static UtenteInterface utenteDao;
  private static RichiestaDisponibilitaInterface richiestaDao;

  private static Utente utente;

  @BeforeAll
  static void setUtente() {
    Date d = Date.valueOf("1998-06-01");
    utente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password",
        "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
    //TestingUtility.createUtente(utente);

    // TestingUtility.createStudente(studente);

  }


  @BeforeEach
  void setUp() throws Exception {
    servlet = new RichiestaDServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    request.getSession().setAttribute("utente", utente);
    request.getSession().setAttribute("login", "si");
    response = new MockHttpServletResponse();
    richiestaDao= mock(RichiestaDisponibilitaDao.class);
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("richiestaDao"), richiestaDao);
    utenteDao= mock(UtenteDao.class);
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("utenteDao"), utenteDao);
    aziendaDao= mock(AziendaDao.class);
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("aziendaDao"), aziendaDao);
    studenteDao= mock(StudenteDao.class);
    when(studenteDao.doRetrieveByKey(anyString())).thenReturn(null);
    when(aziendaDao.doRetrieveByKey(anyString())).thenReturn(null);
    when(studenteDao.doRetrieveByKey("f.ruocco@studenti.unisa.it")).thenReturn((Studente)utente);
    when(aziendaDao.doRetrieveByKey("info@clarotech.it")).thenReturn(new Azienda("info@clarotech.it","Paquale Di Franco", "password", "02188520544","via cardinale,16", "Paquale Di Franco","46692",20 ));
    when(utenteDao.doCheckRegister(anyString())).thenReturn(false);
    when(utenteDao.doCheckRegister("info@clarotech.it")).thenReturn(true);
    when(utenteDao.doCheckRegister("f.ruocco@studenti.unisa.it")).thenReturn(true);
    when(richiestaDao.doSave(any(RichiestaDisponibilita.class))).thenReturn(true);

    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("studenteDao"), studenteDao);
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
    request.setParameter("messaggio",
        "");

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