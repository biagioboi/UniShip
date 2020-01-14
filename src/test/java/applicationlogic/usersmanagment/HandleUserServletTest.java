package applicationlogic.usersmanagment;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.beans.Azienda;
import storage.beans.Utente;

public class HandleUserServletTest extends Mockito {

  private MockHttpServletResponse response;
  private MockHttpServletRequest request;
  private HandleUserServlet servlet;

  private static Utente carrierOffice;
  private static Utente azienda;


  @BeforeAll
  static void setUp() {

    try {
      azienda = new Utente("info@crazytech.it", "Prova", "password", "azienda");
      TestingUtility.createUtente(azienda);

      Azienda prova = new Azienda("info@crazytech.it", "Prova", "password", "03944080657",
          "via prova 2", "pippo", "5485", 55);
      TestingUtility.createAzienda(prova);

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
      TestingUtility.deleteUtente(carrierOffice.getEmail().toLowerCase());
      TestingUtility.deleteUtente(azienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente("info@crazytech.com");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @BeforeEach
  void carica() {
    servlet = new HandleUserServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();

    request.getSession().setAttribute("utente", carrierOffice);
    request.getSession().setAttribute("login", "si");

  }

  @Test
  public void TC_10_01() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email troppo corta\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_02() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email",
        "infoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfo@crazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email troppo lunga\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_03() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "infocrazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email non valida\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_04() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.it");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email gia' registrata\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void TC_10_05() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Nome troppo corto.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_06() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "UnNomeDiUnAziendaDavveroMoltoLungoEImpossibilesrl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Nome troppo lungo.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_07() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "Crazy_Tech_srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Nome invalido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_08() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "039440806cinque8");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Partita IVA invalida.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_09() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080657");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Partita IVA gia' presente.\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void TC_10_10() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Indirizzo troppo piccolo.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_11() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10/ Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Indirizzo non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void TC_10_12() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Rappresentante troppo piccolo.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_13() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mari@ Verd!");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Rappresentante non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_14() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Codice ateco troppo corto.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_15() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.;+10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Codice ateco non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_10_16() throws ServletException, IOException {
    request.setParameter("action", "addCompany");
    request.setParameter("email", "info@crazytech.com");
    request.setParameter("nome", "Crazy Tech srl");
    request.setParameter("piva", "03944080658");
    request.setParameter("indirizzo", "Via Napoli 10, Roma");
    request.setParameter("rappresentante", "Mario Verdi");
    request.setParameter("codAteco", "C.500.10");
    request.setParameter("numeroDipendenti", "20");
    servlet.doPost(request, response);
    assertEquals("{\"status\":\"200\"}",
        response.getContentAsString().trim());
  }
}
