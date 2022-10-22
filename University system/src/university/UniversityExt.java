package university;

import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;

public class UniversityExt extends University {
	
	private int sumS=0, noexamS=0, sumC=0, noexamC=0;
	private int matr1, matr2, matr3;
	private double media1, media2, media3;
	
	private final static Logger logger = Logger.getLogger("University");
	private final static Logger logger4 = Logger.getLogger("Exam");
	
	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}
	
	public void exam(int studentID, int courseID, int grade) {
		if(grade < 0 || grade > 30)
			return;
		int ID = studentID - INITIAL_ID;
		int CODE = courseID - INITIAL_CODE;
		Students s = studenti[ID];
		Courses c = insegnamenti[CODE];
		s.addExam(c, grade);
		c.addExam(grade);
		logger4.info("Student "+studentID+" took an exam in course "+courseID+" with grade "+grade);
	}

	public String studentAvg(int studentId) {
		sumS=0; noexamS=0;
		int ID = studentId - INITIAL_ID;
		if(Float.isNaN(studenti[ID].avgGrade())) {
			return "Student "+studentId+" hasn't taken any exams";
		}
		for(int j=0; j<40; j++) {
			if(studenti[ID].voti[j] != 0) {
				sumS+=studenti[ID].voti[j];
				noexamS++;
			}
		}
		return "Student "+studentId+" : "+(double)sumS/noexamS;
	}
	
	public String courseAvg(int courseID) {
		sumC=0; noexamC=0;
		int CODE = courseID - INITIAL_CODE;
		if(Float.isNaN(insegnamenti[CODE].avgGrade())) {
			return "No student has taken the exam in "+insegnamenti[CODE].getNome();
		}
		return "The average for the course "+insegnamenti[CODE].getNome()+" is: "+insegnamenti[CODE].avgGrade();
	}
	
	/*
	
	public double min(double a, double b, double c) {
		if(a<=b&&a<=c)
			return a;
		if(b<=a&&b<=c)
			return b;
		if(c<=a&&c<=b)
			return c;
		return 0.0;
	}
	
	public String topThreeStudents() {
		double tmp = 0;
		
		for(Students s : studenti) {
			if(s != null) {
				this.studentAvg(s.getN());
				tmp = (double) sumS/noexamS + (double) noexamS*10/(MAX_CODE-INITIAL_CODE);
				if(tmp > min(media1,media2,media3)) {
					if(media1<=media2 && media1<=media3) {
						media1 = tmp;
						matr1 = s.getN();
					}
					else if(media2<=media1 && media2<=media3) {
						media2 = tmp;
						matr2 = s.getN();
					}
					else if(media3<=media1 && media3<=media2) {
						media3 = tmp;
						matr3 = s.getN();
					}
				}
			}
		}
		String stringa = "";
		stringa=stringa+studenti[matr1-INITIAL_ID].getsName()+" "+
				studenti[matr1-INITIAL_ID].getsSurname()+" : "+media1+"\n";
		if(media2!=0) {
			stringa=stringa+studenti[matr2-INITIAL_ID].getsName()+" "+
					studenti[matr2-INITIAL_ID].getsSurname()+" : "+media2+"\n";
			if(media3!=0) {
				stringa=stringa+studenti[matr3-INITIAL_ID].getsName()+" "+
						studenti[matr3-INITIAL_ID].getsSurname()+" : "+media3+"\n";
			}
		}
		return stringa;
	}
	*/
	public String topThreeStudents() {
		Students[] studTemp = Arrays.copyOf(studenti, n-INITIAL_ID);
		
		Arrays.sort(studTemp, new Comparator<Students>(){
			@Override
			public int compare(Students s1, Students s2) {
				return (int)Math.signum(s2.awardsPoints()-s1.awardsPoints());
			}
		});
		
		StringBuffer top3 = new StringBuffer();
		for(int i=0; i<3; i++)
			top3.append(studTemp[i].getsName()).append(" ").append(studTemp[i].getsSurname()).
				append(" : ").append(studTemp[i].awardsPoints()).append("\n");
		return top3.toString();
	}
	
	@Override
	public int enroll(String first, String last) {
		int studentId = super.enroll(first, last);
		logger.info("New student enrolled: "+studentId+", "+first+" "+last);
		return studentId;
	}
	
	@Override
	public int activate(String title, String teacher) {
		int code = super.activate(title, teacher);
		logger.info("New course activated: "+code+", "+title+" "+teacher);
		return code;
	}
	
	@Override
	public void register(int studentID, int courseCode) {
		super.register(studentID, courseCode);
		logger.info("Student "+studentID+" signed up for course "+courseCode);
	}
}
