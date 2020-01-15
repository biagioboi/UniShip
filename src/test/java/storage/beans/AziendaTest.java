package storage.beans;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AziendaTest {

  @Test
  void getPartitaIva() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    assertEquals("03944080658", az.getPartitaIva());
  }

  @Test
  void setPartitaIva() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    az.setPartitaIva("03944000000");
    assertEquals("03944000000", az.getPartitaIva());
  }

  @Test
  void getIndirizzo() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    assertEquals("Casal Velino", az.getIndirizzo());
  }

  @Test
  void setIndirizzo() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    az.setIndirizzo("Ascea");
    assertEquals("Ascea", az.getIndirizzo());
  }

  @Test
  void getRappresentante() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    assertEquals("Biagio Boi", az.getRappresentante());
  }

  @Test
  void setRappresentante() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    az.setRappresentante("Gerardo Gullo");
    assertEquals("Gerardo Gullo", az.getRappresentante());
  }

  @Test
  void getCodAteco() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    assertEquals("58524", az.getCodAteco());
  }

  @Test
  void setCodAteco() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    az.setCodAteco("55555");
    assertEquals("55555", az.getCodAteco());
  }

  @Test
  void getNumeroDipendenti() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    assertEquals(20, az.getNumeroDipendenti());
  }

  @Test
  void setNumeroDipendenti() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    az.setNumeroDipendenti(50);
    assertEquals(50, az.getNumeroDipendenti());
  }

  @Test
  void testEqualsWithWrongType() {
    Studente stud = new Studente();
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    assertEquals(false, az.equals(stud));
  }

  @Test
  void testEqualsWithDifferentPIva() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    Azienda azi = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080650", "Casal Velino", "Biagio Boi", "58524", 20);
    assertEquals(false, az.equals(azi));
  }

  @Test
  void testEquals() {
    Azienda az = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    Azienda azi = new Azienda("info@prova.it", "Prova srl", "2:02:44e9f86136f9b41ce62a1d2605e79ac4be5d5793dac00302553500d1dff4af65d2baa89503990c2114a9b95184", "03944080658", "Casal Velino", "Biagio Boi", "58524", 20);
    assertEquals(true, az.equals(azi));
  }
}