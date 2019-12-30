package applicationlogic.usersmanagment;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.StudenteDao;
import storage.dao.UtenteDao;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession ses = request.getSession(true);
    Utente user;

    user = (Utente) request.getSession().getAttribute("utente");
    if (user == null) {
      String action = request.getParameter("action");
      if (action != null && action.equals("signup")) {
        registrazione(request, response);
      }
    } else {
      Gson obj = new Gson();
      Map<String, String> result = new HashMap<>();
      result.put("status", "409");
      result.put("description", "utente gia' registrato.");
      PrintWriter out = response.getWriter();
      response.setContentType("application/json");
      out.println(obj.toJson(result));
    }
  }

  private void registrazione(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    Gson obj = new Gson();
    HashMap<String, String> result = new HashMap<>();
    try {
      String email = request.getParameter("email");
      if (email.length() < 1) {
        result.put("status", "422");
        result.put("description", "Email too short");
      } else if (email.length() > 50) {
        result.put("status", "422");
        result.put("description", "Email too long");
      } else if (!email.matches("[0-9a-zA-Z.]+@studenti.unisa.it")) {
        result.put("status", "422");
        result.put("description", "Email not valid");
      } else if (new UtenteDao().doCheckRegister(email)) {
        result.put("status", "422");
        result.put("description", "Email already registered");
      }
      if (!result.isEmpty()) {
        out.println(obj.toJson(result));
        return;
      }

      String password = request.getParameter("password");

      if (password.length() < 8) {
        result.put("status", "422");
        result.put("description", "Password too short");
      } else if (!password.matches("[0-9a-zA-Z]{8,}")) {
        result.put("status", "422");
        result.put("description", "Password invalid");
      }
      if (!result.isEmpty()) {
        out.println(obj.toJson(result));
        return;
      }

      String nome = request.getParameter("nome");

      if (nome.length() == 0) {
        result.put("status", "422");
        result.put("description", "Nome too short");
      } else if (nome.length() > 30) {
        result.put("status", "422");
        result.put("description", "Nome too long");
      } else if (!nome.matches("[0-9a-zA-Z]+")) {
        result.put("status", "422");
        result.put("description", "Nome invalid");

      }
      if (!result.isEmpty()) {
        out.println(obj.toJson(result));
        return;
      }

      String cognome = request.getParameter("cognome");
      if (cognome.length() == 0) {
        result.put("status", "422");
        result.put("description", "Cognome too short");
      } else if (cognome.length() > 30) {
        result.put("status", "422");
        result.put("description", "Cognome too long");
      } else if (!cognome.matches("[0-9a-zA-Z]+")) {
        result.put("status", "422");
        result.put("description", "Cognome invalid");
      }

      String codiceFiscale = request.getParameter("codiceFiscale");
      if (codiceFiscale != null) {
        if (codiceFiscale.length() != 16) {
          result.put("status", "422");
          result.put("description", "codiceFiscale too short");
        } else if (!codiceFiscale
            .matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) {
          result.put("status", "422");
          result.put("description", "codiceFiscale invalid");
        }
        if (!result.isEmpty()) {
          out.println(obj.toJson(result));
          return;
        }

        String matricola = request.getParameter("matricola");
        if (matricola.length() != 10) {
          result.put("status", "422");
          result.put("description", "Matricola too short");
        } else if (!matricola.matches("[0-9]{10}")) {
          result.put("status", "422");
          result.put("description", "Matricola invalid");
        }
        if (!result.isEmpty()) {
          out.println(obj.toJson(result));
          return;
        }

        String dataDiNascita = request.getParameter("dataDiNascita");
        if (dataDiNascita.length() != 9) {
          result.put("status", "422");
          result.put("description", "dataDiNascita too short");
        } else if (!dataDiNascita
            .matches(
                "^([0-20-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)[0-9]{4}$")) {
          result.put("status", "422");
          result.put("description", "dataDiNascita invalid");
        }
        if (!result.isEmpty()) {
          out.println(obj.toJson(result));
          return;
        }

        String cittadinanza = request.getParameter("cittadinanza");
        if (cittadinanza.length() == 0) {
          result.put("status", "422");
          result.put("description", "cittadinanza too short");
        } else if (!cittadinanza.matches("[a-zA-Z]+")) {
          result.put("status", "422");
          result.put("description", "cittadinanza invalid");
        }

        String residenza = request.getParameter("residenza");
        if (residenza.length() == 0) {
          result.put("status", "422");
          result.put("description", "residenza too short");
        } else if (!residenza.matches("[A-z0-9,]+")) {
          result.put("status", "422");
          result.put("description", "residenza invalid");
        }
        if (!result.isEmpty()) {
          out.println(obj.toJson(result));
          return;
        }

        String numero = request.getParameter("numero");
        if (!numero.matches("[0-9]{9,12}")) {
          result.put("status", "422");
          result.put("description", "numero invalid");
        }
        if (!result.isEmpty()) {
          out.println(obj.toJson(result));
          return;
        }

        Studente utente = new Studente(email, nome, password, codiceFiscale, matricola,
            Date.valueOf(dataDiNascita), cittadinanza, residenza, numero, cognome);
        if ((new StudenteDao()).doSave(utente)) {
          result.put("status", "200");
          result.put("description", "ok");
        } else {
          result.put("status", "400");
          result.put("description", "unknown error");
        }
        if (!result.isEmpty()) {
          out.println(obj.toJson(result));
          return;
        }
      }
    } catch (Exception ex) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }
  }
}
