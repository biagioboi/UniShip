package applicationlogic.Utility;

import applicationlogic.usersmanagment.SessionServlet;
import applicationlogic.usersmanagment.SignUpServlet;
import storage.interfaces.AziendaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.TirocinioInterface;
import storage.interfaces.UtenteInterface;

public class SignupServletMockedDao extends SignUpServlet {

  public void mockDao(StudenteInterface studenteDao, AziendaInterface aziendaDao,
      UtenteInterface utenteDao, TirocinioInterface tirocinioDao) {
    this.studenteDao = studenteDao;
    this.utenteDao = utenteDao;
  }
}
