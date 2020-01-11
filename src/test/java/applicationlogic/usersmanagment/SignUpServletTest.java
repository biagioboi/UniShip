package applicationlogic.usersmanagment;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
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
    assertEquals("{\"description\":\"Email too short\",\"status\":\"422\"}" + "\r\n", response.getContentAsString());

  }

}