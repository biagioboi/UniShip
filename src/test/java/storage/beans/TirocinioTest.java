package storage.beans;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.junit.jupiter.api.Test;

class TirocinioTest {

  Studente stu = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125",
      Date.valueOf("2020-01-15"), "Italia", "Casal Velino", "3485813155", "Boi");
  Azienda az = new Azienda("info@prova.it", "Prova srl", "password", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);

  @Test
  void getId() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals(5, tir.getId());
  }

  @Test
  void setId() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    tir.setId(10);
    assertEquals(10, tir.getId());
  }

  @Test
  void getStato() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals(Tirocinio.NON_COMPLETO, tir.getStato());
  }

  @Test
  void setStato() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    tir.setStato(Tirocinio.IN_CORSO);
    assertEquals(Tirocinio.IN_CORSO, tir.getStato());
  }

  @Test
  void getOreTotali() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals(100, tir.getOreTotali());
  }

  @Test
  void setOreTotali() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    tir.setOreTotali(200);
    assertEquals(200, tir.getOreTotali());
  }

  @Test
  void getTurorEsterno() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals("Biagio Boi", tir.getTurorEsterno());
  }

  @Test
  void setTurorEsterno() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    tir.setTurorEsterno("Gerardo Gullo");
    assertEquals("Gerardo Gullo", tir.getTurorEsterno());
  }

  @Test
  void getOreSvolte() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals(50, tir.getOreSvolte());
  }

  @Test
  void setOreSvolte() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    tir.setOreSvolte(60);
    assertEquals(60, tir.getOreSvolte());
  }

  @Test
  void getPath() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals("c:qualcosa", tir.getPath());
  }

  @Test
  void setPath() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    tir.setPath("c:qualcosaltro");
    assertEquals("c:qualcosaltro", tir.getPath());
  }

  @Test
  void getStudente() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals(stu, tir.getStudente());
  }

  @Test
  void setStudente() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    Studente y = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703X", "0512105125",
        Date.valueOf("2020-01-15"), "Italia", "Casal Velino", "3485813155", "Boi");
    tir.setStudente(y);
    assertEquals(y, tir.getStudente());
  }

  @Test
  void getAzienda() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals(az, tir.getAzienda());
  }

  @Test
  void setAzienda() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    Azienda y = new Azienda("info@prova.it", "Prova srl", "password", "03944080650", "Casal Velino", "Biagio Boi", "58524", 20);
    tir.setAzienda(y);
    assertEquals(y, tir.getAzienda());
  }

  @Test
  void getMotivazioni() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals("None", tir.getMotivazioni());
  }

  @Test
  void setMotivazioni() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    tir.setMotivazioni("Yes");
    assertEquals("Yes", tir.getMotivazioni());
  }


  @Test
  void testEqualsWithWrongType() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals(false, tir.equals(stu));
  }

  @Test
  void testEqualsWithDifferentId() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    Tirocinio tiro = new Tirocinio(10, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals(false, tir.equals(tiro));
  }

  @Test
  void testEquals() {
    Tirocinio tir = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    Tirocinio tiro = new Tirocinio(5, Tirocinio.NON_COMPLETO, 100, "Biagio Boi", 50, "c:qualcosa", stu, az, "None");
    assertEquals(true, tir.equals(tiro));
  }
}