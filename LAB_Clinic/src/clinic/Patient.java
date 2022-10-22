package clinic;

public class Patient {
	
	private String name;
	private String surname;
	private String ssn;
	private Doctor doc;
	
	public Patient (String name, String surname, String ssn) {
		this.name = name;
		this.surname = surname;
		this.ssn = ssn;
	}

	public void setDoc(Doctor doc) {
		this.doc = doc;
	}
	
	public Doctor getDoctor() {
		return this.doc;
	}

	@Override
	public String toString() {
		return surname+" "+name+" ("+ssn+")";
	}
	
	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getSsn() {
		return ssn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Doctor getDoc() {
		return doc;
	}

}
