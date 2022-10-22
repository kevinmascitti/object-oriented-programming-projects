package groups;

import java.util.LinkedList;
import java.util.List;

public class Bid {

	private Gruppo gruppo;
	private Supplier supplier;
	private int price;
	private int voti = 0;
	private List<Cliente> clienti = new LinkedList<>();

	public Bid(Gruppo gruppo, Supplier supplier, int price) {
		this.gruppo=gruppo;
		this.supplier=supplier;
		this.price=price;
	}

	public Gruppo getGruppo() {
		return gruppo;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public int getPrice() {
		return price;
	}
	
	public String getTypeProd() {
		return gruppo.getTypeProduct();
	}
	
	public void increaseVotes() {
		voti++;
	}
	
	public int getVotes() {
		return voti;
	}

	public void addCustomer(Cliente cliente) {
		if(cliente!=null)
			clienti .add(cliente);
	}

}
