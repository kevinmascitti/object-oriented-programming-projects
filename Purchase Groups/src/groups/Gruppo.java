package groups;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Gruppo {

	private String name;
	private Product product;
	private Map<String, Cliente> clienti = new TreeMap<>();
	private Map<String, Supplier> fornitori = new TreeMap<>();
	private List<Bid> offerte = new LinkedList<>();

	public Gruppo(String name, Product product) {
		this.name=name;
		this.product=product;
	}

	public void addCliente(Cliente c) {
		if(c!=null)
			clienti.put(c.getName(), c);
	}

	public String getName() {
		return name;
	}
	
	public Map<String,Cliente> getCustomers(){
		return clienti;
	}

	public void addSupplier(Supplier su) {
		if(su!=null)
			fornitori.put(su.getName(), su);
	}

	public String getTypeProduct() {
		return product.getType();
	}

	public Map<String, Supplier> getSuppliers() {
		return fornitori;
	}

	public void addBid(Bid b) {
		if(b!=null)
			offerte.add(b);
	}
	
	public List<Bid> getBids(){
		return offerte;
	}

}
