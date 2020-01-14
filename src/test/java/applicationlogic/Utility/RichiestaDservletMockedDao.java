package applicationlogic.Utility;

import applicationlogic.richiestadisponibilitamanagment.RichiestaDServlet;
import storage.interfaces.AziendaInterface;
import storage.interfaces.RichiestaDisponibilitaInterface;
import storage.interfaces.StudenteInterface;
import storage.interfaces.UtenteInterface;

public class RichiestaDservletMockedDao extends RichiestaDServlet {

  public void mockDao(StudenteInterface studenteDao, AziendaInterface aziendaDao,
      UtenteInterface utenteDao, RichiestaDisponibilitaInterface richiestaDao) {
    this.studenteDao = studenteDao;
    this.aziendaDao = aziendaDao;
    this.utenteDao = utenteDao;
    this.richiestaDao = richiestaDao;
  }
}
