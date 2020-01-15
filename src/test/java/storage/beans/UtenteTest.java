package storage.beans;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtenteTest {

  @Test
  void getEmail() {
    Utente u = new Utente("b.boi@studenti.unisa.it", "Biagio", "password", "studente");
    assertEquals("b.boi@studenti.unisa.it", u.getEmail());
  }

  @Test
  void setEmail() {
    Utente u = new Utente("b.boi@studenti.unisa.it", "Biagio", "password", "studente");
    u.setEmail("b.boi1@studenti.unisa.it");
    assertEquals("b.boi1@studenti.unisa.it", u.getEmail());
  }

  @Test
  void getNome() {
    Utente u = new Utente("b.boi@studenti.unisa.it", "Biagio", "password", "studente");
    assertEquals("Biagio", u.getNome());
  }

  @Test
  void setNome() {
    Utente u = new Utente("b.boi@studenti.unisa.it", "Biagio", "password", "studente");
    u.setNome("Gerardo");
    assertEquals("Gerardo", u.getNome());
  }

  @Test
  void getPassword() {
    Utente u = new Utente("b.boi@studenti.unisa.it", "Biagio", "password", "studente");
    assertEquals("password", u.getPassword());
  }

  @Test
  void setPassword() {
    Utente u = new Utente("b.boi@studenti.unisa.it", "Biagio", "password", "studente");
    u.setPassword("password2");
    assertEquals("password2", u.getPassword());
  }

  @Test
  void getTipo() {
    Utente u = new Utente("b.boi@studenti.unisa.it", "Biagio", "password", "studente");
    assertEquals("studente", u.getTipo());
  }

  @Test
  void setTipo() {
    Utente u = new Utente("b.boi@studenti.unisa.it", "Biagio", "password", "studente");
    u.setTipo("azienda");
    assertEquals("azienda", u.getTipo());
  }
}