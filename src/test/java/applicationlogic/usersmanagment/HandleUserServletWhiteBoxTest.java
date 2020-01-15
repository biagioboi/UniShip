package applicationlogic.usersmanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.TestingUtility;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Utente;


public class HandleUserServletWhiteBoxTest extends Mockito {

  private static Utente studente;
  private static Utente azienda;
  private static Utente ufficioCarriere;

  private static Azienda realAzienda;
  private static Studente realStudente;

  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static HandleUserServlet servlet = new HandleUserServlet();


  @BeforeEach
  public void load() {

    try {
      azienda = new Utente("info@prova.it", "Prova", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "azienda");
      TestingUtility.createUtente(azienda);

      realAzienda = new Azienda("info@prova.it", "Prova", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080652",
          "via prova 2", "pippo", "5485", 55);
      TestingUtility.createAzienda(realAzienda);

      studente = new Utente("f.ruocco@studenti.unisa.it", "Frank", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184",
          "studente");
      TestingUtility.createUtente(studente);

      Date d = Date.valueOf("1998-06-01");
      realStudente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184",
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(realStudente);

      ufficioCarriere = new Utente("carrieroffice@unisa.it", "Ufficio Carriere", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184",
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
  public void addCompanyLoggedAsStudente() throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("addCompany");

    servlet.doPost(request, response);

    assertEquals("{\"description\":\"Non autorizzato.\",\"status\":\"403\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void requestWithoutAction() throws ServletException, IOException, SQLException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("");

    servlet.doGet(request, response);

    assertEquals("{\"description\":\"Richiesta invalida.\",\"status\":\"400\"}",
        response.getContentAsString().trim());
  }


}