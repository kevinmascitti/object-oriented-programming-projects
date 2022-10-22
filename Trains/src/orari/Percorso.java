package orari;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Percorso {
	
	private String codice;
	private String categoria;
	private boolean straordinario;
	private List<Fermata> fermate = new ArrayList<>();
	private List<Treno> treni = new ArrayList<>();

	public Percorso(String codice, String categoria) {
		this.codice=codice;
		this.categoria=categoria;
		this.straordinario=false;
	}

	public String getCodice() {
		return codice;
	}

	public String getCategoria() {
		return categoria;
	}

	public boolean isStraordinario() {
		return straordinario;
	}

	public void setStraordinario(boolean newValue) {
		this.straordinario=newValue;
	}

	public Fermata aggiungiFermata(String nomeStazione, int ore, int minuti) {
		Fermata f = new Fermata(nomeStazione, ore, minuti);
		fermate.add(f);
		return f;
	}

	public List<Treno> getTreni() {
		return treni;
	}

	public List<Fermata> getFermate() {
		Collections.sort(fermate, Comparator.comparingInt(Fermata::getOre).thenComparingInt(Fermata::getMinuti));
		return Collections.unmodifiableList(fermate);
	}

	public int ritardoMassimo() {
		return treni.stream()
				.mapToInt(Treno::ritardoFinale)
				.max().orElse(0);
	}

	public int ritardoMedio() {
		return (int) treni.stream()
				.mapToInt(Treno::ritardoFinale)
				.average().orElse(0);
	}

	public void addTreno(Treno t) {
		treni.add(t);
	}

}

