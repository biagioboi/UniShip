package applicationlogic.richiestadisponibilitamanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.usersmanagment.SignUpServlet;
import com.mysql.cj.xdevapi.SqlDataResult;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.DatabaseManager;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.StudenteDao;
import storage.dao.UtenteDao;
import storage.interfaces.UtenteInterface;

public class RichiestaDServletTest extends Mockito {

  private RichiestaDServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;

  private static Utente utente;


  @BeforeAll
  static void setUtente() throws IOException, SQLException, ParseException {
    PreparedStatement preparedStatement = null;
    PreparedStatement prepStat = null;
    Connection connection = null;
    int rs;

    try {
      utente = new Utente("f.ruocco@studenti.unisa.it", "Frank", "password", "studente");
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement(UtenteDao.SAVE);
      preparedStatement.setString(1, utente.getNome());
      preparedStatement.setString(2, utente.getTipo());
      preparedStatement.setString(3, utente.getPassword());
      preparedStatement.setString(4, utente.getEmail().toLowerCase());

      Date d= Date.valueOf("1998-06-01");
      Studente studente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password", "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      prepStat = connection.prepareStatement(StudenteDao.SAVE);
      prepStat.setString(1, studente.getEmail());
      prepStat.setString(2, studente.getCognome());
      prepStat.setString(3, studente.getCodiceFiscale());
      prepStat.setString(4, studente.getMatricola());
      prepStat.setDate(5, studente.getDataDiNascita());
      prepStat.setString(6, studente.getCittadinanza());
      prepStat.setString(7, studente.getResidenza());
      prepStat.setString(8, studente.getNumero());

      rs = preparedStatement.executeUpdate() + prepStat.executeUpdate();

    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    }

  }

  @BeforeEach
  void setUp() throws IOException {
    servlet = new RichiestaDServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    request.getSession().setAttribute("utente", utente);
    request.getSession().setAttribute("login", "si");
  }

  @AfterAll
  static void cancellaUtente() throws IOException, SQLException{
    PreparedStatement preparedStatement = null;
    Connection connection = null;
    int rs;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement("DELETE FROM utente WHERE email = ?");
      preparedStatement.setString(1, utente.getEmail().toLowerCase());

      rs = preparedStatement.executeUpdate();

    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    }


  }

  @Test
  public void TC_3_01() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "");
    request.setParameter("messaggio",
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email della azienda non puo\\u0027 essere vuota\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void TC_3_02() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "info@prova.it");
    request.setParameter("messaggio",
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email azienda errata\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_3_03() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "info@clarotech.it");
    request.setParameter("messaggio",
        "");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Il messaggio non puo\\u0027 essere vuoto\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_3_04() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "info@clarotech.it");
    request.setParameter("messaggio",
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti ma purtroppo non riesco ad inserire tutte le motivazioni qui perchè sono inutili ulteriori parole.");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Il messaggio non puo\\u0027 superare 200 caratteri\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void TC_3_05() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "info@clarotech.it");
    request.setParameter("messaggio",
        "Sono§§§§interessato @ svolgere il Tirocinio fo_rmativo presso di voi in quanto#sono_motivato dai vostri progetti.");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Messaggio non valido\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_3_06() throws ServletException, IOException {
    request.setParameter("action", "sendRequest");
    request.setParameter("azienda", "info@clarotech.it");
    request.setParameter("messaggio",
        "Sono interessato a svolgere il Tirocinio formativo presso di voi in quanto sono motivato dai vostri progetti.");

    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Richiesta inviata.\",\"status\":\"200\"}",
        response.getContentAsString().trim());
  }


}