package warehouse;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Warehouse {
	
	private Map<String, Product> prodotti = new TreeMap<>();
	private Map<String, Supplier> fornitori = new TreeMap<>();
	private int code = 1;
	private Map<String, Order> ordini = new TreeMap<>();

	public Product newProduct(String code, String description){
		Product p = new Product(code,description);
		prodotti.put(code,p);
		return p;
	}
	
	public Collection<Product> products(){
		if(prodotti.values().size()>0)
			return prodotti.values();
		return null;
	}

	public Product findProduct(String code){
		if(prodotti.containsKey(code)) 
			return prodotti.get(code);
		return null;
	}

	public Supplier newSupplier(String code, String name){
		Supplier s = new Supplier(code,name);
		fornitori.put(code,s);
		return s;
	}
	
	public Supplier findSupplier(String code){
		if(fornitori.containsKey(code)) 
			return fornitori.get(code);
		return null;
	}

	public Order issueOrder(Product prod, int quantity, Supplier supp)
		throws InvalidSupplier {
		if(prod.suppliers().contains(supp)) {
			Order o = new Order("ORD"+code, prod, quantity, supp);
			ordini.put("ORD"+code, o);
			prod.addOrder(o);
			supp.addOrder(o);
			code++;
			return o;
		}
		else
			throw new InvalidSupplier();
	}

	public Order findOrder(String code){
		if(ordini.containsKey(code))
			return ordini.get(code);
		return null;
	}
	
	public List<Order> pendingOrders(){
		List<Order> lista = ordini.values().stream()
				.filter(a->a.delivered()==false)
				.sorted(Comparator.comparing(a->((Order)a).getProd().getCode()))
				.collect(Collectors.toList());
		if(lista.size()>0)
			return lista;
		else 
			return null;
	}

	public Map<String,List<Order>> ordersByProduct(){
	    Map<String,List<Order>> mappa = ordini.values().stream()
	    		.collect(Collectors.groupingBy(
	    				o->o.getProd().getCode()
	    				));
	    if(mappa.size()>0)
	    	return mappa;
	    return null;
	}
	
	public Map<String,Long> orderNBySupplier(){
	    Map<String, Long> mappa = ordini.values().stream()
	    		.filter(a->a.delivered()==true)
	    		.collect(Collectors.groupingBy
	    				(Order::getSuppName, 
	    						TreeMap::new, 
	    						Collectors.counting())
	    				);
	    if(mappa.size()>0)
	    	return mappa;
	    return null;
	}
	
	public List<String> countDeliveredByProduct(){
		List<String> lista = prodotti.values().stream()
				.sorted(Comparator.comparing(Product::getDelivered).reversed())
				.map(a->a.getCode()+" - "+a.getDelivered())
				.collect(Collectors.toList())
				;
				
		if(lista.size()>0)
	    	return lista;
	    return null;
	}
}
