package storage.beans;

import java.sql.Time;

/*CAMBIAMENTO: INSERITE STRINGHE FINAL PER LO STATO*/

public class Tirocinio {

  public Tirocinio() {
  }

  /**
   * Rappresentazione del tirocinio.
   *
   * @param id l'id del tirocinio
   * @param stato lo stato attuale del tirocinio
   * @param oreTotali le ore totali del tirocinio
   * @param turorEsterno il nome del tutor esterno del tirocinio
   * @param oreSvolte le ore già svolte
   * @param path la path del PDF del Progetto Formativo
   * @param motivazioni le motivazioni di una eventuale rifiuta
   */
  public Tirocinio(int id, String stato, Time oreTotali, String turorEsterno, Time oreSvolte,
      String path, Studente studente, Azienda azienda, String motivazioni) {
    this.id = id;
    this.stato = stato;
    this.oreTotali = oreTotali;
    this.turorEsterno = turorEsterno;
    this.oreSvolte = oreSvolte;
    this.path = path;
    this.studente = studente;
    this.azienda = azienda;
    this.motivazioni = motivazioni;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStato() {
    return stato;
  }

  public void setStato(String stato) {
    this.stato = stato;
  }

  public Time getOreTotali() {
    return oreTotali;
  }

  public void setOreTotali(Time oreTotali) {
    this.oreTotali = oreTotali;
  }

  public String getTurorEsterno() {
    return turorEsterno;
  }

  public void setTurorEsterno(String turorEsterno) {
    this.turorEsterno = turorEsterno;
  }

  public Time getOreSvolte() {
    return oreSvolte;
  }

  public void setOreSvolte(Time oreSvolte) {
    this.oreSvolte = oreSvolte;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
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

  public String getMotivazioni() {
    return motivazioni;
  }

  public void setMotivazioni(String motivazioni) {
    this.motivazioni = motivazioni;
  }

  private int id;
  private String stato;
  private Time oreTotali;
  private String turorEsterno;
  private Time oreSvolte;
  private String motivazioni;
  private String path;
  private Studente studente;
  private Azienda azienda;

  public static final String NON_COMPLETO = "Non completo";
  public static final String DA_VALUTARE = "Da valutare";
  public static final String DA_CONVALIDARE = "Da Convalidare";
  public static final String RIFIUTATA = "Rifiutata";
  public static final String ACCETTATA = "Accettata";


}
