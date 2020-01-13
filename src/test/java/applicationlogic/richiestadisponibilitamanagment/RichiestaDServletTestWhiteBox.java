package applicationlogic.richiestadisponibilitamanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.DBOperation;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.RichiestaDisponibilitaDao;


public class RichiestaDServletTestWhiteBox extends Mockito {

  private static Utente utente;
  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static RichiestaDServlet servlet = new RichiestaDServlet();
  private RichiestaDisponibilitaDao dao;


  @BeforeEach
  public void setUp() throws Exception {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    dao = mock(RichiestaDisponibilitaDao.class);
    response = new MockHttpServletResponse();
    setFinalStatic(servlet.getClass().getDeclaredField("richiestaDao"), dao);

  }

  @AfterEach
  public void clean() throws Exception {
    setFinalStatic(servlet.getClass().getDeclaredField("richiestaDao"),
        new RichiestaDisponibilitaDao());
  }

  @BeforeEach
  public void addUtente() {
    try {
      utente = new Utente("f.ruocco@studenti.unisa.it", "Frank", "password", "studente");
      DBOperation.createUtente(utente);

      Date d = Date.valueOf("1998-06-01");
      Studente studente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password",
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      DBOperation.createStudente(studente);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @AfterEach
  public void cancellaUtente() {
    try {
      DBOperation.deleteUtente(utente.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
  public void sendRequestFalse() throws ServletException,IOException {

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(utente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("sendRequest");
    when(request.getParameter("azienda")).thenReturn("info@clarotech.it");
    when(request.getParameter("messaggio")).thenReturn(
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Errore generico.\",\"status\":\"400\"}",
        response.getContentAsString().trim());
  }


  static void setFinalStatic(Field field, Object newValue) throws Exception {
    field.setAccessible(true);

    // remove final modifier from field
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

    field.set(null, newValue);
  }


}