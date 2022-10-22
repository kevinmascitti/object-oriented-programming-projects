package orari;


@SuppressWarnings("serial")
public class PercorsoNonValido extends Exception {
	
	public PercorsoNonValido() {}
	public PercorsoNonValido(String msg) {
		super(msg);
	}
}
