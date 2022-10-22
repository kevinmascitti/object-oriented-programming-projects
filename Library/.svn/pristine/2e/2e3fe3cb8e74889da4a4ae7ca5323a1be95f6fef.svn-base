package libreria;

public class Ordine {

    private Libro libro;
	private Editore editore;
	private int quantita;
	private int codice;
	private boolean consegnato;

	public Ordine(Libro libro, Editore editore, int quantitaRiordino, int codice) {
		this.libro=libro;
		this.editore=editore;
		this.quantita=quantitaRiordino;
		this.codice=codice;
		consegnato=false;
	}

	public Editore getEditore(){
        return editore;
    }
    
    public Libro getLibro(){
        return libro;
    }
    
    public int getQuantita(){
        return quantita;
    }

    public boolean isConsegnato(){
        return consegnato;
    }

    public int getNumero(){
        return codice;
    }
    
    public void setConsegnato() {
    	consegnato=true;
    }
    
    @Override
    public String toString() {
    	String s = "Ordine numero "+codice+"; Editore: "+editore+"; Libro: "+libro+"; Quantità: "+quantita+"; Stato: ";
    	if(consegnato=true)
    		s+="Ordine Consegnato\n";
    	else
    		s+="Ordine in giacenza\n";
    	return s;
    }
}
