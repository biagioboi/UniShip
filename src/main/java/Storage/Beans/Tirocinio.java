package Storage.Beans;

import java.sql.Time;

public class Tirocinio {

  public Tirocinio() {
  }

  /**
   * @param id l'id del tirocinio
   * @param stato lo stato attuale del tirocinio
   * @param oreTotali le ore totali del tirocinio
   * @param turorEsterno il nome del tutor esterno del tirocinio
   * @param oreSvolte le ore già svolte
   * @param path la path del PDF del Progetto Formativo
   */
  public Tirocinio(String id, String stato, Time oreTotali, String turorEsterno, Time oreSvolte,
      String path) {
    this.id = id;
    this.stato = stato;
    this.oreTotali = oreTotali;
    this.turorEsterno = turorEsterno;
    this.oreSvolte = oreSvolte;
    this.path = path;
  }

  public String getId() { return id; }

  public void setId(String id) { this.id = id; }

  public String getStato() { return stato; }

  public void setStato(String stato) { this.stato = stato; }

  public Time getOreTotali() { return oreTotali; }

  public void setOreTotali(Time oreTotali) { this.oreTotali = oreTotali; }

  public String getTurorEsterno() { return turorEsterno; }

  public void setTurorEsterno(String turorEsterno) { this.turorEsterno = turorEsterno; }

  public Time getOreSvolte() { return oreSvolte; }

  public void setOreSvolte(Time oreSvolte) { this.oreSvolte = oreSvolte; }

  public String getPath() { return path; }

  public void setPath(String path) { this.path = path; }

  private String id;
  private String stato;
  private Time oreTotali;
  private String turorEsterno;
  private Time oreSvolte;
  private String path;

  public final static String NON_COMPLETO = "non_completo";
  public final static String DA_VALUTARE = "da_valutare";
  public final static String DA_CONVALIDARE = "da_convalidare";
  public final static String RIFIUTATA = "rifiutata";
  public final static String ACCETTATA = "accettata";


}
