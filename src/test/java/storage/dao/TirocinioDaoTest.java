package storage.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TirocinioDaoTest {

  private static TirocinioDao dao = new TirocinioDao();

  @Test
  void doChangeNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doChange(null);
    });
  }

  @Test
  void doRetrieveByStudenteNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByStudente(null);
    });
  }

  @Test
  void doRetrieveByAziendaNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByAzienda(null);
    });
  }

  @Test
  void doRetrieveByKeyNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doRetrieveByKey(-1);
    });
  }

  @Test
  void doSaveNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dao.doSave(null);
    });
  }

}