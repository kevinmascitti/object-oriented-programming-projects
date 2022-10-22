package applications;

public class Capability {

	Richiedente richiedente;
	Skill competenza;
	Integer punti;

	public Capability(Richiedente r, Skill sk, int punti) {
		this.richiedente=r;
		this.competenza=sk;
		this.punti=punti;
	}

	public Richiedente getRichiedente() {
		return richiedente;
	}

	public Skill getSkill() {
		return competenza;
	}

	public Integer getPunti() {
		return punti;
	}

}
