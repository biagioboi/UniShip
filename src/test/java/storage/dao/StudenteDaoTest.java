package storage.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class StudenteDaoTest {

  private static StudenteDao dao = new StudenteDao();

  @Test
  void doRetrieveAll() throws SQLException {
    assertEquals(1,dao.doRetrieveAll().size());
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
}