package applications;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Richiedente {

	String name;
	List<Capability> competenze = new LinkedList<>();
	Position posizione;

	public Richiedente(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Skill> getSkills(){
		return competenze.stream().map(a->a.competenza).distinct().collect(Collectors.toList());
	}

	public Capability getCapability(Skill skill) {
		for(Capability c : competenze) {
			if(c!=null && c.competenza.equals(skill))
				return c;
		}
		return null;
	}
	

}
