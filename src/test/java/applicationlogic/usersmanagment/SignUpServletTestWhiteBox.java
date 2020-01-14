package applicationlogic.usersmanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.TestingUtility;
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
import storage.dao.StudenteDao;
import storage.dao.UtenteDao;


public class SignUpServletTestWhiteBox extends Mockito {

  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static SignUpServlet servlet = new SignUpServlet();
  private StudenteDao dao;
  private static Utente studenteData;

  @BeforeEach
  public void load() {

    try {
      studenteData = new Utente("f.ruocco@studenti.unisa.it", "Frank", "password",
          "studente");
      TestingUtility.createUtente(studenteData);

      Date d = Date.valueOf("1998-06-01");
      Studente studente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password",
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(studente);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @AfterEach
  public void delete() {
    try {
      TestingUtility.deleteUtente(studenteData.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @BeforeEach
  public void setUp() throws Exception {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    dao = spy(StudenteDao.class);
    response = new MockHttpServletResponse();
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("studenteDao"), dao);

  }

  @AfterEach
  public void clean() throws Exception {
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("studenteDao"),
        new StudenteDao());
  }

  @Test
  public void sendRequestFalse() throws ServletException, IOException, SQLException {

    when(request.getSession()).thenReturn(session);
    when(request.getParameter("action")).thenReturn("signup");
    when(request.getParameter("email")).thenReturn("b.boi@studenti.unisa.it");
    when(request.getParameter("password")).thenReturn("password");
    when(request.getParameter("nome")).thenReturn("Biagio");
    when(request.getParameter("cognome")).thenReturn("Boi");
    when(request.getParameter("codiceFiscale")).thenReturn("BOIBGI99A05H703C");
    when(request.getParameter("matricola")).thenReturn("0512105125");
    when(request.getParameter("dataDiNascita")).thenReturn("1999-01-05");
    when(request.getParameter("cittadinanza")).thenReturn("Italia");
    when(request.getParameter("residenza")).thenReturn("Via Velia 2, Casal Velino");
    when(request.getParameter("numero")).thenReturn("3485813155");

    doReturn(false).when(dao).doSave(any(Studente.class));
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Errore sconosciuto.\",\"status\":\"400\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void sendRequestAlreadyLogged() throws ServletException, IOException, SQLException {

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studenteData);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("action")).thenReturn("signup");
    when(request.getParameter("email")).thenReturn("b.boi@studenti.unisa.it");
    when(request.getParameter("password")).thenReturn("password");
    when(request.getParameter("nome")).thenReturn("Biagio");
    when(request.getParameter("cognome")).thenReturn("Boi");
    when(request.getParameter("codiceFiscale")).thenReturn("BOIBGI99A05H703C");
    when(request.getParameter("matricola")).thenReturn("0512105125");
    when(request.getParameter("dataDiNascita")).thenReturn("1999-01-05");
    when(request.getParameter("cittadinanza")).thenReturn("Italia");
    when(request.getParameter("residenza")).thenReturn("Via Velia 2, Casal Velino");
    when(request.getParameter("numero")).thenReturn("3485813155");

    doReturn(false).when(dao).doSave(any(Studente.class));
    servlet.doGet(request, response);
    assertEquals("{\"redirect\":\"index.jsp\",\"status\":\"302\"}",
        response.getContentAsString().trim());
  }




}