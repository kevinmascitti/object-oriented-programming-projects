package clinic;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Doctor {
	
	private String name;
	private String surname;
	private String ssn;
	private int id;
	private String specializzazione;
	
	private List<Patient> patients = new LinkedList<>();
	
	public Doctor(String name, String surname, String ssn, Integer id, String specializzazione) {
		this.name=name;
		this.surname=surname;
		this.ssn=ssn;
		this.id=id;
		this.specializzazione=specializzazione;
	}
	
	public void addPatient(Patient p) {
		patients.add(p);
	}
	
	public Collection<String> getPatients() {
		return patients.stream()
					   .map(Patient::getSsn)
					   .collect(Collectors.toList());
	}

	public String format() {
		return String.format("%3d", patients.size())+" : "+id+" "+surname+" "+name; 
	}

	@Override
	public String toString() {
		return surname+" "+name+" ("+ssn+") ["+id+"]: "+specializzazione;
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


	public int getId() {
		return id;
	}


	public String getSpecializzazione() {
		return specializzazione;
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


	public void setId(int id) {
		this.id = id;
	}


	public void setSpecializzazione(String specializzazione) {
		this.specializzazione = specializzazione;
	}

	
}
