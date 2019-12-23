package Storage.Beans;

import java.time.LocalDate;

public class Tirocinio {

  public Tirocinio() {
  }

  public Tirocinio(String id, LocalDate oreTotali, String turorEsterno, LocalDate oreSvolte,
      String path) {
    this.id = id;
    this.oreTotali = oreTotali;
    turorEsterno = turorEsterno;
    this.oreSvolte = oreSvolte;
    this.path = path;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    id = id;
  }

  public LocalDate getOreTotali() {
    return oreTotali;
  }

  public void setOreTotali(LocalDate oreTotali) {
    this.oreTotali = oreTotali;
  }

  public String getTurorEsterno() {
    return turorEsterno;
  }

  public void setTurorEsterno(String turorEsterno) {
    turorEsterno = turorEsterno;
  }

  public LocalDate getOreSvolte() {
    return oreSvolte;
  }

  public void setOreSvolte(LocalDate oreSvolte) {
    this.oreSvolte = oreSvolte;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  private String id;
  private LocalDate oreTotali;
  private String turorEsterno;
  private LocalDate oreSvolte;
  private String path;

  public static final String NON_COMPLETO = "Non Completo";
  public static final String DA_VALUTARE = "Da Valutare";
  public static final String DA_CONVALIDARE = "Da Convalidare";
  public static final String RIFIUTATA = "Rifiutata";
  public static final String ACCETTATA = "Accettata";

}
