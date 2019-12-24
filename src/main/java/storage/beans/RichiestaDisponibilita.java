package storage.beans;


public class RichiestaDisponibilita {

  public RichiestaDisponibilita() {
  }

  /**
   * Rappresentazione della richiesta di disponibilita' presso l'azienda.
   * @param motivazioni le motivazioni della richiesta di disponibilità
   * @param stato lo stato attuale della richiesta
   */
  public RichiestaDisponibilita(String motivazioni, String stato, Azienda azienda,
      Studente studente) {
    this.motivazioni = motivazioni;
    this.stato = stato;
    this.azienda = azienda;
    this.studente = studente;
  }

  public String getMotivazioni() {
    return motivazioni;
  }

  public void setMotivazioni(String motivazioni) {
    this.motivazioni = motivazioni;
  }

  public String getStato() {
    return stato;
  }

  public void setStato(String stato) {
    this.stato = stato;
  }

  public Studente getStudente() {
    return studente;
  }

  public void setStudente(Studente studente) {
    this.studente = studente;
  }

  public Azienda getAzienda() {
    return azienda;
  }

  public void setAzienda(Azienda azienda) {
    this.azienda = azienda;
  }

  private String motivazioni;
  private String stato;
  private Studente studente;
  private Azienda azienda;

}
