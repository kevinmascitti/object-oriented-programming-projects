package managingProperties;

public class Request {

	private String id;
	private String app;
	private int codice;
	private Stato stato;
	private String professione;
	private int costo;
	private String maint;

	public enum Stato { pending, assigned, completed };
	
	public Request(String owner, String apartment, String professione, int codiceR) {
		this.id=owner;
		this.app=apartment;
		this.professione=professione;
		this.codice=codiceR;
		this.stato=Stato.pending;
	}

	public String getId() {
		return id;
	}

	public String getApp() {
		return app;
	}
	
	public String getBuilding() {
		return String.valueOf(app.toCharArray(), 0, 2);
	}

	public int getCodice() {
		return codice;
	}

	public Stato getStato() {
		return stato;
	}
	
	public int getSpesa() {
		return costo;
	}

	public void setCompletato(int amount) {
		this.stato = Stato.completed;
		this.costo=amount;
	}

	public String getProfessione() {
		return professione;
	}

	public void setAssigned(String maint) {
		this.stato=Stato.assigned;
		this.maint=maint;
	}

	public String getMaint() {
		return maint;
	}

}
