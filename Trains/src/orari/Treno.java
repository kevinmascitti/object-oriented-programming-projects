package orari;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Treno implements Comparable<Treno>{
	private Percorso percorso;
	private int giorno;
	private int mese;
	private int anno;
	
	private List<Passaggio> passaggi = new ArrayList<>();

  public Treno(Percorso percorso, int giorno, int mese, int anno) {
	this.percorso=percorso;
	this.giorno=giorno;
	this.mese=mese;
	this.anno=anno;
  }

  public Percorso getPercorso() {
	return percorso;
  }

  public int getGiorno() {
    return giorno;
  }

  public int getMese() {
    return mese;
  }

  public int getAnno() {
    return anno;
  }

  public Passaggio registraPassaggio(String nomeStazione, int ore, int minuti) 
  	throws StazioneNonValida {
	List<Fermata> fermate = percorso.getFermate();
	for(Fermata f : fermate) {
		if(f.getStazione().equals(nomeStazione)) {
			Passaggio p = new Passaggio(f,ore,minuti);
			passaggi.add(p);
			return p;
		}
	}
	throw new StazioneNonValida();
  }

  public boolean arrivato() {
	Fermata ultima = percorso.getFermate().get(percorso.getFermate().size()-1);
	Collections.sort(passaggi, Comparator.comparingInt(Passaggio::getOra).thenComparing(Passaggio::getMinuti));
	Passaggio ultimo = passaggi.get(passaggi.size()-1);
	return ultimo.getStazione().equals(ultima.getStazione());
  }

  public int ritardoMassimo() {
	return passaggi.stream()
			.mapToInt(Passaggio::ritardo)
			.max().orElse(0);
  }

  public int ritardoFinale() {
	if(passaggi.size()==0)
		return 0;
	Collections.sort(passaggi, Comparator.comparingInt(Passaggio::getOra).thenComparing(Passaggio::getMinuti));
	Passaggio ultimo = passaggi.get(passaggi.size()-1);
    return ultimo.ritardo();
  }
  
  @Override
  public int compareTo(Treno altro) {
	  return (altro.anno*10000+altro.mese*100+altro.giorno)-(anno*10000+mese*100+giorno);
  }
  

}
