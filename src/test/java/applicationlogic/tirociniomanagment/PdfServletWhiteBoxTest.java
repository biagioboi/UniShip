package applicationlogic.tirociniomanagment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import applicationlogic.TestingUtility;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;
import storage.dao.TirocinioDao;

public class PdfServletWhiteBoxTest extends Mockito {

  private static Utente ufficioCarriere;
  private static Utente admin;
  private static Studente studente;
  private static Azienda azienda;
  private static Tirocinio tirocinio;
  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static PdfServlet servlet = new PdfServlet();
  private TirocinioDao dao;


  @BeforeEach
  public void load() {

    try {
      Utente aziendaData = new Utente("info@prova.it", "Prova", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "azienda");
      TestingUtility.createUtente(aziendaData);

      azienda = new Azienda("info@prova.it", "Prova", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080652",
          "via prova 2", "pippo", "5485", 55);
      TestingUtility.createAzienda(azienda);

      Utente studenteData = new Utente("f.ruocco@studenti.unisa.it", "Frank", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184",
          "studente");
      TestingUtility.createUtente(studenteData);

      Date d = Date.valueOf("1998-06-01");
      studente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184",
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(studente);

      ufficioCarriere = new Utente("carrieroffice@unisa.it", "Ufficio Carriere", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184",
          "ufficio_carriere");
      TestingUtility.createUtente(ufficioCarriere);

      admin = new Utente("admin@unisa.it", "Admin", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184",
          "admin");
      TestingUtility.createUtente(admin);

      ClassLoader classLoader = getClass().getClassLoader();
      URL resource = classLoader.getResource("prova.pdf");
      tirocinio = new Tirocinio(999, Tirocinio.NON_COMPLETO, 7000, "pippo", 500, resource.getPath(),
          studente, azienda, "not extist");
      TestingUtility.createTirocinio(tirocinio);


    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @AfterEach
  public void delete() {
    try {
      TestingUtility.deleteUtente(azienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente(studente.getEmail().toLowerCase());
      TestingUtility.deleteUtente(admin.getEmail().toLowerCase());
      TestingUtility.deleteUtente(ufficioCarriere.getEmail().toLowerCase());
      TestingUtility.deleteTirocinio(tirocinio);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @BeforeEach
  public void setUp() throws Exception {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    dao = spy(TirocinioDao.class);
    response = new MockHttpServletResponse();
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("tirocinioDao"), dao);

  }

  @AfterEach
  public void clean() throws Exception {
    TestingUtility.setFinalStatic(servlet.getClass().getDeclaredField("tirocinioDao"),
        new TirocinioDao());
  }


  @Test
  public void showPdfNoUser() throws Exception {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(null);
    when(session.getAttribute("login")).thenReturn(null);
    when(request.getParameter("tirocinio")).thenReturn(null);
    servlet.doGet(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }

  @Test
  public void showPdfNoUserDoPost() throws Exception {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(null);
    when(session.getAttribute("login")).thenReturn(null);
    when(request.getParameter("tirocinio")).thenReturn(null);
    servlet.doPost(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }

  @Test
  public void showPdfNoLogin() throws Exception {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn(null);
    when(request.getParameter("tirocinio")).thenReturn(null);
    servlet.doGet(request, response);
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
  }

  @Test
  public void showPdfNoTirocinio() throws Exception {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("tirocinio")).thenReturn(null);
    servlet.doGet(request, response);
    assertEquals("{\"description\":\"Tirocinio non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void showPdfWrogStudente() throws Exception {
    when(request.getSession()).thenReturn(session);
    String oldEmail = studente.getEmail();
    studente.setEmail(oldEmail + "fs");

    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));

    ServletContext context = spy(ServletContext.class);
    when(request.getServletContext()).thenReturn(context);

    servlet.doGet(request, response);

    studente.setEmail(oldEmail);

    assertEquals("{\"description\":\"Non puoi accedere a questo file.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void showPdfWrogAzienda() throws Exception {
    when(request.getSession()).thenReturn(session);
    String oldEmail = azienda.getEmail();
    azienda.setEmail(oldEmail + "fs");

    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));

    ServletContext context = spy(ServletContext.class);
    when(request.getServletContext()).thenReturn(context);

    servlet.doGet(request, response);

    azienda.setEmail(oldEmail);

    assertEquals("{\"description\":\"Non puoi accedere a questo file.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void showPdf() throws Exception {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));

    ServletContext context = spy(ServletContext.class);
    when(request.getServletContext()).thenReturn(context);

    servlet.doGet(request, response);
    assertEquals("application/octet-stream",response.getContentType());
  }

  @Test
  public void showPdfError() throws Exception {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));

    ServletContext context = spy(ServletContext.class);
    when(request.getServletContext()).thenReturn(context);

    doThrow(SQLException.class).when(dao).doRetrieveByKey(anyInt());

    servlet.doGet(request, response);
    assertEquals("{\"description\":\"Errore generico.\",\"status\":\"400\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void uploadPdfError() throws Exception {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");

    Part part = mock(Part.class, Mockito.CALLS_REAL_METHODS);
    when(part.getInputStream()).thenReturn(new FileInputStream(tirocinio.getPath()));
    when(part.getSize()).thenReturn(10L);
    when(part.getSubmittedFileName()).thenReturn("prova.pdf");

    when(request.getParameter("action")).thenReturn("uploadPdf");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));
    when(request.getPart("file")).thenReturn(part);


    doThrow(SQLException.class).when(dao).doChange(any(Tirocinio.class));

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Errore generico.\",\"status\":\"400\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void uploadPdfErrorTirocinio() throws Exception {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(studente);
    when(session.getAttribute("login")).thenReturn("si");

    Part part = mock(Part.class, Mockito.CALLS_REAL_METHODS);
    when(part.getInputStream()).thenReturn(new FileInputStream(tirocinio.getPath()));
    when(part.getSize()).thenReturn(10L);
    when(part.getSubmittedFileName()).thenReturn("prova.pdf");

    when(request.getParameter("action")).thenReturn("uploadPdf");
    when(request.getParameter("tirocinio")).thenReturn(null);
    when(request.getPart("file")).thenReturn(part);

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Tirocinio non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void createPdfError() throws Exception {

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");

    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("6");
    when(request.getParameter("sede")).thenReturn("Via Napoli 10, Roma");
    when(request.getParameter("obiettivi"))
        .thenReturn("Progettazione e creazione di un e-commerce funzionate");
    when(request.getParameter("competenze"))
        .thenReturn("Lo studente deve avere delle conoscenze in merito alla programmazione web");
    when(request.getParameter("attivita"))
        .thenReturn("Lo studente deve seguire dei corsi all'interno dell'azienda");
    when(request.getParameter("modalita"))
        .thenReturn("Lo studente deve seguire la routine di un dipendente della nostra azienda");
    when(request.getParameter("dataInizio")).thenReturn("2020-02-03");
    when(request.getParameter("dataFine")).thenReturn("2020-03-03");
    when(request.getParameter("orario")).thenReturn("9-13 14-18 da Lunedì a Venerdì");
    when(request.getParameter("numeroRc")).thenReturn("0469875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));

    doThrow(SQLException.class).when(dao).doSave(any(Tirocinio.class));

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Errore generico.\",\"status\":\"400\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void createPdfErrorStudente() throws Exception {

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("utente")).thenReturn(azienda);
    when(session.getAttribute("login")).thenReturn("si");

    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("6");
    when(request.getParameter("sede")).thenReturn("Via Napoli 10, Roma");
    when(request.getParameter("obiettivi"))
        .thenReturn("Progettazione e creazione di un e-commerce funzionate");
    when(request.getParameter("competenze"))
        .thenReturn("Lo studente deve avere delle conoscenze in merito alla programmazione web");
    when(request.getParameter("attivita"))
        .thenReturn("Lo studente deve seguire dei corsi all'interno dell'azienda");
    when(request.getParameter("modalita"))
        .thenReturn("Lo studente deve seguire la routine di un dipendente della nostra azienda");
    when(request.getParameter("dataInizio")).thenReturn("2020-02-03");
    when(request.getParameter("dataFine")).thenReturn("2020-03-03");
    when(request.getParameter("orario")).thenReturn("9-13 14-18 da Lunedì a Venerdì");
    when(request.getParameter("numeroRc")).thenReturn("0469875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase()+"fd");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));

    doThrow(SQLException.class).when(dao).doSave(any(Tirocinio.class));

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email studente errata\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

}