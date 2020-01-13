package applicationlogic.richiestadisponibilitamanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.DBOperation;
import java.io.IOException;
import java.sql.Connection;
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
import org.mockito.Spy;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.DatabaseManager;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.RichiestaDisponibilitaDao;
import storage.interfaces.RichiestaDisponibilitaInterface;

public class RichiestaDServletTestWhiteBox extends Mockito {

  private static Utente utente;
  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static RichiestaDServlet servlet = spy(new RichiestaDServlet());
  private RichiestaDisponibilitaDao dao;


  @BeforeEach
  public void setUp() {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    dao = mock(RichiestaDisponibilitaDao.class);
    response = new MockHttpServletResponse();

  }

  @BeforeEach
  void addUtente() {
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
  void cancellaUtente() {
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
  public void Null() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(utente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("sendRequest");
    when(request.getParameter("azienda")).thenReturn("info@clarotech.it");
    when(request.getParameter("messaggio")).thenReturn("Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");

    servlet.doPost(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }



}