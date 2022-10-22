package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Course {

	String name;
	int availablePositions;
	List<Student> studenti = new ArrayList<>();

	public Course(String name, int availablePositions) {
		this.name=name;
		this.availablePositions=availablePositions;
	}
	
	public String getName() {
        return name;
    }
	
	public boolean hasRoom() {
        return studenti.size()<availablePositions;
    }
	
	public List<String> getEnrolled() {
        return studenti.stream().map(a->a.id).collect(Collectors.toList());
    }

}
