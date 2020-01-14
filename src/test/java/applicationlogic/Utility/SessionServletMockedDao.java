package applicationlogic.Utility;

import applicationlogic.usersmanagment.SessionServlet;
import storage.interfaces.AziendaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.TirocinioInterface;
import storage.interfaces.UtenteInterface;

public class SessionServletMockedDao extends SessionServlet {

  public void mockDao(StudenteInterface studenteDao, AziendaInterface aziendaDao,
      UtenteInterface utenteDao, TirocinioInterface tirocinioDao) {
    this.studenteDao = studenteDao;
    this.aziendaDao = aziendaDao;
    this.utenteDao = utenteDao;
  }
}
