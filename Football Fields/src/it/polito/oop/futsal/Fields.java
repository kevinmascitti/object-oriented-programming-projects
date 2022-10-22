package it.polito.oop.futsal;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {
    
    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }
    }

    private int codeF = 1;
	private Map<Integer, Field> impianto = new TreeMap<>();
	private int openhh;
	private int openmm;
	private int closehh;
	private int closemm;
	private int codeS = 1;
	private Map<Integer, Associate> soci = new TreeMap<>();
    
    public void defineFields(Features... features) throws FutsalException {
        if(features.length<0)
        	throw new FutsalException();
        for(Features f : features) {
        	if( (f.indoor==false && f.ac==true) || (f.indoor==false && f.heating==true) )
        		throw new FutsalException();
        }
        for(Features f : features) {
	        impianto.put(codeF, new Field(codeF, f));
	        codeF++;
        }
    }
    
    public long countFields() {
        return impianto.size();
    }

    public long countIndoor() {
        return impianto.values().stream()
        		.filter(a->a.features.indoor==true)
        		.count();
    }
    
    public String getOpeningTime() {
    	return openhh+":"+openmm;
    }
    
    public void setOpeningTime(String time) {
    	if(time.length()>0) {
	    	this.openhh=Integer.parseInt(time.split(":")[0]);
	    	this.openmm=Integer.parseInt(time.split(":")[1]);
    	}
    }
    
    public String getClosingTime() {
    	return closehh+":"+closemm;
    }
    
    public void setClosingTime(String time) {
    	if(time.length()>0) {
    		this.closehh=Integer.parseInt(time.split(":")[0]);
	    	this.closemm=Integer.parseInt(time.split(":")[1]);
    	}
    }

    public int newAssociate(String first, String last, String mobile) {
        if(first.length()>0 && last.length()>0 && mobile.length()>0) {
        	Associate s = new Associate(codeS, first, last, mobile);
        	soci.put(codeS, s);
        	codeS++;
        	return codeS-1;
        }
        return -1;
    }
    
    public String getFirst(int partyId) throws FutsalException {
        if(!soci.containsKey(partyId))
        	throw new FutsalException();
        return soci.get(partyId).first;
    }
    
    public String getLast(int associate) throws FutsalException {
    	if(!soci.containsKey(associate))
        	throw new FutsalException();
        return soci.get(associate).last;
    }
    
    public String getPhone(int associate) throws FutsalException {
    	if(!soci.containsKey(associate))
        	throw new FutsalException();
        return soci.get(associate).mobile;
    }
    
    public int countAssociates() {
        return soci.size();
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
    	if(field>0 && associate>0 && time.length()>0
    			&& impianto.containsKey(field) 
    			&& soci.containsKey(associate)
    			&& time.compareTo(this.getOpeningTime())>=0
    			&& time.compareTo(this.getClosingTime())<0
    			&& Integer.parseInt(time.split(":")[1])==this.openmm) {
    		impianto.get(field).prenotazioni.put(time, soci.get(associate));
    		soci.get(associate).prenotazioni.put(time, impianto.get(field));
    	}
    	else
    		throw new FutsalException();
    }

    public boolean isBooked(int field, String time) {
    	if(impianto.containsKey(field) && time.length()>0) {
	    	String[] inizio = time.split(":");
	    	String tempoInizio;
	    	if(Integer.parseInt(inizio[1])<this.openmm) {
	    		tempoInizio=Integer.toString((Integer.parseInt(inizio[0])-1)+this.openmm);
	    	}
	    	else {
	    		tempoInizio=inizio[0]+":"+this.openmm;
	    	}
	       	return impianto.get(field).prenotazioni.keySet().contains(tempoInizio);
    	}
    	return false;
    }
    

    public int getOccupation(int field) {
        if(impianto.containsKey(field))
        	return impianto.get(field).prenotazioni.size();
        return -1;
    }
    
    
    public List<FieldOption> findOptions(String time, Features required){
        if(time.length()>0 && required!=null) {
        	List<FieldOption> lista = new LinkedList<>();
        	for(Field f : impianto.values()) {
        		if(!this.isBooked(f.code, time) && f.possiede(required))
        			lista.add(f);
        	}
        	if(lista.size()>0)
        		return lista.stream()
        				.sorted(Comparator.comparing(FieldOption::getOccupation).reversed().thenComparing(FieldOption::getField))
        				.collect(Collectors.toList());
        	return null;
        }
        return null;
    }
    
    public long countServedAssociates() {
        return soci.values().stream()
        		.filter(a->a.prenotazioni.size()>0)
        		.count();
    }
    
    public double occupation() {
    	int blocchi = closehh-openhh-1;
    	blocchi+= closemm<openmm? 0:1;
    	int prenotazioni = impianto.values().stream()
    			.flatMap(a->a.prenotazioni.keySet().stream())
    			.collect(Collectors.toList())
    			.size();
        return (double) prenotazioni/blocchi;
    }
    
    public Map<Integer,Long> fieldTurnover() {
        Map<Integer, Long> mappa = new TreeMap<>();
        for(Field f : impianto.values()) {
        	mappa.put(f.code, (long) f.prenotazioni.size());
        }
        return mappa;
    }
    
}
