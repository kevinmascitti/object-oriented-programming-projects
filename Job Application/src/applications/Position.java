package applications;

import java.util.*;
import java.util.stream.Collectors;

public class Position {

	String name;
	Map<String, Skill> competenze = new TreeMap<>();
	List<Richiedente> candidati = new LinkedList<>();
	Richiedente vincitore;

	public Position(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	public List<String> getApplicants() {
		if(candidati.size()>0)
			return candidati.stream()
					.map(Richiedente::getName)
					.distinct()
					.sorted()
					.collect(Collectors.toList());
		return null;
	}
	
	public String getWinner() {
		if(vincitore!=null)
			return vincitore.name; 
		return null;
	}
}