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
import storage.PasswordManager;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.StudenteDao;
import storage.dao.UtenteDao;
import storage.interfaces.StudenteInterface;
import storage.interfaces.UtenteInterface;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession ses = request.getSession();
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
        throw new IllegalArgumentException("Email troppo corta.");
      } else if (email.length() > 50) {
        throw new IllegalArgumentException("Email troppo lunga.");
      } else if (!email.matches("[0-9a-zA-Z.]+@studenti.unisa.it")) {
        throw new IllegalArgumentException("Email non valida.");
      } else if (utenteDao.doCheckRegister(email)) {
        throw new IllegalArgumentException("Email gia' registrata.");
      }

      String password = request.getParameter("password");
      if (password.length() < 8) {
        throw new IllegalArgumentException("Password troppo corta.");
      } else if (!password.matches("[0-9a-zA-Z]{8,}")) {
        throw new IllegalArgumentException("Password non valida.");
      }

      password = PasswordManager.createHash(password);

      String nome = request.getParameter("nome");
      if (nome.length() == 0) {
        throw new IllegalArgumentException("Nome troppo corto.");
      } else if (nome.length() > 30) {
        throw new IllegalArgumentException("Nome troppo lungo.");
      } else if (!nome.matches("[a-zA-Z ]+")) {
        throw new IllegalArgumentException("Nome non valido.");

      }

      String cognome = request.getParameter("cognome");
      if (cognome.length() == 0) {
        throw new IllegalArgumentException("Cognome troppo corto.");
      } else if (cognome.length() > 30) {
        throw new IllegalArgumentException("Cognome troppo lungo.");
      } else if (!cognome.matches("[a-zA-Z ]+")) {
        throw new IllegalArgumentException("Cognome non valido.");
      }

      String codiceFiscale = request.getParameter("codiceFiscale");
      if (codiceFiscale.compareTo("") != 0) {
        if (codiceFiscale.length() != 16) {
          throw new IllegalArgumentException("Lunghezza del Codice Fiscale non valida.");
        } else if (!codiceFiscale
            .matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) {
          throw new IllegalArgumentException("Codice Fiscale non valido.");
        }
      }

      String matricola = request.getParameter("matricola");
      if (matricola.length() != 10) {
        throw new IllegalArgumentException("Lunghezza della matricola non valida.");
      } else if (!matricola.matches("[0-9]{10}")) {
        throw new IllegalArgumentException("Matricola invalida.");
      }

      String dataDiNascita = request.getParameter("dataDiNascita");
      if (dataDiNascita.length() != 10) {
        throw new IllegalArgumentException("Lunghezza della data di nascita non valida.");
      } else if (!dataDiNascita.matches(
          "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) {
        throw new IllegalArgumentException("Data di nascita invalida.");
      }

      String cittadinanza = request.getParameter("cittadinanza");
      if (cittadinanza.length() == 0) {
        throw new IllegalArgumentException("Cittadinanza troppo corta.");
      } else if (!cittadinanza.matches("[a-zA-Z]+")) {
        throw new IllegalArgumentException("Cittadinanza invalida.");
      }

      String residenza = request.getParameter("residenza");
      if (residenza.length() == 0) {
        throw new IllegalArgumentException("Residenza troppo corta.");
      } else if (!residenza.matches("[A-z0-9, ']+")) {
        throw new IllegalArgumentException("Residenza invalida.");
      }

      String numero = request.getParameter("numero");
      if (!numero.matches("[0-9]{9,12}")) {
        throw new IllegalArgumentException("Numero invalido.");
      }

      Studente studente = new Studente(email, nome, password, codiceFiscale,
          matricola, Date.valueOf(dataDiNascita), cittadinanza, residenza,
          numero, cognome);
      if (!studenteDao.doSave(studente)) {
        throw new RuntimeException("Errore sconosciuto.");
      }
      return studente;
    } catch (SQLException ex) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return null;
    }
  }

  private static StudenteInterface studenteDao = new StudenteDao();
  private static UtenteInterface utenteDao = new UtenteDao();
}
