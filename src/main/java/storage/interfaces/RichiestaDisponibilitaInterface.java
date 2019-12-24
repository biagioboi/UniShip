package storage.interfaces;

import java.util.ArrayList;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;

/*cambiata la signature dei metodi, i retrive prendono come parametri gli oggetti e non
l'identificativo; mentre gli aggiornamenti e inserimenti restituiscono un boolean e non int*/

public interface RichiestaDisponibilitaInterface {

  public ArrayList<RichiestaDisponibilita> doRetrieveByAzienda(Azienda azienda);

  public ArrayList<RichiestaDisponibilita> doRetrieveByStudente(Studente studente);

  public boolean doChange(RichiestaDisponibilita richiesta);

  public boolean doSave(RichiestaDisponibilita richiesta);
}
