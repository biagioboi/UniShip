package applicationlogic.tirociniomanagment;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.FileSystems;
import storage.beans.Azienda;
import storage.beans.Studente;

import static com.itextpdf.text.pdf.BaseFont.EMBEDDED;
import static com.itextpdf.text.pdf.BaseFont.IDENTITY_H;
import static org.thymeleaf.templatemode.TemplateMode.HTML;

/**
 * This is a JUnit test which will generate a PDF using Flying Saucer
 * and Thymeleaf templates. The PDF will display a letter styled with
 * CSS. The letter has two pages and will contain text and images.
 * <p>
 * Simply run this test to generate the PDF. The file is called:
 * <p>
 * /test.pdf
 */
public class PdfServletTest {

  @Test
  public void generatePdf() throws Exception {

    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML");

    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);

    Azienda azienda = new Azienda();
    Studente studente = new Studente();

    Context context = new Context();
    context.setVariable("nomeAzienda",azienda.getNome());
    context.setVariable("indirizzoSede",azienda.getIndirizzo());
    context.setVariable("email",azienda.getEmail());
    context.setVariable("partivaIva",azienda.getPartitaIva());
    context.setVariable("rappresentanteAzienda",azienda.getRappresentante());
    context.setVariable("codiceAteco",azienda.getCodAteco());

    context.setVariable("numeroDipendenti",azienda.getNumeroDipendenti());
    context.setVariable("cognomeStudente",studente.getCognome());
    context.setVariable("nomeStudente",studente.getNome());
    context.setVariable("dataDiNascita",studente.getDataDiNascita());
    context.setVariable("cittadinaza",studente.getCittadinanza());
    context.setVariable("residenza",studente.getResidenza());
    context.setVariable("codiceFiscale",studente.getCodiceFiscale());
    context.setVariable("numeroTelefono",studente.getNumero());
    context.setVariable("emailStudente",studente.getEmail());




// Get the plain HTML with the resolved ${name} variable!
    String html = templateEngine.process("fs-progetto-formativo-curriculare", context);
    String xHtml = convertToXhtml(html);

    OutputStream outputStream = new FileOutputStream("message.pdf");
    ITextRenderer renderer = new ITextRenderer();
    renderer.setDocumentFromString(xHtml);
    renderer.layout();
    renderer.createPDF(outputStream);
    outputStream.close();
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