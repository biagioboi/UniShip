package Storage.Beans;

public class Azienda extends Utente {

  public Azienda() {
  }

  public Azienda(String email, String nome, String password, String tipo, String partitaIva,
      String indirizzo, String rappresentante, String codAteco, int numeroDipendenti) {
    super(email, nome, password, tipo);
    this.partitaIva = partitaIva;
    this.indirizzo = indirizzo;
    this.rappresentante = rappresentante;
    this.codAteco = codAteco;
    this.numeroDipendenti = numeroDipendenti;
  }

  public String getPartitaIva() {
    return partitaIva;
  }

  public void setPartitaIva(String partitaIva) {
    this.partitaIva = partitaIva;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public String getRappresentante() {
    return rappresentante;
  }

  public void setRappresentante(String rappresentante) {
    this.rappresentante = rappresentante;
  }

  public String getCodAteco() {
    return codAteco;
  }

  public void setCodAteco(String codAteco) {
    this.codAteco = codAteco;
  }

  public int getNumeroDipendenti() {
    return numeroDipendenti;
  }

  public void setNumeroDipendenti(int numeroDipendenti) {
    this.numeroDipendenti = numeroDipendenti;
  }

  private String partitaIva;
  private String indirizzo;
  private String rappresentante;
  private String codAteco;
  private int numeroDipendenti;

}
