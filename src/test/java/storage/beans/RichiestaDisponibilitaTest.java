package storage.beans;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.junit.jupiter.api.Test;

class RichiestaDisponibilitaTest {

  Studente stu = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125",
      Date.valueOf("2020-01-15"), "Italia", "Casal Velino", "3485813155", "Boi");
  Azienda az = new Azienda("info@prova.it", "Prova srl", "password", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);

  @Test
  void getMotivazioni() {
    RichiestaDisponibilita ric = new RichiestaDisponibilita("none", RichiestaDisponibilita.ACCETTATA, az, stu);
    assertEquals("none", ric.getMotivazioni());
  }

  @Test
  void setMotivazioni() {
    RichiestaDisponibilita ric = new RichiestaDisponibilita("none", RichiestaDisponibilita.ACCETTATA, az, stu);
    ric.setMotivazioni("Yes");
    assertEquals("Yes", ric.getMotivazioni());
  }

  @Test
  void getStato() {
    RichiestaDisponibilita ric = new RichiestaDisponibilita("none", RichiestaDisponibilita.ACCETTATA, az, stu);
    assertEquals(RichiestaDisponibilita.ACCETTATA, ric.getStato());
  }

  @Test
  void setStato() {
    RichiestaDisponibilita ric = new RichiestaDisponibilita("none", RichiestaDisponibilita.ACCETTATA, az, stu);
    ric.setStato(RichiestaDisponibilita.RIFIUTATA);
    assertEquals(RichiestaDisponibilita.RIFIUTATA, ric.getStato());
  }

  @Test
  void getStudente() {
    RichiestaDisponibilita ric = new RichiestaDisponibilita("none", RichiestaDisponibilita.ACCETTATA, az, stu);
    assertEquals(stu, ric.getStudente());
  }

  @Test
  void setStudente() {
    RichiestaDisponibilita ric = new RichiestaDisponibilita("none", RichiestaDisponibilita.ACCETTATA, az, stu);
    Studente y = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703X", "0512105125",
        Date.valueOf("2020-01-15"), "Italia", "Casal Velino", "3485813155", "Boi");
    ric.setStudente(y);
    assertEquals(y, ric.getStudente());
  }

  @Test
  void getAzienda() {
    RichiestaDisponibilita ric = new RichiestaDisponibilita("none", RichiestaDisponibilita.ACCETTATA, az, stu);
    assertEquals(az, ric.getAzienda());
  }

  @Test
  void setAzienda() {
    RichiestaDisponibilita ric = new RichiestaDisponibilita("none", RichiestaDisponibilita.ACCETTATA, az, stu);
    Azienda y = new Azienda("info@prova.it", "Prova srl", "password", "03944080650", "Casal Velino", "Biagio Boi", "58524", 20);
    ric.setAzienda(y);
    assertEquals(y, ric.getAzienda());
  }
}