package applicationlogic.tirociniomanagment;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.DatabaseManager;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.StudenteDao;
import applicationlogic.DBOperation;

public class PdfServletTest extends Mockito {

  private MockHttpServletResponse response;
  private MockHttpServletRequest request;
  private PdfServlet servlet;


  private static Utente utente;


  @BeforeAll
  static void setUtente() throws IOException, SQLException, ParseException {

    try {
      utente = new Utente("f.ruocco@studenti.unisa.it", "Frank", "password", "studente");
      DBOperation.createUtente(utente);

      Date d= Date.valueOf("1998-06-01");
      Studente studente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password", "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      DBOperation.createStudente(studente);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}