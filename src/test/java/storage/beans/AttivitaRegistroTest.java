package storage.beans;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.junit.jupiter.api.Test;

class AttivitaRegistroTest {

  private Tirocinio tirocinio = new Tirocinio();
  private Date data = Date.valueOf("2020-01-15");

  @Test
  void getId() {
    AttivitaRegistro att = new AttivitaRegistro(5, tirocinio, data, "", 100);
    assertEquals(5, att.getId());
  }

  @Test
  void setId() {
    AttivitaRegistro att = new AttivitaRegistro(5, tirocinio, data, "", 100);
    att.setId(10);
    assertEquals(10, att.getId());
  }

  @Test
  void getTirocinio() {
    AttivitaRegistro att = new AttivitaRegistro(5, tirocinio, data, "", 100);
    assertEquals(tirocinio, att.getTirocinio());
  }

  @Test
  void setTirocinio() {
    AttivitaRegistro att = new AttivitaRegistro(5, null, data, "", 100);
    att.setTirocinio(tirocinio);
    assertEquals(tirocinio, att.getTirocinio());
  }

  @Test
  void getData() {
    AttivitaRegistro att = new AttivitaRegistro(5, tirocinio, data, "", 100);
    assertEquals(data, att.getData());
  }

  @Test
  void setData() {
    AttivitaRegistro att = new AttivitaRegistro(5, tirocinio, null, "", 100);
    att.setData(data);
    assertEquals(data, att.getData());
  }

  @Test
  void getAttivita() {
    AttivitaRegistro att = new AttivitaRegistro(5, tirocinio, data, "lavoro", 100);
    assertEquals("lavoro", att.getAttivita());
  }

  @Test
  void setAttivita() {
    AttivitaRegistro att = new AttivitaRegistro(5, tirocinio, data, "lavoro", 100);
    att.setAttivita("lavoro molto");
    assertEquals("lavoro molto", att.getAttivita());
  }

  @Test
  void getOreSvolte() {
    AttivitaRegistro att = new AttivitaRegistro(5, tirocinio, data, "lavoro", 100);
    assertEquals(100, att.getOreSvolte());
  }

  @Test
  void setOreSvolte() {
    AttivitaRegistro att = new AttivitaRegistro(5, tirocinio, data, "lavoro", 100);
    att.setOreSvolte(200);
    assertEquals(200, att.getOreSvolte());
  }
}