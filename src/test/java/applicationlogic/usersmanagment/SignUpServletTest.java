package applicationlogic.usersmanagment;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SignUpServletTest extends Mockito {

  private SignUpServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;

  @BeforeEach
  void setUp() throws IOException {
    servlet = new SignUpServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
  }

  @Test
  public void TC_1_01() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "");
    request.setParameter("password", "passsword");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email too short\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_02() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email",
        "m.rossim.rossim.rossim.rossim.rossim.rossim.rossim.rossi@studenti.unisa.it");
    request.setParameter("password", "passsword");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email too long\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_03() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.r_ossi@studenti.unisa.it");
    request.setParameter("password", "passsword");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email not valid\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_04() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "g.gullo@studenti.unisa.it");
    request.setParameter("password", "passsword");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email already register.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_05() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "passsw");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Password too short.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_06() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "p_assword");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Password invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_07() throws ServletException, IOException {
    //TODO: this one should be verified in javascript
  }

  @Test
  public void TC_1_08() throws ServletException, IOException {
    //TODO: this one should be verified in javascript
  }

  @Test
  public void TC_1_09() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Nome too short.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void TC_1_10() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "MarioMarioMarioMarioMarioMarioMarioMarioMario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Nome too long.\",\"status\":\"422\"}" + "\r\n",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_11() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mar1o");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Nome invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_12() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Cognome too short.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_13() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "RossiRossiRossiRossiRossiRossiRossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Cognome too long.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_14() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Ross1");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Cognome invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }
}