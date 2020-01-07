package storage.beans;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class AttivitaRegistro {

  public AttivitaRegistro() {
  }

  /**
   * Rappresentazione delle attività del registro di tirocinio.
   *
   * @param id l'id dell'attività del registro
   * @param tirocinio il tirocinio a cui è associata l'attività
   * @param data la data in cui si svolge l'attività
   * @param attivita la descrizione dell'attività svolta
   * @param oreSvolte il numero di ore svolte
   */
  public AttivitaRegistro(int id, Tirocinio tirocinio, Date data, String attivita,
      Timestamp oreSvolte) {
    this.id = id;
    this.tirocinio = tirocinio;
    this.data = data;
    this.attivita = attivita;
    this.oreSvolte = oreSvolte;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Tirocinio getTirocinio() {
    return tirocinio;
  }

  public void setTirocinio(Tirocinio tirocinio) {
    this.tirocinio = tirocinio;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public String getAttivita() {
    return attivita;
  }

  public void setAttivita(String attivita) {
    this.attivita = attivita;
  }

  public Timestamp getOreSvolte() {
    return oreSvolte;
  }

  public void setOreSvolte(Timestamp oreSvolte) {
    this.oreSvolte = oreSvolte;
  }

  private int id;
  private Tirocinio tirocinio;
  private Date data;
  private String attivita;
  private Timestamp oreSvolte;

}
