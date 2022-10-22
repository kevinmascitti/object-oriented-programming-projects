package warehouse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Supplier {

	private String code;
	private String name;
	private Map<String, Product> forniti = new TreeMap<>();
	private Map<String, Order> ordini = new TreeMap<>();

	public Supplier(String code, String name) {
		this.code=code;
		this.name=name;
	}

	public String getCodice(){
		return code;
	}

	public String getNome(){
		return name;
	}
	
	public void addOrder(Order order) {
		if(order!=null) {
			ordini.put(order.getCode(), order);
		}
	}
	
	public void newSupply(Product product){
		if(product!=null) {
			forniti.put(product.getCode(),product);
			product.addSupplier(this);
		}
	}
	
	public List<Product> supplies(){
		return forniti.values().stream()
				.sorted(Comparator.comparing(Product::getDescription))
				.collect(Collectors.toList());
	}
}
