package applicationlogic.Utility;

import applicationlogic.tirociniomanagment.RegistroServlet;
import applicationlogic.usersmanagment.HandleUserServlet;
import storage.interfaces.AttivitaRegistroInterface;
import storage.interfaces.AziendaInterface;
import storage.interfaces.RichiestaDisponibilitaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.TirocinioInterface;
import storage.interfaces.UtenteInterface;

public class RegistroServletMockedDao extends RegistroServlet {

  public void mockDao(AttivitaRegistroInterface attivitaDao, TirocinioInterface tirocinioDao) {
    this.attivitaDao = attivitaDao;
    this.tirocinioDao = tirocinioDao;
  }

}
