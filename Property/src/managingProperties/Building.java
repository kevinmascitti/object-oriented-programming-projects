package managingProperties;

public class Building {
	
	private class Apartment {
		private int n;
		private Owner proprietario;
		public boolean free;
		Apartment(int n){
			this.n=n;
			free=true;
		}
	}

	private String nome;
	private int totapp;
	Apartment[] app;

	public Building(String building, int n) {
		this.nome=building;
		this.totapp=n;
		app = new Apartment[n];
		for(int i = 0; i<n; i++)
			app[i] = new Apartment(i);
	}

	public String getNome() {
		return nome;
	}

	public int getTotapp() {
		return totapp;
	}

	public void setOwner(Owner o, int n) {
		this.app[n-1].proprietario=o;
		this.app[n-1].free=false;
	}

	public Apartment getApp(int n) {
		return app[n-1];
	}

	public boolean isFree(int n) {
		return app[n-1].free;
	}

}
