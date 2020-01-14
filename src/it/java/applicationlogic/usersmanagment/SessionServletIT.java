package applicationlogic.usersmanagment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import applicationlogic.TestingUtility;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import storage.beans.Studente;
import storage.beans.Utente;

public class SessionServletIT {

  private MockHttpServletResponse response;
  private MockMultipartHttpServletRequest request;
  private SessionServlet servlet;

  private static Utente studente;

  @BeforeEach
  public void init() {
    servlet = new SessionServlet();
    response = new MockHttpServletResponse();
    request = new MockMultipartHttpServletRequest();
  }

  @BeforeAll
  static void setUp() {

    try {
      studente = new Utente("m.rossi@studenti.unisa.it", "Mario", "password", "studente");
      TestingUtility.createUtente(studente);

      Date d = Date.valueOf("1998-06-01");
      Studente st = new Studente("m.rossi@studenti.unisa.it", "Mario", "password",
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Rossi");
      TestingUtility.createStudente(st);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @AfterAll
  static void cancella() {
    try {
      TestingUtility.deleteUtente(studente.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void TC_6_01() throws ServletException, IOException {
    request.setParameter("email", "");
    request.setParameter("password", "password");
    request.setParameter("action", "logIn");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email troppo corta.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_6_02() throws ServletException, IOException {
    request.setParameter("email",
        "m.rossim.rossim.rossim.rossim.rossim.rossim.rossim.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("action", "logIn");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email troppo lunga.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_6_03() throws ServletException, IOException {
    request.setParameter("email", "m.ro1ssi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("action", "logIn");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email non presente.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_6_04() throws ServletException, IOException {
    request.setParameter("email", "m.rossi2@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("action", "logIn");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email non presente.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_6_05() throws ServletException, IOException {
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "pass");
    request.setParameter("action", "logIn");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Password troppo corta.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_6_06() throws ServletException, IOException {
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "passwo?rd");
    request.setParameter("action", "logIn");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Formato password non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_6_07() throws ServletException, IOException {
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "passwoord");
    request.setParameter("action", "logIn");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Credenziali non valide.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_6_08() throws ServletException, IOException {
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("action", "logIn");
    servlet.doPost(request, response);
    assertEquals("{\"redirect\":\"index.jsp\",\"status\":\"302\"}",
        response.getContentAsString().trim());
  }

}