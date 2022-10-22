package applications;

import java.util.*;
import java.util.stream.Collectors;

public class HandleApplications {

	private Map<String, Skill> competenze = new TreeMap<>();
	private Map<String, Position> posizioni = new TreeMap<>();
	private Map<String, Richiedente> richiedenti = new TreeMap<>();
	private List<Capability> capacitatot = new LinkedList<>();
	
	public void addSkills(String... names) throws ApplicationException {
		if(names.length>0) {
			for(String s : names) {
				if(competenze.containsKey(s))
					throw new ApplicationException();
				Skill sk = new Skill(s);
				competenze.put(s, sk);
			}
		}
		else throw new ApplicationException();
	}
	public void addPosition(String name, String... skillNames) throws ApplicationException {
		if( name.length()>0 && skillNames.length>0 && !posizioni.containsKey(name) ) {
			Position p = new Position(name);
			posizioni.put(name, p);
			for(String s : skillNames) {
				if(s.length()>0 && competenze.containsKey(s)) {
					Skill sk = competenze.get(s);
					sk.posizioni.put(p.name, p);
					posizioni.get(name).competenze.put(sk.name, sk);
				}
				else
					throw new ApplicationException();
			}
		}
		else
			throw new ApplicationException();
		
	}
	public Skill getSkill(String name) {
		if(competenze.containsKey(name))
			return competenze.get(name);
		return null;
	}
	public Position getPosition(String name) {
		if(posizioni.containsKey(name))
			return posizioni.get(name);
		return null;
	}
	
	public void addApplicant(String name, String capabilities) throws ApplicationException {
		if(!richiedenti.containsKey(name))
			throw new ApplicationException();
		Richiedente r = new Richiedente(name);
		richiedenti.put(name,  r);
		String[] capacita = capabilities.split(",");
		for(String s : capacita) {
			if(s.length()>0 && s.contains(":")) {
				String[] valori = s.split(":");
				if( !competenze.containsKey(valori[0]) || 
						Integer.parseInt(valori[1])<1 ||
						Integer.parseInt(valori[1])>10 )
					throw new ApplicationException();
				Skill sk = competenze.get(valori[0]);
				r.competenze.add(new Capability(r, sk, Integer.parseInt(valori[1])));
				sk.richiedenti.put(name, r);
				Capability c = new Capability(r, sk, Integer.parseInt(valori[1]));
				capacitatot.add(c);
			}
		}
	}
	public String getCapabilities(String applicantName) throws ApplicationException {
		if(applicantName.length()>0 && richiedenti.containsKey(applicantName)) {
			if( richiedenti.get(applicantName).competenze.size()==0 )
				return "";
			else {
				return richiedenti.get(applicantName).competenze.stream()
						.sorted(Comparator.comparing(c -> c.getSkill().getName()))
						.map( (Capability c) -> c.getSkill().getName()+":"+c.getPunti() )
						.collect(Collectors.joining(","));
			}
		}
		else
			throw new ApplicationException();
	}
	
	public void enterApplication(String applicantName, String positionName) throws ApplicationException {
		if( applicantName.length()>0 && positionName.length()>0 &&
				richiedenti.containsKey(applicantName) && posizioni.containsKey(positionName) ) {
			Richiedente r = richiedenti.get(applicantName);
			Position p = posizioni.get(positionName);
			if(r.posizione!=null ||
				!r.competenze.stream().map(a->a.getSkill()).collect(Collectors.toList()).containsAll(p.competenze.values()))
				throw new ApplicationException();
			r.posizione=p;
			p.candidati.add(r);
		}		
		else
			throw new ApplicationException();
			
	}
	
	public int setWinner(String applicantName, String positionName) throws ApplicationException {
		if( applicantName.length()>0 && positionName.length()>0
				&& richiedenti.containsKey(applicantName) && posizioni.containsKey(positionName)
				&& richiedenti.get(applicantName).posizione.equals(posizioni.get(positionName))
				&& posizioni.get(positionName).vincitore==null 
				&& relazione(posizioni.get(positionName), richiedenti.get(applicantName))==true) {
			posizioni.get(positionName).vincitore=richiedenti.get(applicantName);
			return richiedenti.get(applicantName).competenze.size();
		}
		else
			throw new ApplicationException();
	}
	
	private boolean relazione(Position position, Richiedente richiedente) {
		int somma=0;
		for (Skill skill : position.competenze.values())
			somma += richiedente.getCapability(skill).punti;
		if(somma>position.competenze.size()*6) 
			return true;
		return false;
	}
	public SortedMap<String, Long> skill_nApplicants() {
		return richiedenti.values().stream()
				.flatMap(applicant -> applicant.getSkills().stream())
				.collect(Collectors.groupingBy(Skill::getName,
						TreeMap::new,
						Collectors.counting()
						));
	}
	public String maxPosition() {
		int max = -1;
		String maxnome = "";
		for(Position p : posizioni.values()) {
			if(p.candidati.size()>max) {
				max=p.candidati.size();
				maxnome=p.name;
			}
		}
		return maxnome;
	}
}

