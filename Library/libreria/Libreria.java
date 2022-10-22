package libreria;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Libreria {
	
	private Map<String, Editore> editori = new TreeMap<>();
	private List<Libro> libri = new ArrayList<>();
	private List<Ordine> ordini = new ArrayList<>();
	private int codice = 1;
	static List<String> data;

    public Editore creaEditore(String nome, int tempoConsegna, String email){
    	Editore e = new Editore(nome, tempoConsegna, email);
    	editori.put(nome, e);
        return e;
    }

    public Editore getEditore(String nome){
        return editori.get(nome);
    }

    public Collection<Editore> getEditori(){
        return editori.values();
    }

    public Libro creaLibro(String titolo, String autore, int anno, double prezzo, String nomeEditore)
    			throws EditoreInesistente {
    	Editore e;
    	if(editori.containsKey(nomeEditore)) {
    		e = editori.get(nomeEditore);
	    	Libro l = new Libro(this, titolo, autore, anno, prezzo, e);
	    	libri.add(l);
	    	return l;
    	} else {
    		throw new EditoreInesistente("Errore: editore mancante.");
    	}
    }
    
    public Libro getLibro(String autore, String titolo){
        if(autore==null && titolo==null) {
        	System.out.println("Errore: libro non trovato");
        	return null;
        }
        if(autore==null) {
        	try {
        	 return libri.stream()
        		.filter(p->p.getTitolo().equals(titolo))
        		.distinct()
        		.findAny()
        		.get();
        	} catch (NoSuchElementException e) {
        		return null;
        	}
        }
        if(titolo==null) {
        	try {
           	 return libri.stream()
           		.filter(p->p.getAutore().equals(autore))
           		.distinct()
           		.findAny()
           		.get();
           	} catch (NoSuchElementException e) {
           		return null;
           	}
        }
        for(Libro l : libri)
        	if(l.getAutore().equals(autore) && l.getTitolo().equals(titolo))
        		return l;
        return null;
    }
    
    public Collection<Libro> getClassificaSettimana(final int settimana){
        return libri.stream()
        		.sorted(Comparator.comparing(l->((Libro)l).getVenditeS(settimana)).reversed())
        		.collect(Collectors.toCollection(LinkedList::new));
    }
    
    public Collection<Libro> getClassificaMese(final int mese){
        return libri.stream()
        		.sorted(Comparator.comparing(l->((Libro)l).getVenditeM(mese)).reversed())
        		.collect(Collectors.toCollection(LinkedList::new));
    }
    
    public Collection<Ordine> getOrdini(){
        return ordini;
    }
    
    public Ordine creaOrdine(Libro libro, Editore editore, int quantitaRiordino) {
    	Ordine o = new Ordine(libro, editore, quantitaRiordino, codice);
    	codice++;
    	ordini.add(o);
    	return o;
    }
    
    public void ordineRicevuto(int numOrdine){
    	Ordine o = ordini.get(numOrdine-1);
    	o.setConsegnato();
    	o.getLibro().setQuantita(o.getLibro().getQuantita()+o.getQuantita());
    }
    
    public void leggi(String file) throws EditoreInesistente{
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			data = in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		String regExpE = "\\s*(E)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*";
		String regExpL = "\\s*(L)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*;\\s*(\\d+)\\s*;\\s*(\\d+)\\s*;\\s*(\\w+)\\s*";
		Pattern paE = Pattern.compile(regExpE);
		Pattern paL = Pattern.compile(regExpL);
		Matcher m;
		for(String s : data) {
			m = paE.matcher(s);
			if( m.matches() ) {
				if( m.group(1).equals("E") ) {
					Editore e = new Editore(m.group(2), Integer.parseInt(m.group(3)), m.group(4));
					editori.put(m.group(2), e);
				}
				
			}
			else {
				m = paL.matcher(s);
				if(m.matches()) {
					if( m.group(1).equals("L") ) {
						Editore e = getEditore(m.group(6));
						Libro l = new Libro(this, m.group(2), m.group(3), Integer.parseInt(m.group(4)), Double.parseDouble(m.group(5)), e);
						l.setQuantita(Integer.parseInt(m.group(7)));
						libri.add(l);
					}
				}
			}
		}
    }
}
