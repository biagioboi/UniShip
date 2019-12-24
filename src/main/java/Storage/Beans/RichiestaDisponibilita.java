package Storage.Beans;


public class RichiestaDisponibilita {

  public RichiestaDisponibilita() {
  }

  /**
   * @param motivazioni le motivazioni della richiesta di disponibilità
   * @param stato lo stato attuale della richiesta
   */
  public RichiestaDisponibilita(String motivazioni, String stato) {
    this.motivazioni = motivazioni;
    this.stato = stato;
  }

  public String getMotivazioni() { return motivazioni; }

  public void setMotivazioni(String motivazioni) { this.motivazioni = motivazioni; }

  public String getStato() { return stato; }

  public void setStato(String stato) { this.stato = stato; }

  private String motivazioni;
  private String stato;

}
