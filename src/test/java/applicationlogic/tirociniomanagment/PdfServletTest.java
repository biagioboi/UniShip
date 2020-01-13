package applicationlogic.tirociniomanagment;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
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
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;
import applicationlogic.DBOperation;

public class PdfServletTest extends Mockito {

  private MockHttpServletResponse response;
  private HttpServletRequest request;
  private PdfServlet servlet;
  private HttpSession session;


  private static Tirocinio tirocinio;
  private static Utente azienda;
  private static Utente studente;


  @BeforeAll
  static void setUp(){

    try {
      azienda  = new Utente("info@prova.it", "Prova", "password", "azienda");
      DBOperation.createUtente(azienda);

      Azienda az = new Azienda("info@prova.it", "Prova", "password", "03944080652", "via prova 2", "pippo", "5485", 55);
      DBOperation.createAzienda(az);

      studente = new Utente("f.ruocco@studenti.unisa.it", "Frank", "password", "studente");
      DBOperation.createUtente(studente);

      Date d= Date.valueOf("1998-06-01");
      Studente st = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password", "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      DBOperation.createStudente(st);

      tirocinio = new Tirocinio(999, Tirocinio.NON_COMPLETO, 7000, "pippo", 500, "not extis", st, az, "not extist");
      DBOperation.createTirocinio(tirocinio);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @AfterAll
  static void delete() {
    try {
      DBOperation.deleteUtente(azienda.getEmail().toLowerCase());
      DBOperation.deleteUtente(studente.getEmail().toLowerCase());
      DBOperation.deleteTirocinio(tirocinio);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @BeforeEach
  void carica() {
    servlet = new PdfServlet();
    request = mock(HttpServletRequest.class,Mockito.CALLS_REAL_METHODS);
    session = mock(HttpSession.class,Mockito.CALLS_REAL_METHODS);
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



}