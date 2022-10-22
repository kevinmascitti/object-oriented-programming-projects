package managingProperties;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Owner {

	private String nome;
	private String[] appartamenti;

	public Owner(String owner, String[] apartments) {
		this.nome=owner;
		this.appartamenti=apartments;
	}

	public String getNome() {
		return nome;
	}

	public String[] getAppartamenti() {
		return appartamenti;
	}

	public boolean possiede(String apartment) {
		if(!Stream.of(appartamenti).collect(Collectors.toList()).contains(apartment))
			return false;
		return true;
	}

}
