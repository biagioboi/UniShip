package applicationlogic.usersmanagment;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
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
    Utente user = (Utente) request.getSession().getAttribute("utente");
    Gson obj = new Gson();
    Map<String, String> result = new HashMap<>();

    if (user == null) {
      String action = request.getParameter("action");
      if (action != null && action.equals("signup")) {
        try {
          Studente studente = registrazione(request, response);
          ses.setAttribute("utente", studente);
          ses.setAttribute("login", "si");
          ses.setAttribute("tipo", studente.getTipo());
          result.put("status", "302");
          result.put("redirect", "index.jsp");
        } catch (IllegalArgumentException e) {
          result.put("status", "422");
          result.put("description", e.getMessage());
        } catch (RuntimeException e) {
          result.put("status", "400");
          result.put("description", e.getMessage());
        }
      }
    } else {
      result.put("status", "302");
      result.put("redirect", "index.jsp");
    }

    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    out.println(obj.toJson(result));
  }

  private Studente registrazione(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException, IllegalArgumentException {

    try {

      String email = request.getParameter("email");
      if (email.length() < 1) {
        throw new IllegalArgumentException("Email too short");
      } else if (email.length() > 50) {
        throw new IllegalArgumentException("Email too long");
      } else if (!email.matches("[0-9a-zA-Z.]+@studenti.unisa.it")) {
        throw new IllegalArgumentException("Email not valid");
      } else if (new UtenteDao().doCheckRegister(email)) {
        throw new IllegalArgumentException("Email already register.");
      }

      String password = request.getParameter("password");
      if (password.length() < 8) {
        throw new IllegalArgumentException("Password too short.");
      } else if (!password.matches("[0-9a-zA-Z]{8,}")) {
        throw new IllegalArgumentException("Password invalid.");
      }

      String nome = request.getParameter("nome");
      if (nome.length() == 0) {
        throw new IllegalArgumentException("Nome too short.");
      } else if (nome.length() > 30) {
        throw new IllegalArgumentException("Nome too long.");
      } else if (!nome.matches("[a-zA-Z ]+")) {
        throw new IllegalArgumentException("Nome invalid.");

      }

      String cognome = request.getParameter("cognome");
      if (cognome.length() == 0) {
        throw new IllegalArgumentException("Cognome too short.");
      } else if (cognome.length() > 30) {
        throw new IllegalArgumentException("Cognome too long.");
      } else if (!cognome.matches("[a-zA-Z ]+")) {
        throw new IllegalArgumentException("Cognome invalid.");
      }

      String codiceFiscale = request.getParameter("codiceFiscale");
      if (codiceFiscale.compareTo("") != 0) {
        if (codiceFiscale.length() != 16) {
          throw new IllegalArgumentException("Codice Fiscale too short.");
        } else if (!codiceFiscale
            .matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) {
          throw new IllegalArgumentException("Codice Fiscale invalid.");
        }
      }

      String matricola = request.getParameter("matricola");
      if (matricola.length() != 10) {
        throw new IllegalArgumentException("Matricola too short.");
      } else if (!matricola.matches("[0-9]{10}")) {
        throw new IllegalArgumentException("Matricola invalid.");
      }

      String dataDiNascita = request.getParameter("dataDiNascita");
      if (dataDiNascita.length() != 10) {
        throw new IllegalArgumentException("Data di nascita too short.");
      } else if (!dataDiNascita.matches(
          "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) {
        throw new IllegalArgumentException("Data di nascita invalid.");
      }

      String cittadinanza = request.getParameter("cittadinanza");
      if (cittadinanza.length() == 0) {
        throw new IllegalArgumentException("Cittadinanza too short.");
      } else if (!cittadinanza.matches("[a-zA-Z]+")) {
        throw new IllegalArgumentException("Cittadinanza invalid.");
      }

      String residenza = request.getParameter("residenza");
      if (residenza.length() == 0) {
        throw new IllegalArgumentException("Residenza too short.");
      } else if (!residenza.matches("[A-z0-9, ']+")) {
        throw new IllegalArgumentException("Residenza invalid.");
      }

      String numero = request.getParameter("numero");
      if (!numero.matches("[0-9]{9,12}")) {
        throw new IllegalArgumentException("Numero invalid.");
      }

      Studente utente = new Studente(email, nome, password, codiceFiscale,
          matricola,Date.valueOf(dataDiNascita), cittadinanza, residenza,
          numero, cognome);
      if (!(new StudenteDao()).doSave(utente)) {
        throw new RuntimeException("Errore sconosciuto.");
      }
      return utente;
    } catch (SQLException ex) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return null;
    }
  }
}