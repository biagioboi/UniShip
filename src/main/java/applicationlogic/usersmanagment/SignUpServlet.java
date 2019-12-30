package applicationlogic.usersmanagment;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
        return;
      }
    } else {
      response.setContentType("application/json");
      JsonObject obj = new JsonObject();
      obj.put("status", "409");
      obj.put("description", "User already logged in");
      PrintWriter out = response.getWriter();
      out.println(obj.toJson());
    }
  }

  private void registrazione(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();

    try {
      String email = request.getParameter("email");
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
      } else if (new UtenteDao().doCheckRegister(email)) {
        JsonObject obj = new JsonObject();
        obj.put("status", "422");
        obj.put("description", "Email already registered");
        out.println(obj.toJson());
      }

      String password = request.getParameter("password");

      if (password.length() < 8) {
        JsonObject obj = new JsonObject();
        obj.put("status", "422");
        obj.put("description", "Password too short");
        out.println(obj.toJson());
        return;
      } else if (!password.matches("[0-9a-zA-Z]{8,}")) {
        JsonObject obj = new JsonObject();
        obj.put("status", "422");
        obj.put("description", "Password invalid");
        out.println(obj.toJson());
      }

      String nome = request.getParameter("nome");

      if (nome.length() == 0) {
        JsonObject obj = new JsonObject();
        obj.put("status", "422");
        obj.put("description", "Nome too short");
        out.println(obj.toJson());
        return;
      } else if (nome.length() > 30) {
        JsonObject obj = new JsonObject();
        obj.put("status", "422");
        obj.put("description", "Nome too long");
        out.println(obj.toJson());
        return;
      } else if (!nome.matches("[0-9a-zA-Z]+")) {
        JsonObject obj = new JsonObject();
        obj.put("status", "422");
        obj.put("description", "Nome invalid");
        out.println(obj.toJson());
      }

      String cognome = request.getParameter("cognome");
      if (cognome.length() == 0) {
        JsonObject obj = new JsonObject();
        obj.put("status", "422");
        obj.put("description", "Cognome too short");
        out.println(obj.toJson());
        return;
      } else if (cognome.length() > 30) {
        JsonObject obj = new JsonObject();
        obj.put("status", "422");
        obj.put("description", "Cognome too long");
        out.println(obj.toJson());
        return;
      } else if (!cognome.matches("[0-9a-zA-Z]+")) {
        JsonObject obj = new JsonObject();
        obj.put("status", "422");
        obj.put("description", "Cognome invalid");
        out.println(obj.toJson());
      }

      String codiceFiscale = request.getParameter("codiceFiscale");
      if (codiceFiscale != null) {
        if (codiceFiscale.length() != 11) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "codiceFiscale too short");
          out.println(obj.toJson());
          return;
        } else if (!codiceFiscale
            .matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "codiceFiscale invalid");
          out.println(obj.toJson());
        }

        String matricola = request.getParameter("matricola");
        if (matricola.length() != 10) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "Matricola too short");
          out.println(obj.toJson());
          return;
        } else if (!matricola.matches("[0-9]{10}")) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "Matricola invalid");
          out.println(obj.toJson());
        }

        String dataDiNascita = request.getParameter("dataDiNascita");
        if (dataDiNascita.length() != 9) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "dataDiNascita too short");
          out.println(obj.toJson());
          return;
        } else if (!dataDiNascita
            .matches(
                "^([0-20-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)[0-9]{4}$")) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "dataDiNascita invalid");
          out.println(obj.toJson());
        }

        String cittadinanza = request.getParameter("cittadinanza");
        if (cittadinanza.length() == 0) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "cittadinanza too short");
          out.println(obj.toJson());
          return;
        } else if (!cittadinanza.matches("[a-zA-Z]+")) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "cittadinanza invalid");
          out.println(obj.toJson());
        }

        String residenza = request.getParameter("residenza");
        if (residenza.length() == 0) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "residenza too short");
          out.println(obj.toJson());
          return;
        } else if (!residenza.matches("[A-z0-9,]+")) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "residenza invalid");
          out.println(obj.toJson());
        }

        String numero = request.getParameter("numero");
        if (!numero.matches("[0-9]{9,12}")) {
          JsonObject obj = new JsonObject();
          obj.put("status", "422");
          obj.put("description", "numero invalid");
          out.println(obj.toJson());
        }

        Studente utente = new Studente(email, nome, password, codiceFiscale, matricola,
            Date.valueOf(dataDiNascita), cittadinanza, residenza, numero, cognome);
        if ((new StudenteDao()).doSave(utente)) {
          JsonObject obj = new JsonObject();
          obj.put("status", "200");
          obj.put("description", "ok");
          out.println(obj.toJson());
        } else {
          JsonObject obj = new JsonObject();
          obj.put("status", "400");
          obj.put("description", "unknown error");
          out.println(obj.toJson());
        }

      }
    } catch (Exception ex) {
      JsonObject obj = new JsonObject();
      obj.put("status", "500");
      obj.put("description", "Internal Error");
      out.println(obj.toJson());
    }
  }
}
