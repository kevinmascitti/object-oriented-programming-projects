package orari;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Orari {
	
	private Map<String, Percorso> mappaPercorsi = new HashMap<>();
	
	public Percorso creaPercorso(String codice, String categoria) {
		Percorso p = new Percorso(codice, categoria);
		mappaPercorsi.put(codice, p);
		return p;
	}

	public Collection<Percorso> getPercorsi() {
		return mappaPercorsi.values();
	}

	public Percorso getPercorso(String codice) {
		return mappaPercorsi.get(codice);
	}

	public Treno nuovoTreno(String codice, int giorno, int mese, int anno) 
			throws PercorsoNonValido {
		if(!mappaPercorsi.containsKey(codice)) {
			throw new PercorsoNonValido("Codice non esistente: " + codice);
		}
		Percorso p = mappaPercorsi.get(codice);
		Treno t = new Treno(p, giorno, mese, anno);
		p.addTreno(t);
		return t;
	}

	public List<String> classificaRitardi() {
		return mappaPercorsi.values().stream()
				.sorted(Comparator.comparing(Percorso::ritardoMedio).reversed())
				.map( p->p.getCodice()+": "+p.ritardoMedio())
				.collect(Collectors.toList());
	}

}
