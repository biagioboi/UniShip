package storage.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import storage.beans.Azienda;
import storage.beans.RichiestaDisponibilita;
import storage.beans.Studente;

/*cambiata la signature dei metodi, i retrive prendono come parametri gli oggetti e non
l'identificativo; mentre gli aggiornamenti e inserimenti restituiscono un boolean e non int*/

public interface RichiestaDisponibilitaInterface {


  /**
   * Questo metodo si occupa di trovare le richieste di disponibilità associate all'azienda
   * passata come parametro.
   * @param azienda l'Azienda di cui si vogliono sapere le richieste di disponibilità.
   * @return ArrayList di oggetti di tipo RichiestaDisponibilita
   * @throws SQLException nel caso in cui non si riesce ad eseguire la query.
   * @throws IllegalArgumentException nel caso in cui si passa un'Azienda non presente nel Database.
   * @pre azienda != null
   */
  ArrayList<RichiestaDisponibilita> doRetrieveByAzienda(Azienda azienda)
      throws SQLException, IllegalArgumentException;

  ArrayList<RichiestaDisponibilita> doRetrieveByStudente(Studente studente);

  boolean doChange(RichiestaDisponibilita richiesta);

  boolean doSave(RichiestaDisponibilita richiesta);
}
