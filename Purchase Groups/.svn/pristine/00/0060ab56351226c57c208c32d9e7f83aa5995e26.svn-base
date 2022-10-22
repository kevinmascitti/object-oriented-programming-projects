package groups;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Product {

	private String type;
	private Map<String,Supplier> fornitori = new TreeMap<>();
	private List<Bid> offerte = new LinkedList<>();
	private int massimo = 0;

	public Product(String productTypeName) {
		this.type=productTypeName;
	}
	
	public void addSupplier(Supplier s) {
		fornitori.putIfAbsent(s.getName(), s);
	}

	public String getType() {
		return type;
	}

	public Map<String,Supplier> getFornitori() {
		return fornitori;
	}

	public void addBid(Bid b) {
		if(b!=null) {
			offerte.add(b);
			if(b.getPrice()>massimo )
				massimo=b.getPrice();
		}
	}
	
	public int getMassimo() {
		return massimo;
	}
	
	public List<Bid> getBids() {
		return offerte;
	}


}
