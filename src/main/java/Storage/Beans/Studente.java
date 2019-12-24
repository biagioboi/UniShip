package Storage.Beans;

import java.sql.Date;

public class Studente extends Utente {

  public Studente() {
  }

  /**
   * @param email l'email dello studente
   * @param nome il nome dello studente
   * @param password la password di accesso dello studente
   * @param codiceFiscale il codice fiscale dello studente
   * @param matricola la matricola dello studente
   * @param dataDiNascita la data di nascita dello studente
   * @param cittadinanza la cittadinanza dello studente
   * @param residenza la residenza dello studente
   * @param numero il numero telefonico dello studente
   * @param cognome il cognome dello studente
   */
  public Studente(String email, String nome, String password, String codiceFiscale,
      String matricola, Date dataDiNascita, String cittadinanza, String residenza,
      String numero, String cognome) {
    super(email, nome, password, STUDENTE);
    this.codiceFiscale = codiceFiscale;
    this.matricola = matricola;
    this.dataDiNascita = dataDiNascita;
    this.cittadinanza = cittadinanza;
    this.residenza = residenza;
    this.numero = numero;
    this.cognome = cognome;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getMatricola() {
    return matricola;
  }

  public void setMatricola(String matricola) {
    this.matricola = matricola;
  }

  public Date getDataDiNascita() {
    return dataDiNascita;
  }

  public void setDataDiNascita(Date dataDiNascita) {
    this.dataDiNascita = dataDiNascita;
  }

  public String getCittadinanza() {
    return cittadinanza;
  }

  public void setCittadinanza(String cittadinanza) {
    this.cittadinanza = cittadinanza;
  }

  public String getResidenza() {
    return residenza;
  }

  public void setResidenza(String residenza) {
    this.residenza = residenza;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  private String codiceFiscale;
  private String matricola;
  private Date dataDiNascita;
  private String cittadinanza;
  private String residenza;
  private String numero;
  private String cognome;
}
