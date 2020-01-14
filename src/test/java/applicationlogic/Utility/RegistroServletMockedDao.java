package applicationlogic.Utility;

import applicationlogic.tirociniomanagment.RegistroServlet;
import storage.interfaces.AttivitaRegistroInterface;
import storage.interfaces.TirocinioInterface;

public class RegistroServletMockedDao extends RegistroServlet {

  public void mockDao(AttivitaRegistroInterface attivitaDao, TirocinioInterface tirocinioDao) {
    this.attivitaDao = attivitaDao;
    this.tirocinioDao = tirocinioDao;
  }

}
