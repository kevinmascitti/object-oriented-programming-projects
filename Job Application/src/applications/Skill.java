package applications;

import java.util.*;
import java.util.stream.Collectors;


public class Skill {

	String name;
	Map<String, Position> posizioni = new TreeMap<>();
	Map<String, Richiedente> richiedenti = new TreeMap<>();
	
	public Skill(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	public List<Position> getPositions() {
		return posizioni.values().stream()
				.sorted(Comparator.comparing(Position::getName))
				.collect(Collectors.toList());
	}

}