package university;

import java.util.logging.Logger;

public class University /*throws UniversityException*/ {
	private String uName; //@param name name of the university
	private String rName; //@param first
	private String rSurname; //@param last

	protected Students[] studenti;	//Dichiaro il vettore
	static final int INITIAL_ID = 10000;
	static final int MAX_S = 1000;
	protected int n = INITIAL_ID;

	protected Courses[] insegnamenti;
	static final int INITIAL_CODE = 10;
	static final int MAX_CODE = 50;
	private int cod = INITIAL_CODE;
	
	/*
	 * private Collection<Student> studenti;
	 * 
	 * FINDSTUDENT
	 * for(Student s : studenti){
	 * 	if(s.getId() == id)
	 * 		return s
	 * }
	 * throw new UniversityException("Missing student ID: " + ID);
	 * 
	 * private List<Student> studenti;
	 * Posso accedere a una lista tramite un indice!
	 * Student s = include[id-INITIAL_ID];
	 * 
	 * FINDSTUDENT
	 * if(s==null){
	 *	throw new UniversityException("Missing student ID: " + ID);
	 *	return s;
	 *
	 * collezioneStudenti = new ArrayList<>(); //diamon operator
	 * listaStudenti = new LinkedList<>(); //diamond operator
	 * listaStudenti = new LinkedList<Student>();
	 * 
	 * private Map<Integer, Student> studenti;
	 * //accedo con la chiave vera
	 * 
	 * FINDSTUDENT
	 * Student s = mappaStudenti.get(id); 
	 * if(s==null){
	 *	throw new UniversityException("Missing student ID: " + ID);
	 *	return s;
	 */
	
	public University(String name){ //Constructor
		uName = name;
		studenti = new Students[MAX_S]; 	//Inizializzo il vettore
		insegnamenti = new Courses[MAX_CODE];
	}
	
	public String getName(){ //Getter for the name of the university
		
		if(uName != null) {
			return uName;
		}
		System.err.println("Errore nel nome dell'università.");
		return null;
	}
			 
	public void setRector(String first, String last){ //Defines the rector for the university
		
		if(first != null)
			rName = first;
		else {
			System.err.println("Errore nel nome inserito.");
			return;
		}
		if(last != null)
			rSurname = last;
		else {
			System.err.println("Errore nel cognome inserito.");
			return;
		}
		
//		System.out.println("Informazioni sul rettore inserite correttamente.");
	}
	
	public String getRector(){ //Retrieves the rector of the university
		
		if(rName != null && rSurname != null)
			return rName + " " + rSurname;
		return null;
	}
	
	public int enroll(String first, String last){ //Enroll a student in the university
		
		if(first != null && last != null) {
			if(n >= INITIAL_ID + MAX_S) {
				System.err.println("Hai raggiunto il numero massimo (1000) di studenti e non puoi più inserirne uno.");
				return -1;
			}
			studenti[n - INITIAL_ID] = new Students(first, last, n);
			return n++;
		}
		System.err.println("Errore inserimento studente.");
		return -1;
	}
	
	public String student(int id) /*throws UniversityException*/{ //Retrieves the information for a given student
		
		int i = id - INITIAL_ID;
		if(i >= 0 && i < MAX_S) {
			return String.valueOf(id) + " " + studenti[i].getsName() + " " + studenti[i].getsSurname();
		}
		else {
			System.err.println("Student ID non valido.");
			return null;
//			throw new UniversityException("Invalid studentID: "+id);
		}
	}
	
	public int activate(String title, String teacher){ //Activates a new course with the given teacher
		
		if(title != null && teacher != null) {
			if(cod >= INITIAL_CODE + MAX_CODE) {
				System.err.println("Hai raggiunto il numero massimo di insegnamenti (50) e non puoi più inserirne uno.");
				return -1;
			}
			insegnamenti[cod - INITIAL_CODE] = new Courses(title, teacher, cod);
			return cod++;
		}
		System.err.println("Errore inserimento insegnamento.");
		return -1;
	}
	
	public String course(int code){ //Retrieves the information for a given course
		int i = code - INITIAL_CODE;
		if(i >= 0 && i < INITIAL_CODE+MAX_CODE) {
			return insegnamenti[i].toString();
		}
		else {
			System.err.println("Errore codice corso inserito.");
			return null;
		}
	}
	
	public void register(int studentID, int courseCode){ //Register a student to attend a course
		if(studentID < 10000 || studentID > 11000 || courseCode < 10 || courseCode > 60) {
			System.err.println("Errore del numero di matricola o del codice del corso.");
			return;
		}
		else {
			Students s = studenti[studentID-INITIAL_ID];			
			Courses c = insegnamenti[courseCode-INITIAL_CODE];
			s.enroll(c);
			c.enroll(s);
		}
	}
	
	String temp = "";
	
	public String listAttendees(int courseCode){ //Retrieve a list of attendees
		if(courseCode >= 10 && courseCode < 60) {
			Courses c = insegnamenti[courseCode-INITIAL_CODE];
			return c.attendees();
		}
		else {
			System.err.println("Errore codice del corso.");
			return null;
		}
	}

	public String studyPlan(int studentID) { //Retrieves the study plan for a student
		if(studentID >= 10000 && studentID < 11000) {
			Students s = studenti[studentID-INITIAL_ID];
			return s.courses();
		}
		else {
			System.err.println("Errore codice di matricola.");
			return null;
		}
	}
}
