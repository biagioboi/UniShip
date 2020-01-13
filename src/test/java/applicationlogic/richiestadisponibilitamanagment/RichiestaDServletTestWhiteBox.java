package applicationlogic.richiestadisponibilitamanagment;

import static org.junit.jupiter.api.Assertions.*;

import applicationlogic.DBOperation;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.beans.Studente;
import storage.beans.Utente;

public class RichiestaDServletTestWhiteBox extends Mockito {

  private static Utente utente;
  private HttpServletRequest request;
  private MockHttpServletResponse response;

  @BeforeEach
  private void setUp() {
    request = mock(HttpServletRequest.class);

  }


  static void addUtente() {
    try {
      utente = new Utente("f.ruocco@studenti.unisa.it", "Frank", "password", "studente");
      DBOperation.createUtente(utente);

      Date d = Date.valueOf("1998-06-01");
      Studente studente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password",
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      DBOperation.createStudente(studente);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  static void cancellaUtente() {
    try {
      DBOperation.deleteUtente(utente.getEmail().toLowerCase());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


}