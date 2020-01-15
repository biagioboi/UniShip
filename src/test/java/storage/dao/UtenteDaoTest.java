package storage.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class UtenteDaoTest {

  private static UtenteDao dao = new UtenteDao();

  @Test
  void doRetrieveAll() throws SQLException {
    assertEquals(5,dao.doRetrieveAll().size());
  }

  @Test
  void doRetrieveByKeyNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByKey(null);
    });
  }

  @Test
  void doSaveNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doSave(null);
    });
  }

  @Test
  void doCheckRegisterNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doCheckRegister(null);
    });
  }

  @Test
  void doCheckLogin() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doCheckLogin(null,null);
    });
  }

  @Test
  void doCheckLogin1() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doCheckLogin("email",null);
    });
  }


}