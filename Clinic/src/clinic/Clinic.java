package clinic;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {

	private Map<String,Patient> patients = new HashMap<>();
	private Map<Integer,Doctor> doctors = new HashMap<>();
	private Map<Integer,Patient> registered = new HashMap<>();
	private List<String> data = new ArrayList<>();
	
	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		Patient p = new Patient(first,last,ssn);
		patients.putIfAbsent(ssn, p);

	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		if(patients.containsKey(ssn)) {
			return patients.get(ssn).toString();
		} else {
			throw new NoSuchPatient("Il paziente non è presente.");
		}
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		Doctor d = new Doctor(first,last,ssn,docID,specialization);
		doctors.putIfAbsent(docID, d);
		
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		if(doctors.containsKey(docID)) {
			return doctors.get(docID).toString();
		} else {
			throw new NoSuchDoctor("Il dottore non è presente.");
		}
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		Patient p;
		Doctor d;
		if(patients.containsKey(ssn)) {
			p = patients.get(ssn);
		} else {
			throw new NoSuchPatient("Il paziente non è presente.");
		}
		if(doctors.containsKey(docID)) {
			d = doctors.get(docID);
		} else {
			throw new NoSuchDoctor("Il dottore non è presente.");
		}
		p.setDoc(d);
		d.addPatient(p);

	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		Patient p;
		if(patients.containsKey(ssn)) {
			p = patients.get(ssn);
		} else {
			throw new NoSuchPatient("Il paziente non è presente.");
		}
		Doctor d = p.getDoctor();
		if(doctors.containsKey(d.getId())) {
			return d.getId();
		} else {
			throw new NoSuchDoctor("Il dottore non è presente.");
		}
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		Doctor d;
		if(doctors.containsKey(id)) {
			d = doctors.get(id);
		} else {
			throw new NoSuchDoctor("Il dottore non è presente.");
		}
		return d.getPatients();
		
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public void loadData(Reader reader) throws IOException {
		try (BufferedReader in = new BufferedReader(reader)) {
			data = in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		String regExpP = "\\s*(P)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*";
		String regExpM = "\\s*(M)\\s*;\\s*(\\d+)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*";
		Pattern paP = Pattern.compile(regExpP);
		Pattern paM = Pattern.compile(regExpM);
		Matcher m;
		for(String s : data) {
			m = paP.matcher(s);
			if( m.matches() ) {
				if( m.group(1).equals("P") ) {
					Patient p = new Patient (m.group(2), m.group(3), m.group(4));
					patients.put(m.group(4), p);
				}
				
			}
			else {
				m = paM.matcher(s);
				if(m.matches()) {
					if( m.group(1).equals("M") ) {
						try {
							Integer.parseInt(m.group(2));
						}
						catch(NumberFormatException e) {
							System.out.println("NumberFormatException trovata!");
						}
						Doctor d = new Doctor (m.group(3), m.group(4), m.group(5), Integer.parseInt(m.group(2)), m.group(6));
						doctors.put(Integer.parseInt(m.group(2)), d);
					}
				}
			}
		}
	}


	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		Collection<Integer> c = doctors.values().stream()
					.filter(p->p.getPatients().size() == 0)
					.sorted( Comparator.comparing(Doctor::getSurname).thenComparing(Doctor::getName))
					.map(p->p.getId())
					.collect(Collectors.toCollection(LinkedList::new));
		System.out.println(c);
		return c;
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		int sum = 0;
		int i = 0;
		for(Doctor d : doctors.values()) {
			sum+=d.getPatients().size();
			i++;
		}
		int media = sum/i;
		
		return doctors.values().stream()
				.filter(p->p.getPatients().size()>=media)
				.map(Doctor::getId)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		return doctors.values().stream()
				.sorted(Comparator.comparing(Doctor::getId).reversed())
				.map(Doctor::format)
				.collect(Collectors.toCollection(LinkedList::new))
				;
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
//		doctors.values().stream()
//				.collect(Collectors.groupingBy(
//						Doctor::getSpecializzazione,
//						new TreeMap<String, Integer>(),
//						Collectors.summingInt(d->d.getPatients().values().size())
//						))
//				;
		return null;
	}
	
}
