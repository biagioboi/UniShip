package storage.beans;

public class Azienda extends Utente {

  public Azienda() {
  }

  /**
   * Rappresentazione dell'azienda.
   *
   * @param email l'email dell'azienda
   * @param nome il nome dell'azienda
   * @param password la password per accedere al sistema dell'azienda
   * @param partitaIva la partita iva dell'azienda
   * @param indirizzo l'indirizzo dell'azienda
   * @param rappresentante il rappresentate dell'azienda
   * @param codAteco il codice ATECO dell'azienda
   * @param numeroDipendenti il numero di dipendenti dell'azienda
   */
  public Azienda(String email, String nome, String password, String partitaIva,
      String indirizzo, String rappresentante, String codAteco, int numeroDipendenti) {
    super(email, nome, password, AZIENDA);
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

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Azienda)) {
      return false;
    }
    Azienda other = (Azienda) obj;
    if (this.partitaIva.equals(other.partitaIva)) {
      return true;
    }
    return false;
  }

  private String partitaIva;
  private String indirizzo;
  private String rappresentante;
  private String codAteco;
  private int numeroDipendenti;

}
