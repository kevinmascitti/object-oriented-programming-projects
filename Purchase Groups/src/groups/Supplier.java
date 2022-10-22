package groups;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Supplier {
	
	private String name;
	private Map<String, Product> prodotti = new TreeMap<>();
	private Map<String, Gruppo> gruppi = new TreeMap<>();
	private List<Bid> offerte = new LinkedList<>();

	public Supplier(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

	public Map<String,Product> getProdotti() {
		return prodotti;
	}

	public void addProduct(Product p) {
		if(p!=null)
			prodotti.put(p.getType(), p);
	}

	public void addGruppo(Gruppo g) {
		if(g!=null)
			gruppi .put(g.getName(), g);
	}

	public void addBid(Bid b) {
		if(b!=null)
			offerte.add(b);
	}

	public Map<String, Gruppo> getGruppi() {
		return gruppi;
	}
	
	public List<Bid> getBids(){
		return offerte;
	}

}
