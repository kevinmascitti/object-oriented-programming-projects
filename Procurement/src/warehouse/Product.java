package warehouse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Product {

	
	private String code;
	private String description;
	private int quantity;
	private Map<String,Supplier> fornitori = new TreeMap<>();
	private Map<String, Order> ordini = new TreeMap<>();
	private int delivered = 0;

	public Product(String code, String description) {
		this.code=code;
		this.description=description;
	}

	public String getCode(){
		return code;
	}

	public String getDescription(){
		return description;
	}
	
	public void setQuantity(int quantity){
		this.quantity=quantity;
	}

	public void decreaseQuantity(){
		quantity--;
	}

	public int getQuantity(){
		return quantity;
	}
	
	public void addSupplier(Supplier supplier) {
		if(supplier!=null)
			fornitori.put(supplier.getCodice(),supplier);
	}
	
	public void addOrder(Order order) {
		if(order!=null) {
			ordini.put(order.getCode(), order);
		}
	}

	public List<Supplier> suppliers(){
		return fornitori.values().stream()
				.sorted(Comparator.comparing(Supplier::getNome))
				.collect(Collectors.toList());
	}

	public List<Order> pendingOrders(){
		List<Order> lista = ordini.values().stream()
				.filter(a->a.delivered()==false)
				.sorted(Comparator.comparing(Order::getQuantity).reversed())
				.collect(Collectors.toList());
		if(lista.size()>0)
			return lista;
		return null;
	}

	public void increaseDelivered() {
		delivered++;
	}
	
	public int getDelivered() {
		return delivered;
	}

}
