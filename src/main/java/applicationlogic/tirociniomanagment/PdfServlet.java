package applicationlogic.tirociniomanagment;

import java.io.IOException;
import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import storage.beans.Utente;

@WebServlet("/PDFServlet")
public class PdfServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      Utente user = (Utente) request.getSession().getAttribute("utente");
      if (user == null)
        throw new AuthenticationException("Not Authorized");
      String action = request.getParameter("action");
      if (action != null && action.equals("createPDF")) {
        createPDF(request, response);
      }
    } catch (AuthenticationException e) {
      //TODO
    }

  }

  private void createPDF(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    String numeroCfu = request.getParameter("cfu");
    if (!numeroCfu.matches("[0-9]{1,2}")) {
      throw new IllegalArgumentException("Numero cfu non valido.");
    }

    String sedeSvolgimento = request.getParameter("sede");
    if (sedeSvolgimento.length() == 0) {
      throw new IllegalArgumentException("Inserire una sede di svolgimento.");
    } else if (!sedeSvolgimento.matches("[A-z0-9 ,]")) {
      throw new IllegalArgumentException("Sede svolgimento non valida.");
    }

    String obiettivi = request.getParameter("obiettivi");
    if (obiettivi.length() == 0) {
      throw new IllegalArgumentException("Inserire gli obiettivi.");
    } else if (!obiettivi.matches("[A-z0-9 ,.;]")) {
      throw new IllegalArgumentException("Formato degli obiettivi non valido.");
    }

    String competenze = request.getParameter("competenze");
    if (competenze.length() == 0) {
      throw new IllegalArgumentException("Inserire le competenze.");
    } else if (!competenze.matches("[A-z0-9 ,.;]")) {
      throw new IllegalArgumentException("Formato delle competenze non valido.");
    }

    String attivita = request.getParameter("attivita");
    if (attivita.length() == 0) {
      throw new IllegalArgumentException("Inserire le attivit&agrave;.");
    } else if (!attivita.matches("[A-z0-9 ,.;]")) {
      throw new IllegalArgumentException("Formato delle attivit&agrave; non valido.");
    }

    String modalita = request.getParameter("modalita");
    if (modalita.length() == 0) {
      throw new IllegalArgumentException("Inserire le modalit&agrave; .");
    } else if (!modalita.matches("[A-z0-9 ,.;]")) {
      throw new IllegalArgumentException("Formato delle modalit&agrave; non valido.");
    }
  }
}
