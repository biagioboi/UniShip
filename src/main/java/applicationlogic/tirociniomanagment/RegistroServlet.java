package applicationlogic.tirociniomanagment;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
import storage.beans.AttivitaRegistro;
import storage.beans.Tirocinio;
import storage.beans.Utente;
import storage.dao.AttivitaRegistroDao;
import storage.dao.TirocinioDao;
import storage.interfaces.AttivitaRegistroInterface;
import storage.interfaces.TirocinioInterface;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {

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
        if (action.equals("addActivity")) {

          if (addActivity(request, response)) {
            result.put("status", "200");
            result.put("description", "Attivita' aggiunta.");
          } else {
            result.put("status", "400");
            result.put("description", "Errore generico.");
          }

          out.println(obj.toJson(result));

        } else if (action.equals("viewRegister")) {
          out.println(obj.toJson(viewRegister(request, response)));

        }
      } catch (IllegalArgumentException e) {
        result.put("status", "422");
        result.put("description", e.getMessage());

        out.println(obj.toJson(result));
      }


    }


  }

  private ArrayList<AttivitaRegistro> viewRegister(HttpServletRequest request,
      HttpServletResponse response) {

    String tirocinioId = request.getParameter("tirocinio");
    if (tirocinioId == null || tirocinioId.equals("")) {
      throw new IllegalArgumentException("Tirocinio non valido");
    }
    try {

      Tirocinio tirocinio = tirocinioDao.doRetrieveByKey(Integer.parseInt(tirocinioId));

      Utente user = (Utente) request.getSession().getAttribute("utente");

      if (!user.getTipo().equals(Utente.UFFICIO_CARRIERE) && !user.getTipo().equals(Utente.ADMIN)) {
        if (!tirocinio.getAzienda().getEmail().equals(user.getEmail()) && !tirocinio.getStudente()
            .getEmail().equals(user.getEmail())) {
          throw new IllegalArgumentException("Non puoi accedere a queste informazioni.");
        }
      }

      return attivitaDao.doRetrieveByTirocinio(tirocinio);

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return null;

  }

  private boolean addActivity(HttpServletRequest request, HttpServletResponse response) {

    String tirocinioId = request.getParameter("tirocinio");
    if (tirocinioId == null || tirocinioId.equals("") || !tirocinioId.matches("[0-9]+")) {
      throw new IllegalArgumentException("Formato del tirocinio non valido.");
    }

    String ore = request.getParameter("oreSvolte");
    if (ore == null || ore.equals("")) {
      throw new IllegalArgumentException("Le ore non possono essere vuote");
    }

    String data = request.getParameter("data");
    if (data == null) {
      throw new IllegalArgumentException("La data non puo' essere vuota");
    } else if (!data.matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) {
      throw new IllegalArgumentException("Formato della data non valido");
    }

    String descrizione = request.getParameter("attivita");
    if (descrizione == null || descrizione.equals("")) {
      throw new IllegalArgumentException("La descrizione non puo' essere vuota");
    } else if (descrizione.length() > 50) {
      throw new IllegalArgumentException("Descrizione troppo lunga");
    } else if (!descrizione.matches("[A-z 0-9,;.'-]+")) {
      throw new IllegalArgumentException("Formato della descrizione non valido");
    }

    try {

      Tirocinio tirocinio = tirocinioDao.doRetrieveByKey(Integer.parseInt(tirocinioId));

      if (tirocinio == null) {
        throw new IllegalArgumentException("Tirocinio non valido.");
      }
      Utente user = (Utente) request.getSession().getAttribute("utente");

      if (!tirocinio.getAzienda().getEmail().equals(user.getEmail()) && !tirocinio.getStudente()
          .getEmail().equals(user.getEmail())) {
        throw new IllegalArgumentException("Non puoi aggiungere attivita a questo tirocinio.");
      }

      if (tirocinio.getOreSvolte() >= tirocinio.getOreTotali()) {
        throw new IllegalArgumentException(
            "Non puoi aggiungere altre attivita ad questo tirocinio.");
      }

      AttivitaRegistro attivita = new AttivitaRegistro();
      attivita.setTirocinio(tirocinio);

      double minutes = 0;
      String[] split = ore.split(":");
      minutes += Double.parseDouble(split[0]) * 60;
      minutes += Double.parseDouble(split[1]);
      attivita.setOreSvolte(minutes);
      tirocinio.setOreSvolte(tirocinio.getOreSvolte() + minutes);

      if (tirocinio.getOreSvolte() >= tirocinio.getOreTotali()) {
        tirocinio.setStato(Tirocinio.DA_VALUTARE);
      }

      attivita.setData(Date.valueOf(data));
      attivita.setAttivita(descrizione);

      return (tirocinioDao.doChange(tirocinio) && attivitaDao.doSave(attivita));

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return false;
  }

  AttivitaRegistroInterface attivitaDao = new AttivitaRegistroDao();
  TirocinioInterface tirocinioDao = new TirocinioDao();
}
