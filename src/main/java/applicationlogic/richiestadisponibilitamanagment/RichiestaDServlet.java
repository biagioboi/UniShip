package applicationlogic.richiestadisponibilitamanagment;

import com.sun.deploy.net.HttpRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.AziendaDao;
import storage.dao.RichiestaDisponibilitaDao;
import storage.dao.StudenteDao;
import storage.dao.UtenteDao;
import storage.interfaces.AziendaInterface;
import storage.interfaces.RichiestaDisponibilitaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.UtenteInterface;

@WebServlet("/RichiestaDServlet")
public class RichiestaDServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    Utente user = (Utente) session.getAttribute("utente");
    String login = (String) session.getAttribute("login");

    if (login == null || !login.equals("si") || user == null) {
      throw new IllegalArgumentException("Devi effettuare il login.");
    }

  }

  private boolean sendAvailabilityRequest(HttpServletRequest request,
      HttpServletResponse response) {

    StudenteInterface studenteDao = new StudenteDao();
    AziendaInterface aziendaDao = new AziendaDao();

    String emailAzienda = request.getParameter("azienda");
    String messaggio = request.getParameter("Messaggio");

    if (emailAzienda == null) {
      throw new IllegalArgumentException("Email della azienda non puo' essere vuota");
    }

    if (messaggio == null) {
      throw new IllegalArgumentException("Il messaggio non puo' essere vuoto");
    }

    try {
      UtenteInterface utenteDao = new UtenteDao();
      if (!utenteDao.doCheckRegister(emailAzienda)) {
        throw new IllegalArgumentException("Email azienda errata");
      }

      Utente user = (Utente) request.getSession().getAttribute("utente");
      Studente studente = studenteDao.doRetrieveByKey(user.getEmail());
      Azienda azienda = aziendaDao.doRetrieveByKey(emailAzienda);
      RichiestaDisponibilita richiesta = new RichiestaDisponibilita();

      richiesta.setMotivazioni(messaggio);
      richiesta.setAzienda(azienda);
      richiesta.setStudente(studente);
      richiesta.setStato(RichiestaDisponibilita.VALUTAZIONE);

      RichiestaDisponibilitaInterface richiestaDao = new RichiestaDisponibilitaDao();

      return richiestaDao.doSave(richiesta);

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return false;
  }

  private ArrayList<RichiestaDisponibilita> viewAllAvailabilityRequest(
      HttpServletRequest request, HttpServletResponse response) {

    Utente user = (Utente) request.getSession().getAttribute("utente");
    AziendaInterface aziendaDao = new AziendaDao();

    try {
      Azienda azienda = aziendaDao.doRetrieveByKey(user.getEmail());
      RichiestaDisponibilitaInterface richiestaDao = new RichiestaDisponibilitaDao();

      return richiestaDao.doRetrieveByAzienda(azienda);

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return null;
  }
}
