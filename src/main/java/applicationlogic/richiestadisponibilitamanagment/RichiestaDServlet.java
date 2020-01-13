package applicationlogic.richiestadisponibilitamanagment;

import com.google.gson.Gson;
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
    }

    String action = request.getParameter("action");
    Gson obj = new Gson();
    Map<String, String> result = new HashMap<>();

    PrintWriter out = response.getWriter();
    response.setContentType("application/json");

    if (action != null) {
      try {
        if (action.equals("sendRequest")) {

          if (sendAvailabilityRequest(request, response)) {
            result.put("status", "200");
            result.put("description", "Richiesta inviata.");
          } else {
            result.put("status", "400");
            result.put("description", "Errore generico.");
          }

          out.println(obj.toJson(result));

        } else if (action.equals("viewAllRequest")) {

          out.println(obj.toJson(viewAllAvailabilityRequest(request, response)));

        } else if (action.equals("viewRequest")) {

          out.println(obj.toJson(viewAvailabilityRequest(request, response)));

        } else if (action.equals("respondRequest")) {

          if (respondToRequest(request, response)) {
            result.put("status", "200");
            result.put("description", "Richiesta risposta.");
          } else {
            result.put("status", "400");
            result.put("description", "Errore generico.");
          }

          out.println(obj.toJson(result));
        }
      } catch (IllegalArgumentException e) {
        result.put("status", "422");
        result.put("description", e.getMessage());

        out.println(obj.toJson(result));
      }


    }

  }

  private boolean sendAvailabilityRequest(HttpServletRequest request,
      HttpServletResponse response) {

    String emailAzienda = request.getParameter("azienda");
    String messaggio = request.getParameter("messaggio");

    if (emailAzienda == null || emailAzienda.equals("")) {
      throw new IllegalArgumentException("Email della azienda non puo' essere vuota");
    }

    if (messaggio == null || messaggio.equals("")) {
      throw new IllegalArgumentException("Il messaggio non puo' essere vuoto");
    }

    if (messaggio.length() > 200) {
      throw new IllegalArgumentException("Il messaggio non puo' superare 200 caratteri");
    }

    if (!messaggio.matches("[a-zA-z 0-9,.]+")) {
      throw new IllegalArgumentException("Messaggio non valido");
    }

    try {
      if (!utenteDao.doCheckRegister(emailAzienda)) {
        throw new IllegalArgumentException("Email azienda errata");
      }

      Utente user = (Utente) request.getSession().getAttribute("utente");
      Studente studente = studenteDao.doRetrieveByKey(user.getEmail());
      Azienda azienda = aziendaDao.doRetrieveByKey(emailAzienda);

      RichiestaDisponibilitaInterface richiestaDao = new RichiestaDisponibilitaDao();
      if (richiestaDao.doRetrieveByKey(studente, azienda) != null) {
        throw new IllegalArgumentException("E' gia' presente una richiesta.");
      }

      RichiestaDisponibilita richiesta = new RichiestaDisponibilita();

      richiesta.setMotivazioni(messaggio);
      richiesta.setAzienda(azienda);
      richiesta.setStudente(studente);
      richiesta.setStato(RichiestaDisponibilita.VALUTAZIONE);

      return richiestaDao.doSave(richiesta);

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return false;
  }

  private ArrayList<RichiestaDisponibilita> viewAvailabilityRequest(
      HttpServletRequest request, HttpServletResponse response) {

    Utente user = (Utente) request.getSession().getAttribute("utente");

    try {
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
      return richiestaDao.doRetrieveAll();

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return null;
  }

  private boolean respondToRequest(HttpServletRequest request, HttpServletResponse response) {

    String emailStudente = request.getParameter("studente");
    String motivazioni = request.getParameter("messaggio");
    String risposta = request.getParameter("risposta");

    if (risposta == null || risposta.equals("")) {
      throw new IllegalArgumentException("La risposta non puo' essere vuota");
    }

    if (emailStudente == null || emailStudente.equals("")) {
      throw new IllegalArgumentException("Email dello studente non puo' essere vuota");
    }

    if (motivazioni == null || motivazioni.equals("")) {
      throw new IllegalArgumentException("le motivazioni non possono essere vuote");
    }

    try {

      if (!utenteDao.doCheckRegister(emailStudente)) {
        throw new IllegalArgumentException("Email studente errata");
      }

      Utente user = (Utente) request.getSession().getAttribute("utente");
      Studente studente = studenteDao.doRetrieveByKey(emailStudente);
      Azienda azienda = aziendaDao.doRetrieveByKey(user.getEmail());

      RichiestaDisponibilita richiesta = richiestaDao.doRetrieveByKey(studente, azienda);
      if (richiesta == null) {
        throw new IllegalArgumentException("Richiesta non trovata.");
      }

      richiesta.setMotivazioni(motivazioni);
      if (risposta.equals(RichiestaDisponibilita.ACCETTATA)) {
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

  private static StudenteInterface studenteDao = new StudenteDao();
  private static AziendaInterface aziendaDao = new AziendaDao();
  private static UtenteInterface utenteDao = new UtenteDao();
  private static RichiestaDisponibilitaInterface richiestaDao = new RichiestaDisponibilitaDao();
}
