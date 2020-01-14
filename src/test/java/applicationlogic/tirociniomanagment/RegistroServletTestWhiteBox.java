package applicationlogic.tirociniomanagment;

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
import storage.beans.Studente;
import storage.beans.Utente;


public class RegistroServletTestWhiteBox extends Mockito {

  private static Utente studente;
  private static Utente azienda;
  private static Utente ufficioCarriere;

  private static Azienda realAzienda;
  private static Studente realStudente;

  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static RegistroServlet servlet = new RegistroServlet();


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
    response = new MockHttpServletResponse();

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
  public void addActivityNull() throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(null);
    when(session.getAttribute("login")).thenReturn(null);

    servlet.doPost(request, response);

    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }


}