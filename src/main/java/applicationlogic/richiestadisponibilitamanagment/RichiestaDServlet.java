package applicationlogic.richiestadisponibilitamanagment;

import com.google.gson.Gson;
import com.sun.deploy.net.HttpRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
      //throw new IllegalArgumentException("Devi effettuare il login.");
    }

    String action = request.getParameter("action");
    Gson obj = new Gson();
    Map<String, String> result = new HashMap<>();

    if (action != null && action.equals("sendRequest")) {
      try {
        if (sendAvailabilityRequest(request, response)) {
          result.put("status", "200");
          result.put("description", "Richiesta inviata.");
        } else {
          result.put("status", "400");
          result.put("description", "Errore generico.");
        }
      } catch (IllegalArgumentException e) {
        result.put("status", "422");
        result.put("description", e.getMessage());
      }

      PrintWriter out = response.getWriter();
      response.setContentType("application/json");
      out.println(obj.toJson(result));
    }

  }

  private boolean sendAvailabilityRequest(HttpServletRequest request,
      HttpServletResponse response) {

    StudenteInterface studenteDao = new StudenteDao();
    AziendaInterface aziendaDao = new AziendaDao();

    String emailAzienda = request.getParameter("azienda");
    String messaggio = request.getParameter("messaggio");

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

  private ArrayList<RichiestaDisponibilita> viewAvailabilityRequest(
      HttpServletRequest request, HttpServletResponse response) {

    Utente user = (Utente) request.getSession().getAttribute("utente");
    AziendaInterface aziendaDao = new AziendaDao();
    StudenteInterface studenteDao = new StudenteDao();

    try {
      RichiestaDisponibilitaInterface richiestaDao = new RichiestaDisponibilitaDao();
      ArrayList<RichiestaDisponibilita> result = null;

      if (user.getTipo().equals(Utente.AZIENDA)) {

        Azienda azienda = aziendaDao.doRetrieveByKey(user.getEmail());
        result = richiestaDao.doRetrieveByAzienda(azienda);

      } else if (user.getTipo().equals(Utente.STUDENTE)) {
        Studente studente = studenteDao.doRetrieveByKey(user.getEmail());
        result = richiestaDao.doRetrieveByStudente(studente);
      }

      return result;

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return null;
  }

  private ArrayList<RichiestaDisponibilita> viewAllAvailabilityRequest(
      HttpServletRequest request, HttpServletResponse response) {

    Utente user = (Utente) request.getSession().getAttribute("utente");

    if (!user.getTipo().equals(Utente.ADMIN) && !user.getTipo().equals(Utente.UFFICIO_CARRIERE)) {
      throw new IllegalArgumentException("Non hai l'autorizzazione per accedere a questi dati");
    }

    try {
      RichiestaDisponibilitaInterface richiestaDao = new RichiestaDisponibilitaDao();
      return richiestaDao.doRetrieveAll();

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return null;
  }

  private boolean respondToRequest(HttpServletRequest request, HttpServletResponse response) {

    String emailStudente = request.getParameter("studente");
    String motivazioni = request.getParameter("Messaggio");
    String risposta = request.getParameter("risposta");

    if (risposta == null) {
      throw new IllegalArgumentException("La risposta non puo' essere vuota");
    }

    if (emailStudente == null) {
      throw new IllegalArgumentException("Email dello studente non puo' essere vuota");
    }

    if (motivazioni == null) {
      throw new IllegalArgumentException("le motivazioni non possono essere vuote");
    }

    try {
      StudenteInterface studenteDao = new StudenteDao();
      AziendaInterface aziendaDao = new AziendaDao();
      UtenteInterface utenteDao = new UtenteDao();
      RichiestaDisponibilitaInterface richiestaDao = new RichiestaDisponibilitaDao();

      if (!utenteDao.doCheckRegister(emailStudente)) {
        throw new IllegalArgumentException("Email studente errata");
      }

      Utente user = (Utente) request.getSession().getAttribute("utente");
      Studente studente = studenteDao.doRetrieveByKey(emailStudente);
      Azienda azienda = aziendaDao.doRetrieveByKey(user.getEmail());
      RichiestaDisponibilita richiesta = richiestaDao.doRetrieveByKey(studente, azienda);

      richiesta.setMotivazioni(motivazioni);
      if (risposta.equals("accettata")) {
        richiesta.setStato(RichiestaDisponibilita.ACCETTATA);
      } else {
        richiesta.setStato(RichiestaDisponibilita.RIFIUTATA);
      }

      return richiestaDao.doChange(richiesta);


    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return false;

  }
}
