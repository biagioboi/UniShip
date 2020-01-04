package applicationlogic.tirociniomanagment;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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
import storage.dao.AziendaDao;
import storage.dao.StudenteDao;
import storage.dao.TirocinioDao;
import storage.interfaces.AttivitaRegistroInterface;
import storage.interfaces.AziendaInterface;
import storage.interfaces.StudenteInterface;
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


  }

  private ArrayList<AttivitaRegistro> viewRegister(HttpServletRequest request,
      HttpServletResponse response) {

    AttivitaRegistroInterface attivitaDao = new AttivitaRegistroDao();
    TirocinioInterface tirocinioDao = new TirocinioDao();

    String tirocinioId = request.getParameter("tirocinio");
    if (tirocinioId == null) {
      throw new IllegalArgumentException("Tirocinio non valido");
    }
    try {

      Tirocinio tirocinio = tirocinioDao.doRetrieveByKey(Integer.parseInt(tirocinioId));
      return attivitaDao.doRetrieveByTirocinio(tirocinio);

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return null;

  }

  private boolean addActivity(HttpServletRequest request, HttpServletResponse response) {

    AttivitaRegistroInterface attivitaDao = new AttivitaRegistroDao();
    TirocinioInterface tirocinioDao = new TirocinioDao();

    String tirocinioId = request.getParameter("tirocinio");
    if (tirocinioId == null) {
      throw new IllegalArgumentException("Tirocinio non valido");
    }

    String ore = request.getParameter("oreSvolte");
    if (ore == null) {
      throw new IllegalArgumentException("Le ore non possono essere vuote");
    }

    String data = request.getParameter("data");
    if (data == null) {
      throw new IllegalArgumentException("La data non puo' essere vuota");
    }

    String descrizione = request.getParameter("attivita");
    if (descrizione == null) {
      throw new IllegalArgumentException("La descrizione non puo' essere vuota");
    }

    try {

      Tirocinio tirocinio = tirocinioDao.doRetrieveByKey(Integer.parseInt(tirocinioId));
      AttivitaRegistro attivita = new AttivitaRegistro();
      attivita.setTirocinio(tirocinio);
      attivita.setOreSvolte(Time.valueOf(ore));
      attivita.setData(Date.valueOf(data));
      attivita.setAttivita(descrizione);

      return attivitaDao.doSave(attivita);

    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return false;
  }
}
