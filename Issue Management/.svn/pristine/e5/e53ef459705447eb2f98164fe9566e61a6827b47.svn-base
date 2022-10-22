package ticketing;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Componente {

	private String nome;
	private Map<String,Componente> sopracomponenti = new LinkedHashMap<>();
	private Map<String,Componente> sottocomponenti = new LinkedHashMap<>();

	public Componente(String name) {
		this.nome=name;
	}

	public String getNome() {
		return nome;
	}

	public Map<String,Componente> getPrecs() {
		return sopracomponenti;
	}

	public Map<String,Componente> getSuccs() {
		return sottocomponenti;
	}

	public void addPrec(Componente c) {
		sopracomponenti.putIfAbsent(c.getNome(), c);
	}

	public void addSucc(Componente c) {
		sottocomponenti.put(c.getNome(), c);
	}

}
