package applicationlogic.tirociniomanagment;

import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;
import storage.beans.Utente;
import storage.dao.AziendaDao;
import storage.dao.RichiestaDisponibilitaDao;
import storage.dao.StudenteDao;
import storage.dao.TirocinioDao;
import storage.dao.UtenteDao;
import storage.interfaces.AziendaInterface;
import storage.interfaces.RichiestaDisponibilitaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.TirocinioInterface;
import storage.interfaces.UtenteInterface;

//todo : da cambiare il nome sulla documetazione per createPDF in createPdf
@WebServlet("/PdfServlet")
public class PdfServlet extends HttpServlet {

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
        if (action.equals("createPdf")) {

          if (createPdf(request, response)) {
            result.put("status", "200");
            result.put("description", "pdf creato con successo.");
          } else {
            result.put("status", "400");
            result.put("description", "Errore generico.");
          }

          out.println(obj.toJson(result));

        }
      } catch (IllegalArgumentException e) {
        result.put("status", "422");
        result.put("description", e.getMessage());

        out.println(obj.toJson(result));
      }
    }

  }

  private boolean createPdf(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String numeroCfu = request.getParameter("cfu");
    if (numeroCfu == null || numeroCfu.length() == 0) {
      throw new IllegalArgumentException("Inserire i cfu.");
    }
    if (!numeroCfu.matches("[0-9]{1,2}")) {
      throw new IllegalArgumentException("Numero cfu non valido.");
    }

    String sedeSvolgimento = request.getParameter("sede");
    if (sedeSvolgimento == null || sedeSvolgimento.length() == 0) {
      throw new IllegalArgumentException("Inserire una sede di svolgimento.");
    } else if (!sedeSvolgimento.matches("[A-z0-9 ,]+")) {
      throw new IllegalArgumentException("Sede svolgimento non valida.");
    }

    String obiettivi = request.getParameter("obiettivi");
    if (obiettivi == null || obiettivi.length() == 0) {
      throw new IllegalArgumentException("Inserire gli obiettivi.");
    } else if (!obiettivi.matches("[A-z0-9 ,.;]+")) {
      throw new IllegalArgumentException("Formato degli obiettivi non valido.");
    }

    String competenze = request.getParameter("competenze");
    if (competenze == null || competenze.length() == 0) {
      throw new IllegalArgumentException("Inserire le competenze.");
    } else if (!competenze.matches("[A-z0-9 ,.;]+")) {
      throw new IllegalArgumentException("Formato delle competenze non valido.");
    }

    String attivita = request.getParameter("attivita");
    if (attivita == null || attivita.length() == 0) {
      throw new IllegalArgumentException("Inserire le attivit&agrave;.");
    } else if (!attivita.matches("[A-z0-9 ,.;]+")) {
      throw new IllegalArgumentException("Formato delle attivit&agrave; non valido.");
    }

    String modalita = request.getParameter("modalita");
    if (modalita == null || modalita.length() == 0) {
      throw new IllegalArgumentException("Inserire le modalit&agrave; .");
    } else if (!modalita.matches("[A-z0-9 ,.;]+")) {
      throw new IllegalArgumentException("Formato delle modalit&agrave; non valido.");
    }

    String dataInizio = request.getParameter("dataInizio");
    if (dataInizio == null || dataInizio.length() == 0) {
      throw new IllegalArgumentException("Inserire la data di inizio.");
    } else if (!dataInizio.matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) {
      throw new IllegalArgumentException("Formato delle data di inizio non valida.");
    }

    String dataFine = request.getParameter("dataFine");
    if (dataFine == null || dataFine.length() == 0) {
      throw new IllegalArgumentException("Inserire la data di fine.");
    } else if (!dataFine.matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) {
      throw new IllegalArgumentException("Formato delle data di fine non valida.");
    }

    String orario = request.getParameter("orario");
    if (orario == null || orario.length() == 0) {
      throw new IllegalArgumentException("Inserire l'orario di lavoro.");
    } else if (!orario.matches("[A-z0-9 ,.;]+")) {
      throw new IllegalArgumentException("Formato dell'orario non valido.");
    }

    String numeroRc = request.getParameter("numeroRc");
    if (numeroRc == null || numeroRc.length() == 0) {
      throw new IllegalArgumentException("Inserire il numero RC.");
    } else if (!numeroRc.matches("[0-9]+")) {
      throw new IllegalArgumentException("Formato del numero RC non valido.");
    }

    String polizza = request.getParameter("polizza");
    if (polizza == null || polizza.length() == 0) {
      throw new IllegalArgumentException("Inserire la polizza assicurativa infortuni.");
    } else if (!obiettivi.matches("[A-z0-9 ,.;]+")) {
      throw new IllegalArgumentException(
          "Formato della aolizza assicurativa infortuni non valido.");
    }

    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML");
    templateResolver.setCharacterEncoding("UTF-8");

    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);

    String emailStudente = request.getParameter("studente");
    if (emailStudente == null || emailStudente.length() == 0) {
      throw new IllegalArgumentException("Email dello studente non puo' essere vuota");
    }

    try {
      StudenteInterface studenteDao = new StudenteDao();
      AziendaInterface aziendaDao = new AziendaDao();
      UtenteInterface utenteDao = new UtenteDao();

      if (!utenteDao.doCheckRegister(emailStudente)) {
        throw new IllegalArgumentException("Email studente errata");
      }

      Utente user = (Utente) request.getSession().getAttribute("utente");
      Studente studente = studenteDao.doRetrieveByKey(emailStudente);
      Azienda azienda = aziendaDao.doRetrieveByKey(user.getEmail());

      Context context = new Context();
      context.setVariable("nomeAzienda", azienda.getNome());
      context.setVariable("indirizzoSede", azienda.getIndirizzo());
      context.setVariable("email", azienda.getEmail());
      context.setVariable("partitaIva", azienda.getPartitaIva());
      context.setVariable("rappresentanteAzienda", azienda.getRappresentante());
      context.setVariable("codiceAteco", azienda.getCodAteco());

      context.setVariable("numeroDipendenti", azienda.getNumeroDipendenti());
      context.setVariable("cognomeStudente", studente.getCognome());
      context.setVariable("nomeStudente", studente.getNome());
      context.setVariable("dataDiNascita", studente.getDataDiNascita());
      context.setVariable("cittadinanza", studente.getCittadinanza());
      context.setVariable("residenza", studente.getResidenza());
      context.setVariable("codiceFiscale", studente.getCodiceFiscale());
      context.setVariable("numeroTelefono", studente.getNumero());
      context.setVariable("emailStudente", studente.getEmail());

      context.setVariable("cfu", numeroCfu);
      context.setVariable("sedeSvolgimento", sedeSvolgimento);
      context.setVariable("obiettivi", obiettivi);
      context.setVariable("competenze", competenze);
      context.setVariable("attivita", attivita);
      context.setVariable("modalita", attivita);
      context.setVariable("dataInizio", dataInizio);
      context.setVariable("dataFine", dataFine);
      context.setVariable("orario", orario);
      context.setVariable("rc", numeroRc);
      context.setVariable("pInfortuni", polizza);

      // Get the plain HTML with the resolved ${name} variable!
      String html = templateEngine.process("template", context);
      String xhtml = convertToXhtml(html);

      OutputStream outputStream = new FileOutputStream("test.pdf");
      ITextRenderer renderer = new ITextRenderer();
      renderer.setDocumentFromString(xhtml);
      renderer.layout();
      renderer.createPDF(outputStream);
      outputStream.close();

      return true;

    } catch (DocumentException | SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return false;

  }

  private String convertToXhtml(String html) throws UnsupportedEncodingException {
    Tidy tidy = new Tidy();
    tidy.setInputEncoding(UTF_8);
    tidy.setOutputEncoding(UTF_8);
    tidy.setXHTML(true);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(UTF_8));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    tidy.parseDOM(inputStream, outputStream);
    return outputStream.toString(UTF_8);
  }

  private static final String UTF_8 = "UTF-8";
}
