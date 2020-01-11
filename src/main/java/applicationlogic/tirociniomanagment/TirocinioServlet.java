package applicationlogic.tirociniomanagment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;
import storage.dao.AziendaDao;
import storage.dao.StudenteDao;
import storage.dao.TirocinioDao;
import storage.dao.UtenteDao;
import storage.interfaces.AziendaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.TirocinioInterface;
import storage.interfaces.UtenteInterface;

@WebServlet("/TirocinioServlet")
public class TirocinioServlet extends HttpServlet {

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

        if (action.equals("validateInternship")) {

          if (validateInternship(request, response)) {
            result.put("status", "200");
            result.put("description", "Risposta inviata.");
          } else {
            result.put("status", "400");
            result.put("description", "Errore generico.");
          }

          out.println(obj.toJson(result));

        } else if (action.equals("viewInternship")) {
          out.println(obj.toJson(viewInternship(request, response)));
        }else if (action.equals("viewInternshipByFilter")) {
          out.println(obj.toJson(viewInternshipByFilter(request, response)));
        }
      } catch (IllegalArgumentException e) {
        result.put("status", "422");
        result.put("description", e.getMessage());
        out.println(obj.toJson(result));
      } catch (Exception ex) {
        result.put("status", "422");
        result.put("description", "Errore generico.");
        out.println(obj.toJson(result));
      }

    }

  }

  private ArrayList<Tirocinio> viewInternship(HttpServletRequest request,
      HttpServletResponse response) {

    // se e' una azienda o uno studente ritorna i tirocini in cui
    // compaiono altrimenti ritorna tutti i tirocini

    Utente user = (Utente) request.getSession().getAttribute("utente");

    try {
      TirocinioInterface tirocinioDao = new TirocinioDao();
      ArrayList<Tirocinio> result = null;

      if (user.getTipo().equals(Utente.AZIENDA)) {
        AziendaInterface aziendaDao = new AziendaDao();
        Azienda azienda = aziendaDao.doRetrieveByKey(user.getEmail());
        result = tirocinioDao.doRetrieveByAzienda(azienda);

      } else if (user.getTipo().equals(Utente.STUDENTE)) {
        StudenteInterface studenteDao = new StudenteDao();
        Studente studente = studenteDao.doRetrieveByKey(user.getEmail());
        result = tirocinioDao.doRetrieveByStudente(studente);
      } else {
        result = tirocinioDao.doRetrieveAll();
      }

      return result;

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return null;

  }

  private ArrayList<Tirocinio> viewInternshipByFilter(HttpServletRequest request,
      HttpServletResponse response) {

    Utente user = (Utente) request.getSession().getAttribute("utente");
    if (!user.getTipo().equals(Utente.UFFICIO_CARRIERE) && !user.getTipo().equals(Utente.ADMIN)) {
      throw new IllegalArgumentException("Non puoi accedere a queste informazioni.");
    }

    String stato = request.getParameter("stato");

    if (stato == null && !stato.equals("Tutti") && !stato.equals(Tirocinio.ACCETTATA)
        && !stato.equals(Tirocinio.DA_VALUTARE) && !stato.equals(Tirocinio.DA_CONVALIDARE)
        && !stato.equals(Tirocinio.NON_COMPLETO) && !stato.equals(Tirocinio.RIFIUTATA)
        && !stato.equals(Tirocinio.IN_CORSO)) {

      throw new IllegalArgumentException("Inserire uno stato valido.");
    }

    try {
      TirocinioInterface tirocinioDao = new TirocinioDao();

      ArrayList<Tirocinio> result = tirocinioDao.doRetrieveAll();

      /*
       * se lo stato e' diverso da tutti , rimuovo tutti i tirocini
       * che hanno uno stato diverso da quello voluto
       *
       * */
      if (!stato.equals("Tutti")) {
        result.removeIf((t -> !t.getStato().equals(stato)));
      }

      UtenteInterface utenteDao = new UtenteDao();

      /*
       * se l'email dello studente e' diverso da null ,controllo che lo studente esista e
       * rimuovo tutti i tirocini che hanno uno studente diverso da quello voluto
       *
       * */
      String emailStudente = request.getParameter("studente");
      if (emailStudente != null && emailStudente.length() != 0) {
        if (!utenteDao.doCheckRegister(emailStudente)) {
          throw new IllegalArgumentException("Studente non prensente nel sistema.");
        }

        result.removeIf((t -> !t.getStudente().getEmail().equals(emailStudente)));
      }

      String emailAzienda = request.getParameter("azienda");
      if (emailAzienda != null && emailAzienda.length() != 0) {

        if (!utenteDao.doCheckRegister(emailAzienda)) {
          throw new IllegalArgumentException("Azienda non prensente nel sistema.");
        }

        result.removeIf((t -> !t.getAzienda().getEmail().equals(emailAzienda)));
      }

      return result;


    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return null;
  }

  private boolean validateInternship(HttpServletRequest request, HttpServletResponse response) {

    Utente user = (Utente) request.getSession().getAttribute("utente");

    if (!user.getTipo().equals(Utente.UFFICIO_CARRIERE) && !user.getTipo().equals(Utente.ADMIN)) {
      throw new IllegalArgumentException("Non puoi validare questo tirocinio.");
    }

    String id = request.getParameter("tirocinio");
    if (id == null || id.length() < 1) {
      throw new IllegalArgumentException("id non valido.");
    }

    String risposta = request.getParameter("risposta");
    if (risposta == null || risposta.equals("")) {
      throw new IllegalArgumentException("La risposta non puo' essere vuota");
    }

    try {

      TirocinioInterface tirocinioDao = new TirocinioDao();
      Tirocinio tirocinio = tirocinioDao.doRetrieveByKey(Integer.parseInt(id));

      if (risposta.equals(Tirocinio.ACCETTATA)) {
        if (user.getTipo().equals(Utente.ADMIN)) {
          if (tirocinio.getOreSvolte() >= tirocinio.getOreTotali()) {
            tirocinio.setStato(Tirocinio.ACCETTATA);
          } else {
            tirocinio.setStato(Tirocinio.IN_CORSO);
          }

        } else if (user.getTipo().equals(Utente.UFFICIO_CARRIERE)) {
          tirocinio.setStato(Tirocinio.DA_CONVALIDARE);
        }
      } else {

        String motivazioni = request.getParameter("motivazioni");
        if (motivazioni == null || motivazioni.equals("")) {
          throw new IllegalArgumentException("le motivazioni non possono essere vuote");
        }

        tirocinio.setMotivazioni(motivazioni);
        tirocinio.setStato(Tirocinio.RIFIUTATA);
      }

      return tirocinioDao.doChange(tirocinio);


    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    return false;
  }
}
