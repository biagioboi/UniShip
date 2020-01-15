package storage.beans;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.junit.jupiter.api.Test;

class StudenteTest {

  Date data = Date.valueOf("2020-01-15");

  @Test
  void getCodiceFiscale() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    assertEquals("BOIBGI99A05H703C", st.getCodiceFiscale());
  }

  @Test
  void setCodiceFiscale() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    st.setCodiceFiscale("BOIBGI99A05H703X");
    assertEquals("BOIBGI99A05H703X", st.getCodiceFiscale());
  }

  @Test
  void getMatricola() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    assertEquals("0512105125", st.getMatricola());
  }

  @Test
  void setMatricola() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    st.setMatricola("0512105120");
    assertEquals("0512105120", st.getMatricola());
  }

  @Test
  void getDataDiNascita() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    assertEquals(data, st.getDataDiNascita());
  }

  @Test
  void setDataDiNascita() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    Date y = Date.valueOf("2020-10-10");
    st.setDataDiNascita(y);
    assertEquals(y, st.getDataDiNascita());
  }

  @Test
  void getCittadinanza() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    assertEquals("Italia", st.getCittadinanza());
  }

  @Test
  void setCittadinanza() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    st.setCittadinanza("Congo");
    assertEquals("Congo", st.getCittadinanza());
  }

  @Test
  void getResidenza() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    assertEquals("Casal Velino", st.getResidenza());
  }

  @Test
  void setResidenza() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    st.setResidenza("Ascea");
    assertEquals("Ascea", st.getResidenza());
  }

  @Test
  void getNumero() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    assertEquals("3485813155", st.getNumero());
  }

  @Test
  void setNumero() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    st.setNumero("3485813100");
    assertEquals("3485813100", st.getNumero());
  }

  @Test
  void getCognome() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    assertEquals("Boi", st.getCognome());
  }

  @Test
  void setCognome() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    st.setCognome("Gullo");
    assertEquals("Gullo", st.getCognome());
  }

  @Test
  void testEqualsWithWrongType() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    Azienda az = new Azienda("info@prova.it", "Prova srl", "password", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    assertEquals(false, st.equals(az));
  }

  @Test
  void testEqualsWithDifferentCF() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    Studente stu = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703X", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    assertEquals(false, st.equals(stu));
  }

  @Test
  void testEquals() {
    Studente st = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    Studente stu = new Studente("b.boi@studenti.unisa.it", "Biagio", "password", "BOIBGI99A05H703C", "0512105125", data, "Italia", "Casal Velino", "3485813155", "Boi");
    assertEquals(true, st.equals(stu));
  }
}