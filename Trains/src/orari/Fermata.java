package orari;


public class Fermata implements Comparable<Fermata>{
  private String nomeStazione;
  private int ore;
  private int minuti;
  
  public Fermata(String nomeStazione, int ore, int minuti) {
  	this.nomeStazione = nomeStazione;
  	this.ore = ore;
  	this.minuti = minuti;
  }

  public String getStazione() {
    return nomeStazione;
  }

  public int getOre() {
    return ore;
  }

  public int getMinuti() {
    return minuti;
  }
  
  @Override
  public int compareTo(Fermata altra) {
	  if(this.ore!=altra.ore)
		  return this.ore - altra.ore;
	  return this.minuti - altra.minuti;
  }

}
