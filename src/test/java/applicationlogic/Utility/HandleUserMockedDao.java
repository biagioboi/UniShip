package applicationlogic.Utility;

import applicationlogic.usersmanagment.HandleUserServlet;
import storage.interfaces.AziendaInterface;
import storage.interfaces.RichiestaDisponibilitaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.UtenteInterface;

public class HandleUserMockedDao extends HandleUserServlet {

  public void mockDao(StudenteInterface studenteDao, AziendaInterface aziendaDao,
      UtenteInterface utenteDao, RichiestaDisponibilitaInterface tirocinioDao) {
    this.studenteDao = studenteDao;
    this.aziendaDao = aziendaDao;
  }
}
