package applicationlogic.tirociniomanagment;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;
import applicationlogic.DBOperation;

public class PdfServletTest extends Mockito {

  private MockHttpServletResponse response;
  private MockMultipartHttpServletRequest request;
  private PdfServlet servlet;


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
    request = new MockMultipartHttpServletRequest();
    response = new MockHttpServletResponse();

    request.getSession().setAttribute("utente", studente);
    request.getSession().setAttribute("login", "si");


  }

  @Test
  public void TC_5_01() throws ServletException, IOException {

    InputStream file = mock(InputStream.class);
    Part part = mock(Part.class);
    when(part.getInputStream()).thenReturn(file);
    when(part.getSize()).thenReturn(0L);
    when(request.getPart("file")).thenReturn(part);


    request.addParameter("action", "uploadPdf");
    request.addParameter("tirocinio", String.valueOf(tirocinio.getId()));

    servlet.doPost(request, response);
    System.out.println(response.getContentAsString());
  }



}