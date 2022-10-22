package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Student {

	String id;
	double gradeAverage;
	List<Course> richieste = new ArrayList<>();
	Course enrolledIn;

	public Student(String id, double gradeAverage) {
		this.id=id;
		this.gradeAverage=gradeAverage;
	}

	public int choiceNo(String c) {
		if(c.length()>0 && richieste.stream().map(a->a.name).collect(Collectors.toList()).contains(c))
			return richieste.stream().map(a->a.name).collect(Collectors.toList()).indexOf(c)+1;
		return -1;
	}
	
	public double getGradeAverage() {
		return gradeAverage;
	}

	public List<Course> getPreferences() {
		return richieste;
	}

	public boolean isEnrolled() {
        return enrolledIn != null;
    }
	
	public boolean isEnrolled(int choice) {
        return isEnrolled() && choice <= richieste.size() && enrolledIn == richieste.get(choice-1);
    }

	public String getId() {
		return id;
	}

}
