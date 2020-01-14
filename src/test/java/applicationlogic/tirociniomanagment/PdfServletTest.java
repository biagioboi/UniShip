package applicationlogic.tirociniomanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.TestingUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.PasswordHash;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;

public class PdfServletTest extends Mockito {

  private MockHttpServletResponse response;
  private HttpServletRequest request;
  private PdfServlet servlet;
  private HttpSession session;


  private static Tirocinio tirocinio;
  private static RichiestaDisponibilita ricDisp;

  private static Utente azienda;
  private static Utente studente;
  private static Utente secondAzienda;


  @BeforeAll
  static void setUp() {

    try {
      azienda = new Utente("info@prova.it", "Prova", PasswordHash.createHash("password"), "azienda");
      TestingUtility.createUtente(azienda);

      Azienda az = new Azienda("info@prova.it", "Prova", PasswordHash.createHash("password"), "03944080652", "via prova 2",
          "pippo", "5485", 55);
      TestingUtility.createAzienda(az);

      studente = new Utente("f.ruocco@studenti.unisa.it", "Frank", PasswordHash.createHash("password"), "studente");
      TestingUtility.createUtente(studente);

      Date d = Date.valueOf("1998-06-01");
      Studente st = new Studente("f.ruocco@studenti.unisa.it", "Frank", PasswordHash.createHash("password"),
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(st);

      tirocinio = new Tirocinio(999, Tirocinio.NON_COMPLETO, 7000, "pippo", 500, "not extis", st,
          az, "not extist");
      TestingUtility.createTirocinio(tirocinio);

      secondAzienda = new Utente("info@provaaa.it", "Prova", PasswordHash.createHash("password"), "azienda");
      TestingUtility.createUtente(secondAzienda);

      Azienda azi = new Azienda("info@provaaa.it", "Prova", PasswordHash.createHash("password"), "03944080650",
          "via prova 2",
          "pippo", "5485", 55);
      TestingUtility.createAzienda(azi);

      ricDisp = new RichiestaDisponibilita("none", RichiestaDisponibilita.ACCETTATA, azi, st);
      TestingUtility.createRichiestaDisponibilita(ricDisp);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @AfterAll
  static void delete() {
    try {
      TestingUtility.deleteUtente(azienda.getEmail().toLowerCase());
      TestingUtility.deleteUtente(studente.getEmail().toLowerCase());
      TestingUtility.deleteUtente(secondAzienda.getEmail().toLowerCase());
      TestingUtility.deleteTirocinio(tirocinio);
      TestingUtility.deleteRicDisp(ricDisp);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @BeforeEach
  void carica() {
    servlet = new PdfServlet();
    request = mock(HttpServletRequest.class, Mockito.CALLS_REAL_METHODS);
    session = mock(HttpSession.class, Mockito.CALLS_REAL_METHODS);
    response = new MockHttpServletResponse();

    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("utente")).thenReturn(studente);
    when(request.getSession().getAttribute("login")).thenReturn("si");


  }

  @Test
  public void TC_5_01() throws ServletException, IOException {

    // prendo la path della root della cartella resources per i test.
    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource("prova.pdf");

    // Apro il file
    File file = new File(resource.getFile());
    InputStream stream = new FileInputStream(file);
    Part part = mock(Part.class, Mockito.CALLS_REAL_METHODS);
    when(part.getInputStream()).thenReturn(stream);
    when(part.getSize()).thenReturn(10L);
    when(part.getSubmittedFileName()).thenReturn("prova.pdf");

    when(request.getParameter("action")).thenReturn("uploadPdf");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(999));
    when(request.getPart("file")).thenReturn(part);

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Non puoi accedere a questo tirocinio.\",\"status\":\"422\"}",
        response.getContentAsString().trim());

  }

  @Test
  public void TC_5_02() throws ServletException, IOException {

    // prendo la path della root della cartella resources per i test.
    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource("prova.txt");

    // Apro il file
    File file = new File(resource.getFile());
    InputStream stream = new FileInputStream(file);
    Part part = mock(Part.class, Mockito.CALLS_REAL_METHODS);
    when(part.getInputStream()).thenReturn(stream);
    when(part.getSize()).thenReturn(10L);
    when(part.getSubmittedFileName()).thenReturn("prova.txt");

    when(request.getParameter("action")).thenReturn("uploadPdf");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));
    when(request.getPart("file")).thenReturn(part);

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Estensione non valida.\",\"status\":\"422\"}",
        response.getContentAsString().trim());

  }


  @Test
  public void TC_5_03() throws ServletException, IOException {

    // prendo la path della root della cartella resources per i test.
    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource("prova.pdf");

    // Apro il file
    File file = new File(resource.getFile());
    InputStream stream = new FileInputStream(file);
    Part part = mock(Part.class, Mockito.CALLS_REAL_METHODS);
    when(part.getInputStream()).thenReturn(stream);
    when(part.getSize()).thenReturn(10L);
    when(part.getSubmittedFileName()).thenReturn("prova.pdf");

    when(request.getParameter("action")).thenReturn("uploadPdf");
    when(request.getParameter("tirocinio")).thenReturn(String.valueOf(tirocinio.getId()));
    when(request.getPart("file")).thenReturn(part);

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"file caricato con successo.\",\"status\":\"200\"}",
        response.getContentAsString().trim());

  }

  @Test
  public void TC_18_01() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(azienda);
    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("652");
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
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Numero cfu non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_02() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(azienda);
    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("6");
    when(request.getParameter("sede")).thenReturn("");
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
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Inserire una sede di svolgimento.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_03() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(azienda);
    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("6");
    when(request.getParameter("sede")).thenReturn("Via Napoli 10, R?oma");
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
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Sede svolgimento non valida.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_04() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("6");
    when(request.getParameter("sede")).thenReturn("Via Napoli 10, Roma");
    when(request.getParameter("obiettivi")).thenReturn("");
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
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Inserire gli obiettivi.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_05() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("6");
    when(request.getParameter("sede")).thenReturn("Via Napoli 10, Roma");
    when(request.getParameter("obiettivi"))
        .thenReturn("Progettazione e creazione di un e-commerc?e funzionate");
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
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Formato degli obiettivi non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_06() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("6");
    when(request.getParameter("sede")).thenReturn("Via Napoli 10, Roma");
    when(request.getParameter("obiettivi"))
        .thenReturn("Progettazione e creazione di un e-commerce funzionate");
    when(request.getParameter("competenze")).thenReturn("");
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
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Inserire le competenze.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_07() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("6");
    when(request.getParameter("sede")).thenReturn("Via Napoli 10, Roma");
    when(request.getParameter("obiettivi"))
        .thenReturn("Progettazione e creazione di un e-commerce funzionate");
    when(request.getParameter("competenze"))
        .thenReturn("Lo studente deve avere delle conoscenze in ?merito alla programmazione web");
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
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Formato delle competenze non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_08() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("6");
    when(request.getParameter("sede")).thenReturn("Via Napoli 10, Roma");
    when(request.getParameter("obiettivi"))
        .thenReturn("Progettazione e creazione di un e-commerce funzionate");
    when(request.getParameter("competenze"))
        .thenReturn("Lo studente deve avere delle conoscenze in merito alla programmazione web");
    when(request.getParameter("attivita")).thenReturn("");
    when(request.getParameter("modalita"))
        .thenReturn("Lo studente deve seguire la routine di un dipendente della nostra azienda");
    when(request.getParameter("dataInizio")).thenReturn("2020-02-03");
    when(request.getParameter("dataFine")).thenReturn("2020-03-03");
    when(request.getParameter("orario")).thenReturn("9-13 14-18 da Lunedì a Venerdì");
    when(request.getParameter("numeroRc")).thenReturn("0469875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Inserire le attivita'.\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void TC_18_09() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
    when(request.getParameter("action")).thenReturn("createPdf");
    when(request.getParameter("cfu")).thenReturn("6");
    when(request.getParameter("sede")).thenReturn("Via Napoli 10, Roma");
    when(request.getParameter("obiettivi"))
        .thenReturn("Progettazione e creazione di un e-commerce funzionate");
    when(request.getParameter("competenze"))
        .thenReturn("Lo studente deve avere delle conoscenze in merito alla programmazione web");
    when(request.getParameter("attivita"))
        .thenReturn("Lo studente deve segu?ire dei corsi all'interno dell'azienda");
    when(request.getParameter("modalita"))
        .thenReturn("Lo studente deve seguire la routine di un dipendente della nostra azienda");
    when(request.getParameter("dataInizio")).thenReturn("2020-02-03");
    when(request.getParameter("dataFine")).thenReturn("2020-03-03");
    when(request.getParameter("orario")).thenReturn("9-13 14-18 da Lunedì a Venerdì");
    when(request.getParameter("numeroRc")).thenReturn("0469875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Formato delle attivita' non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void TC_18_10() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
        .thenReturn("");
    when(request.getParameter("dataInizio")).thenReturn("2020-02-03");
    when(request.getParameter("dataFine")).thenReturn("2020-03-03");
    when(request.getParameter("orario")).thenReturn("9-13 14-18 da Lunedì a Venerdì");
    when(request.getParameter("numeroRc")).thenReturn("0469875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Inserire le modalita'.\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void TC_18_11() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
        .thenReturn("Lo studente deve? seguire la routine di un dipendente della nostra azienda");
    when(request.getParameter("dataInizio")).thenReturn("2020-02-03");
    when(request.getParameter("dataFine")).thenReturn("2020-03-03");
    when(request.getParameter("orario")).thenReturn("9-13 14-18 da Lunedì a Venerdì");
    when(request.getParameter("numeroRc")).thenReturn("0469875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Formato delle modalita' non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void TC_18_12() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
    when(request.getParameter("dataInizio")).thenReturn("2020-20-03");
    when(request.getParameter("dataFine")).thenReturn("2020-03-03");
    when(request.getParameter("orario")).thenReturn("9-13 14-18 da Lunedì a Venerdì");
    when(request.getParameter("numeroRc")).thenReturn("0469875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals(
        "{\"description\":\"Formato della data di inizio non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_13() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
    when(request.getParameter("dataFine")).thenReturn("2020-20-03");
    when(request.getParameter("orario")).thenReturn("9-13 14-18 da Lunedì a Venerdì");
    when(request.getParameter("numeroRc")).thenReturn("0469875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Formato della data di fine non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_14() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
    when(request.getParameter("orario")).thenReturn("");
    when(request.getParameter("numeroRc")).thenReturn("0469875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Inserire l'orario di lavoro.\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void TC_18_15() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
    when(request.getParameter("orario")).thenReturn("9-13 14-18 da Lunedì a Venerdì?");
    when(request.getParameter("numeroRc")).thenReturn("0469875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals(
        "{\"description\":\"Formato dell'orario di lavoro non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim().replace("\\u0027", "'"));
  }

  @Test
  public void TC_18_16() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
    when(request.getParameter("numeroRc")).thenReturn("");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Inserire il numero RC.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_17() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
    when(request.getParameter("numeroRc")).thenReturn("0469d875431");
    when(request.getParameter("polizza")).thenReturn("464612464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Formato del numero RC non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_18() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
    when(request.getParameter("polizza")).thenReturn("");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals(
        "{\"description\":\"Inserire la polizza assicurativa infortuni.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_19() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
    when(request.getParameter("polizza")).thenReturn("4646?12464");
    when(request.getParameter("studente")).thenReturn(studente.getEmail().toLowerCase());
    servlet.doPost(request, response);
    assertEquals(
        "{\"description\":\"Formato della polizza assicurativa infortuni non valido.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_18_20() throws ServletException, IOException {
    when(request.getSession().getAttribute("utente")).thenReturn(secondAzienda);
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
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"pdf creato con successo.\",\"status\":\"200\"}",
        response.getContentAsString().trim());
  }
}