package applicationlogic.usersmanagment;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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

    String action = request.getParameter("action");
    if (action != null && action.equals("retrieveUserLogged")) {
      Utente user = (Utente) request.getSession().getAttribute("utente");
      if (user != null) {
        response.getWriter().println(obj.toJson(user));
      } else {
        response.getWriter().println(obj.toJson(null));
      }
    }
    /*PrintWriter out = response.getWriter();
    StringBuilder sb = new StringBuilder();
    BufferedReader br = request.getReader();
    String str;
    while ((str = br.readLine()) != null) {
      sb.append(str);
    }
    JsonObject jsonRequest = Jsoner.deserialize(sb.toString(), new JsonObject());
    if (jsonRequest.containsKey("login")) {
      login(request, response);
    } else {
      logout(request, response);
    }*/
  }

  private void login(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    StringBuilder sb = new StringBuilder();
    BufferedReader br = request.getReader();
    String str;
    while ((str = br.readLine()) != null) {
      sb.append(str);
    }
    response.setContentType("application/json");

    try {
      JsonObject jsonRequest = Jsoner.deserialize(sb.toString(), new JsonObject());
      if (jsonRequest.containsKey("email")) {
        String email = (String) jsonRequest.get("email");
        if (email.length() < 1) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "Email too short");
          out.println(obj.toJson());
          return;
        } else if (email.length() > 50) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "Email too long");
          out.println(obj.toJson());
        } else if (!email.matches("[0-9a-zA-Z.]+@studenti.unisa.it")) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "Email not valid");
          out.println(obj.toJson());
        } else if (!new UtenteDao().doCheckRegister(email)) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "Email not in database");
          out.println(obj.toJson());
        }

        if (jsonRequest.containsKey("password")) {
          String password = (String) jsonRequest.get("password");
          if (new UtenteDao().doCheckLogin(email, password)) {
            JsonObject obj = new JsonObject();
            HttpSession session = request.getSession(true);
            session.setAttribute("utente", new UtenteDao().doRetrieveByKey(email));
            obj.put("status", "200");
            obj.put("description", "ok");
            out.println(obj.toJson());
          } else {
            JsonObject obj = new JsonObject();
            obj.put("status", "401");
            obj.put("description", "invalid credential");
            out.println(obj.toJson());
          }
        }
      }
    } catch (Exception ex) {
      JsonObject obj = new JsonObject();
      obj.put("status", "500");
      obj.put("description", "Internal Error");
      out.println(obj.toJson());
    }
  }

  private void logout(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    session.invalidate();
  }
}
