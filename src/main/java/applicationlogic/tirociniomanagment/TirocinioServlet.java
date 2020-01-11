package applicationlogic.tirociniomanagment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import storage.interfaces.AziendaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.TirocinioInterface;

@WebServlet("/TirocinioServlet")
public class TirocinioServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    Gson obj = new GsonBuilder().serializeNulls().create();
    response.setContentType("application/json");
    Utente user = (Utente) request.getSession().getAttribute("utente");
    Map<String, String> result = new HashMap<>();

    if (user == null) {
      result.put("status", "403");
      result.put("description", "Not Authorized");
      out.println(obj.toJson(result));
    } else {
      try {
        String action = request.getParameter("action");

        if (action.equals("validateInternship")) {
          if (user.getTipo().equals(Utente.ADMIN) || user.getTipo()
              .equals(Utente.UFFICIO_CARRIERE)) {
            validateInternship(request, response);
            result.put("status", "200");
            result.put("description", "Richiesta inoltrata.");
            out.println(obj.toJson(result));
          } else {
            result.put("status", "403");
            result.put("description", "Not Authorized");
            out.println(obj.toJson(result));
          }
        } else if (action.equals("viewInternship")) {
          List<Tirocinio> tirocini = viewInternship(request, response);
          out.println(obj.toJson(tirocini));
        } else if (action.equals("changeState")) {
          changeState(request, response);
        } else if (action.equals("viewInternshipByFilter")) {
          viewInternshipByFilter(request, response);
        } else {
          result.put("status", "400");
          result.put("description", "Invalid Request");
          out.println(obj.toJson(result));
        }
      } catch (IllegalArgumentException e) {
        result.put("status", "422");
        result.put("description", e.getMessage());
        out.println(obj.toJson(result));
      } catch (Exception ex) {
        result.put("status", "422");
        result.put("description", "Generic error.");
        out.println(obj.toJson(result));
      }
    }
  }

  private ArrayList<Tirocinio> viewInternship(HttpServletRequest request,
      HttpServletResponse response) {

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

  private void changeState(HttpServletRequest request, HttpServletResponse response) {
    // TODO: implement viewInternshipByFilter

  }

  private void viewInternshipByFilter(HttpServletRequest request, HttpServletResponse response) {
    // TODO: implement viewInternshipByFilter

  }

  private boolean validateInternship(HttpServletRequest request, HttpServletResponse response) {

    Utente user = (Utente) request.getSession().getAttribute("utente");

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
        } else {
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
