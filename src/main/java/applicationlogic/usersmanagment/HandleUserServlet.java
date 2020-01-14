package applicationlogic.usersmanagment;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.util.Pair;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import storage.PasswordHash;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;
import storage.beans.Utente;
import storage.dao.AziendaDao;
import storage.dao.RichiestaDisponibilitaDao;
import storage.dao.StudenteDao;
import storage.interfaces.AziendaInterface;
import storage.interfaces.RichiestaDisponibilitaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.TirocinioInterface;
import storage.interfaces.UtenteInterface;

//TODO: Aggiungere retrieve free companies all'ODD
// ho aggiunto il metodo retrieve free companies
@WebServlet("/HandleUserServlet")
public class HandleUserServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    Gson obj = new GsonBuilder().serializeNulls().create();
    Map<String, String> result = new HashMap<>();
    try {

      Utente user = (Utente) request.getSession().getAttribute("utente");
      if (user == null) {
        throw new AuthenticationException("Non autorizzato.");
      }

      String action = request.getParameter("action");
      if (action != null && action.equals("retrieveCompanies")) {
        ArrayList<Pair> results = retrieveFreeCompanies(request, response);
        out.println(obj.toJson(results));
        return;
      }
      if (action.equals("addCompany")) {
        if (!user.getTipo().equals(Utente.UFFICIO_CARRIERE) && !user.getTipo()
            .equals(Utente.ADMIN)) {
          throw new AuthenticationException("Non autorizzato.");
        } else if (addCompany(request, response)) {
          result.put("status", "200");
          // result.put("redirect", "index.jsp");
        } else {
          throw new RuntimeException();
        }
      } else if (action.equals("changeCompanyData")) {
        if (!user.getTipo().equals(Utente.UFFICIO_CARRIERE) && !user.getTipo()
            .equals(Utente.ADMIN) && !user.getEmail().equals(request.getParameter("azienda"))) {
          throw new AuthenticationException("Non autorizzato.");
        } else {
          throw new RuntimeException();
        }
      } else {
        result.put("status", "400");
        result.put("description", "Richiesta invalida.");
      }

    } catch (AuthenticationException e) {
      result.put("status", "403");
      result.put("description", e.getMessage());
    } catch (IllegalArgumentException e) {
      result.put("status", "422");
      result.put("description", e.getMessage());
    } catch (Exception ex) {
      result.put("status", "422");
      result.put("description", "Errore generico.");
    }

    out.println(obj.toJson(result));
  }


  private boolean addCompany(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String email = request.getParameter("email");
    if (email.length() == 0) {
      throw new IllegalArgumentException("Email troppo corta");
    } else if (email.length() > 50) {
      throw new IllegalArgumentException("Email troppo lunga");
    } else if (!email.matches("[0-9a-zA-Z.]+@[a-z.]+.[a-z]+")) {
      throw new IllegalArgumentException("Email non valida");
    }


    if (aziendaDao.doRetrieveByKey(email) != null) {
      throw new IllegalArgumentException("Email gia' registrata");
    }

    String nome = request.getParameter("nome");
    if (nome.length() == 0) {
      throw new IllegalArgumentException("Nome troppo corto.");
    } else if (nome.length() > 30) {
      throw new IllegalArgumentException("Nome troppo lungo.");
    } else if (!nome.matches("[a-zA-Z .&'-]+")) {
      throw new IllegalArgumentException("Nome invalido.");
    }

    String piva = request.getParameter("piva");
    if (!piva.matches("[0-9]{11}")) {
      throw new IllegalArgumentException("Partita IVA invalida.");
    }

    ArrayList<Azienda> aziende = aziendaDao.doRetrieveAll();
    for (Azienda az : aziende) {
      if (az.getPartitaIva().equals(piva)) {
        throw new IllegalArgumentException("Partita IVA gia' presente.");
      }
    }

    String indirizzo = request.getParameter("indirizzo");
    if (indirizzo.length() == 0) {
      throw new IllegalArgumentException("Indirizzo troppo piccolo.");
    } else if (!indirizzo.matches("[A-z 0-9,]+")) {
      throw new IllegalArgumentException("Indirizzo non valido.");
    }

    String rappresentante = request.getParameter("rappresentante");
    if (rappresentante.length() == 0) {
      throw new IllegalArgumentException("Rappresentante troppo piccolo.");
    } else if (!rappresentante.matches("[A-z ]+")) {
      throw new IllegalArgumentException("Rappresentante non valido.");
    }

    String codAteco = request.getParameter("codAteco");
    if (codAteco.length() == 0) {
      throw new IllegalArgumentException("Codice ateco troppo corto.");
    } else if (!codAteco.matches("[A-Z0-9.]+")) {
      throw new IllegalArgumentException("Codice ateco non valido.");
    }

    String numeroDipendenti = request.getParameter("numeroDipendenti");
    if (!numeroDipendenti.matches("[0-9]+")) {
      throw new IllegalArgumentException("Numero dipendenti non valido.");
    }

    //Invio email con le credenziali

    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "587");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true"); //TLS

    Session session = Session.getInstance(prop,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(USERNAME_EMAIL, PASSWORD_EMAIL);
          }
        });

    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(USERNAME_EMAIL));
    message.setRecipients(
        Message.RecipientType.TO,
        InternetAddress.parse(email)
    );
    String newPassword = PasswordHash.generatePassword();
    message.setSubject("Registrazione UniShip");
    message.setText("La registrazione alla piattaforma UniShip e' andata a buon fine."
        + "\n\nLe vostre credenziali sono : \nEmail: l'email fornita all'ufficio carriere\n"
        + "Password : " + newPassword
        + "\n\nQUESTA EMAIL E' STATA AUTOGENERATA, NON RISPONDERE A QUESTA EMAIL.");

    Transport.send(message);

    String password = PasswordHash.createHash(newPassword);
    Azienda azienda = new Azienda(email, nome, password, piva, indirizzo, rappresentante, codAteco,
        Integer.parseInt(numeroDipendenti));

    return aziendaDao.doSave(azienda);
  }

  private ArrayList<Pair> retrieveFreeCompanies(HttpServletRequest request,
      HttpServletResponse response) {
    ArrayList<Pair> result = new ArrayList<>();
    Utente utente = (Utente) request.getSession().getAttribute("utente");

    try {
      Studente studente = studenteDao.doRetrieveByKey(utente.getEmail());
      ArrayList<RichiestaDisponibilita> richieste = richiestaDao.doRetrieveByStudente(studente);
      ArrayList<Azienda> aziendeDispo = aziendaDao.doRetrieveAll();
      for (Azienda a : aziendeDispo) {
        RichiestaDisponibilita ri = null;
        for (RichiestaDisponibilita r : richieste) {
          if (r.getAzienda().equals(a)) {
            ri = r;
          }
        }
        result.add(new Pair(a, ri));
      }
    } catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return null;
    }
    return result;

  }

  public static final String USERNAME_EMAIL = "uniship.info@gmail.com";
  public static final String PASSWORD_EMAIL = "uniship2020";

  protected static AziendaInterface aziendaDao = new AziendaDao();
  protected static RichiestaDisponibilitaInterface richiestaDao = new RichiestaDisponibilitaDao();
  protected static StudenteInterface studenteDao = new StudenteDao();
}