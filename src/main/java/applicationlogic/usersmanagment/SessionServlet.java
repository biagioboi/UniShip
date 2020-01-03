package applicationlogic.usersmanagment;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import storage.beans.Utente;
import storage.dao.UtenteDao;

@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Gson obj = new Gson();
    HttpSession session = request.getSession();

    String action = request.getParameter("action");
    if (action != null && action.equals("retrieveUserLogged")) {
      Utente user = (Utente) session.getAttribute("utente");
      if (user != null) {
        response.getWriter().println(obj.toJson(user));
      } else {
        response.getWriter().println(obj.toJson(null));
      }
    } else if (action != null && action.equals("logIn")) {
      JsonObject result = new JsonObject();
      response.setContentType("application/json");
      try {
        Utente utente = login(request, response);
        session.setAttribute("utente", utente);
        session.setAttribute("login", "si");
        session.setAttribute("tipo", utente.getTipo());
        result.put("status", "302");
        result.put("redirect", "index.jsp");
      } catch (IllegalArgumentException e) {
        result.put("status", "422");
        result.put("description", e.getMessage());
      } catch (RuntimeException ex) {
        result.put("status", "422");
        result.put("description", "Generic error.");
      }

      PrintWriter out = response.getWriter();
      response.setContentType("application/json");
      out.println(obj.toJson(result));
    } else if (action != null && action.equals("logOut")) {

      JsonObject result = new JsonObject();
      response.setContentType("application/json");

      logout(request, response);

      result.put("status", "302");
      result.put("redirect", "signin.html");

      PrintWriter out = response.getWriter();
      response.setContentType("application/json");
      out.println(obj.toJson(result));
    }
  }

  private Utente login(HttpServletRequest request, HttpServletResponse response)
      throws RuntimeException {
    UtenteDao utenteDao = new UtenteDao();

    String email = request.getParameter("email");
    String password = request.getParameter("password");

    try {
      if (email.length() < 1) {
        throw new IllegalArgumentException("Email too short.");
      } else if (email.length() > 50) {
        throw new IllegalArgumentException("Email too long.");
      } else if (!email.matches("[0-9a-zA-Z.]+@studenti.unisa.it")) {
        throw new IllegalArgumentException("Email not valid.");
      } else if (!utenteDao.doCheckRegister(email)) {
        throw new IllegalArgumentException("Email not exist.");
      }

      if (!(utenteDao.doCheckLogin(email, password))) {
        throw new IllegalArgumentException("Credential not valid.");
      } else {
        return utenteDao.doRetrieveByKey(email);
      }
    } catch (SQLException ex) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return null;
    }
  }

  private void logout(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    session.invalidate();
  }
}
