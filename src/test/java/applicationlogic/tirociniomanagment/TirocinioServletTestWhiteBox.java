package applicationlogic.tirociniomanagment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import applicationlogic.TestingUtility;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.AssertThrows;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;

public class TirocinioServletTestWhiteBox extends Mockito {

  private MockHttpServletResponse response;
  private MockHttpServletRequest request;
  private TirocinioServlet servlet;

  private static Tirocinio firstTirocinio;
  private static Tirocinio secondTirocinio;

  private static Utente firstStudente;
  private static Utente secondStudente;

  private static Utente firstAzienda;

  private static Utente carrierOffice;


  @BeforeAll
  static void setUp() {

    try {
      firstAzienda = new Utente("info@prova.it", "Prova", "password", "azienda");
      TestingUtility.createUtente(firstAzienda);

      Azienda prova = new Azienda("info@prova.it", "Prova", "password", "03944080652",
          "via prova 2", "pippo", "5485", 55);
      TestingUtility.createAzienda(prova);

      firstStudente = new Utente("f.ruocco@studenti.unisa.it", "Frank", "password", "studente");
      TestingUtility.createUtente(firstStudente);

      Date d = Date.valueOf("1998-06-01");
      Studente ruocco = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password",
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(ruocco);

      secondStudente = new Utente("m.rossi@studenti.unisa.it", "Mario", "password", "studente");
      TestingUtility.createUtente(secondStudente);

      d = Date.valueOf("1998-01-05");
      Studente rossi = new Studente("m.rossi@studenti.unisa.it", "Mario", "password",
          "MRORSS98A05H703Q", "1234567891", d, "Italia", "Rofrano", "3485813158", "Rossi");
      TestingUtility.createStudente(rossi);

      firstTirocinio = new Tirocinio(0, Tirocinio.NON_COMPLETO, 7000, "pippo", 0, "not extis",
          ruocco, prova, "not extist");
      TestingUtility.createTirocinio(firstTirocinio);

      secondTirocinio = new Tirocinio(0, Tirocinio.IN_CORSO, 7000, "pippo", 500, "not extis", rossi,
          prova, "not extist");
      TestingUtility.createTirocinio(secondTirocinio);

      carrierOffice = new Utente("carrieroffice@unisa.it", "Ufficio Carriere", "password",
          "ufficio_carriere");
      TestingUtility.createUtente(carrierOffice);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @AfterAll
  static void delete() {
    try {
      TestingUtility.deleteUtente(firstAzienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente(firstStudente.getEmail().toLowerCase());
      TestingUtility.deleteUtente(secondStudente.getEmail().toLowerCase());
      TestingUtility.deleteTirocinio(firstTirocinio);
      TestingUtility.deleteTirocinio(secondTirocinio);
      TestingUtility.deleteUtente(carrierOffice.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @BeforeEach
  void carica() {
    servlet = new TirocinioServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
  }
  @Test
  //branch !login.equals("si")
  public void login1() throws Exception{
    request.getSession().setAttribute("utente", null);
    request.getSession().setAttribute("login", "");
    servlet.doPost(request,response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }

  //branch no user
  @Test
  public void login2() throws Exception{
    request.getSession().setAttribute("utente", null);
    request.getSession().setAttribute("login", "si");
    servlet.doPost(request,response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }

  //branch no action
  @Test
  public void nullAction() throws Exception{
    request.getSession().setAttribute("utente", carrierOffice);
    request.getSession().setAttribute("login", "si");
    servlet.doPost(request,response);
    assertEquals("", response.getContentAsString().trim());
  }

  @Test
  //branch !user.getTipo().equals(Utente.UFFICIO_CARRIERE) && !user.getTipo().equals(Utente.ADMIN)
  public void validateInternship1() throws ServletException, IOException {
    request.getSession().setAttribute("utente", firstStudente);
    request.getSession().setAttribute("login", "si");
    request.setParameter("action","validateInternship");
    servlet.doPost(request,response);
    assertEquals("{\"description\":\"Non puoi validare questo tirocinio.\",\"status\":\"422\"}", response.getContentAsString().trim());
  }

  @Test
  //branch !user.getTipo().equals(Utente.UFFICIO_CARRIERE) && !user.getTipo().equals(Utente.ADMIN)
  public void viewInternshipByFilter1() throws ServletException, IOException {
    request.getSession().setAttribute("utente", firstStudente );
    request.getSession().setAttribute("login", "si");
    request.setParameter("action","viewInternship");
    servlet.doPost(request,response);
    assertEquals("{\"description\":\"Non puoi accedere a queste informazioni.\",\"status\":\"422\"}", response.getContentAsString().trim());
  }

}