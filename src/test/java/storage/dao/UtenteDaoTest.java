package storage.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.DatabaseManager;
import storage.beans.Utente;

class UtenteDaoTest {
  private static UtenteDao utenteDao = new UtenteDao();
  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void doCheckLogin() {
    try {
      assertTrue(utenteDao.doCheckLogin("g.gullo@studenti.unisa.it","password"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void doCheckRegister() {
  }

  @Test
  void doRetrieveAll() {
  }

  @Test
  void doRetrieveByKey() {
    try {
      assertEquals("Gerardo",utenteDao.doRetrieveByKey("g.gullo@studenti.unisa.it").getNome());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void doSave() {
    Utente utente = new Utente();
    utente.setNome("Gerardo");
    utente.setTipo(Utente.STUDENTE);
    utente.setEmail("g.gullo@studenti.unisa.it");
    utente.setPassword("password");

    try {
      assertTrue(utenteDao.doSave(utente));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}