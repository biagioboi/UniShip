package applicationlogic.tirociniomanagment;

import applicationlogic.Utility.TestingUtility;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.TirocinioDao;

public class PdfServletTestWhiteBox extends Mockito {

  private static Utente ufficioCarriere;
  private static Utente admin;
  private static Studente studente;
  private static Azienda azienda;
  private static RichiestaDisponibilita richiesta;
  private HttpServletRequest request;
  private MockHttpServletResponse response;
  private HttpSession session;
  private static PdfServlet servlet = new PdfServlet();
  private TirocinioDao dao;


  @BeforeEach
  public void load() {

    try {
      Utente aziendaData = new Utente("info@prova.it", "Prova", "password", "azienda");
      TestingUtility.createUtente(aziendaData);

      azienda = new Azienda("info@prova.it", "Prova", "password", "03944080652",
          "via prova 2", "pippo", "5485", 55);
      TestingUtility.createAzienda(azienda);

      Utente studenteData = new Utente("f.ruocco@studenti.unisa.it", "Frank", "password",
          "studente");
      TestingUtility.createUtente(studenteData);

      Date d = Date.valueOf("1998-06-01");
      studente = new Studente("f.ruocco@studenti.unisa.it", "Frank", "password",
          "RCCFNC98H01H501E", "1234567891", d, "Italia", "Vallo", "3485813158", "Ruocco");
      TestingUtility.createStudente(studente);

      ufficioCarriere = new Utente("carrieroffice@unisa.it", "Ufficio Carriere", "password",
          "ufficio_carriere");
      TestingUtility.createUtente(ufficioCarriere);

      admin = new Utente("admin@unisa.it", "Admin", "password",
          "admin");
      TestingUtility.createUtente(admin);


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


}