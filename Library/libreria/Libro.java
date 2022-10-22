package libreria;

public class Libro {

	private Libreria libreria;
    private String titolo;
	private String autore;
	private int anno;
	private double prezzo;
	private Editore editore;
	private int quantita = 0;
	
	Integer[] venditeS = new Integer[52];
	Integer[] venditeM = new Integer[12];
	
	private int soglia = 0;
	private int quantitaRiordino = 0;


	public Libro(Libreria libreria, String titolo, String autore, int anno, double prezzo, Editore editore) {
		this.libreria=libreria;
		this.titolo=titolo;
		this.autore=autore;
		this.anno=anno;
		this.prezzo=prezzo;
		this.editore=editore;
		for(int i=0; i<52; i++)
			venditeS[i]=0;
		for(int i=0; i<12; i++)
			venditeM[i]=0;
	}

	public String getTitolo(){
        return titolo;
    }
    
    public String getAutore(){
        return autore;
    }
    
    public int getAnno(){
        return anno;
    }

    public double getPrezzo(){
        return prezzo;
    }
    
    public Editore getEditore(){
        return editore;
    }

    public void setQuantita(int q){ 
    	this.quantita=q;
    }
    
    public int getQuantita(){
        return quantita;	
    }

    public void registraVendita(int settimana, int mese){
    	if(settimana<1||settimana>52){
    		System.out.println("Errore settimana!");
    		return;
    	}
    	if(mese<1||mese>12){
    		System.out.println("Errore mese!");
    		return;
    	}
    	quantita=quantita-1;
    	venditeS[settimana-1]++;
    	venditeM[mese-1]++;
    	if(quantita<=soglia) {
    		libreria.creaOrdine(this, editore, quantitaRiordino);
    	}
    }
    
    public int getVenditeS(int settimana){
        return venditeS[settimana-1];
    }
    
    public int getVenditeM(int mese){
        return venditeM[mese-1];
    }

    public void setParametri(int soglia, int quantitaRiordino){   
    	this.soglia=soglia;
    	this.quantitaRiordino=quantitaRiordino;
    }
    
    @Override
    public String toString() {
    	return titolo;
    }
}
