package delivery;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Delivery {
	
	private int id = 1;
	private int code = 1;
	private Map<Integer, Customer> clienti = new LinkedHashMap<>();
	private List<Menu> menu = new LinkedList<>();
	private Map<Integer, Order> ordini = new LinkedHashMap<>();
    
    public static enum OrderStatus { NEW, CONFIRMED, PREPARATION, ON_DELIVERY, DELIVERED } 
    
    /**
     * Creates a new customer entry and returns the corresponding unique ID.
     * 
     * The ID for the first customer must be 1.
     * 
     * 
     * @param name name of the customer
     * @param address customer address
     * @param phone customer phone number
     * @param email customer email
     * @return unique customer ID (positive integer)
     */
    public int newCustomer(String name, String address, String phone, String email) throws DeliveryException {
        Long i = clienti.values().stream().filter(a->a.getEmail()==email).count();
    	if(i==0) {
        	Customer c = new Customer(id, name, address, phone, email);
        	clienti.put(id,c);
        	id++;
        	return id-1;
        }
        else {
        	throw new DeliveryException();
        }
    }
    
    /**
     * Returns a string description of the customer in the form:
     * <pre>
     * "NAME, ADDRESS, PHONE, EMAIL"
     * </pre>
     * 
     * @param customerId
     * @return customer description string
     */
    public String customerInfo(int customerId){
        if(clienti.containsKey(customerId))
        	return clienti.get(customerId).toString();
        return null;
    }
    
    /**
     * Returns the list of customers, sorted by name
     * 
     */
    public List<String> listCustomers(){
        return clienti.values().stream()
        		.map(a->a.toString())
        		.sorted()
        		.collect(Collectors.toList());
    }
    
    /**
     * Add a new item to the delivery service menu
     * 
     * @param description description of the item (e.g. "Pizza Margherita", "Bunet")
     * @param price price of the item (e.g. 5 EUR)
     * @param category category of the item (e.g. "Main dish", "Dessert")
     * @param prepTime estimate preparation time in minutes
     */
    public void newMenuItem(String description, double price, String category, int prepTime){
        Menu m = new Menu(description, price, category, prepTime);
        if(!menu.contains(m))
        	menu.add(m);
    }
    
    /**
     * Search for items matching the search string.
     * The items are sorted by category first and then by description.
     * 
     * The format of the items is:
     * <pre>
     * "[CATEGORY] DESCRIPTION : PRICE"
     * </pre>
     * 
     * @param search search string
     * @return list of matching items
     */
    public List<String> findItem(String search){
        return menu.stream()
        		.filter(a->a.getDescription().toLowerCase().contains(search.toLowerCase()))
        		.map(Menu::toString)
        		.collect(Collectors.toList());
    }
    
    public Menu findMenu(String search){
		Menu m;
    	try{
    		m = menu.stream()
        		.filter(a->a.getDescription().toLowerCase().contains(search.toLowerCase()))
        		.findFirst().get();
    	} catch (NoSuchElementException e) {
    		return null;
    	}
    	return m;
    }
    
    /**
     * Creates a new order for the given customer.
     * Returns the unique id of the order, a progressive
     * integer number starting at 1.
     * 
     * @param customerId
     * @return order id
     */
    public int newOrder(int customerId){
        Order o = new Order(code, clienti.get(customerId));
        ordini.put(code, o);
        clienti.get(customerId).ordini.put(code, o);
        code++;
        return code-1;
    }
    
    /**
     * Add a new item to the order with the given quantity.
     * 
     * If the same item is already present in the order is adds the
     * given quantity to the current quantity.
     * 
     * The method returns the final total quantity of the item in the order. 
     * 
     * The item is found through the search string as in {@link #findItem}.
     * If none or more than one item is matched, then an exception is thrown.
     * 
     * @param orderId the id of the order
     * @param search the item search string
     * @param qty the quantity of the item to be added
     * @return the final quantity of the item in the order
     * @throws DeliveryException in case of non unique match or invalid order ID
     */
    public int addItem(int orderId, String search, int qty) throws DeliveryException {
        if(search!=null && this.findItem(search).size()==1 && Integer.valueOf(orderId)!=null && orderId<code) {
        	Menu m = this.findMenu(search);
        	Order o = ordini.get(orderId);
        	if(o.menu.values().isEmpty() || !o.menu.containsKey(m)) {
        		o.menu.put(m, new Integer(qty));
        		return qty;
        	} else {
        		Integer i = o.menu.get(m);
        		i+=qty;
        		o.menu.remove(m);
        		o.menu.put(m, i);
        		return i;
        	}
        }
        else {
        	throw new DeliveryException();
        }
    }
    
    /**
     * Show the items of the order using the following format
     * <pre>
     * "DESCRIPTION, QUANTITY"
     * </pre>
     * 
     * @param orderId the order ID
     * @return the list of items in the order
     * @throws DeliveryException when the order ID in invalid
     */
    public List<String> showOrder(int orderId) throws DeliveryException {
        if(orderId>=code) {
        	throw new DeliveryException();
        }
        List<String> lista = new LinkedList<>();
        Map<Menu, Integer> mappa = ordini.values().stream()
        		.filter(a->a.getCode()==orderId)
        		.findFirst().orElse(null)
        		.menu;
        for (Map.Entry<Menu, Integer> entry : mappa.entrySet()) {
        	Menu k = entry.getKey();
        	Integer v = entry.getValue();
        	if(k!=null && v!=null)
        		lista.add(k.getDescription()+", "+v);
        }
        if(lista.size()>0)
        	return lista;
        return null;
    }
    
    /**
     * Retrieves the total amount of the order.
     * 
     * @param orderId the order ID
     * @return the total amount of the order
     * @throws DeliveryException when the order ID in invalid
     */
    public double totalOrder(int orderId) throws DeliveryException {
        if(orderId>=code || ordini.get(orderId)==null)
        	throw new DeliveryException();
    	Order o = ordini.get(orderId);
        double price = 0;
        for (Map.Entry<Menu, Integer> entry : o.menu.entrySet()) {
        	Menu k = entry.getKey();
        	Integer v = entry.getValue();
        	if(k!=null && v!=null)
        		price+= k.getPrice() * v;
        }
        return price;
    }
    
    /**
     * Retrieves the status of an order
     * 
     * @param orderId the order ID
     * @return the current status of the order
     * @throws DeliveryException when the id is invalid
     */
    public OrderStatus getStatus(int orderId) throws DeliveryException {
        if(orderId>=code || ordini.get(orderId)==null)
        	throw new DeliveryException();
        return ordini.get(orderId).state;
    }
    
    /**
     * Confirm the order. The status goes from {@code NEW} to {@code CONFIRMED}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>start-up delay (conventionally 5 min)
     * <li>preparation time (max of all item preparation time)
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code NEW}
     */
    public int confirm(int orderId) throws DeliveryException {
    	if(orderId>=code || ordini.get(orderId)==null || ordini.get(orderId).state!=OrderStatus.NEW)
        	throw new DeliveryException();
        ordini.get(orderId).state=OrderStatus.CONFIRMED;
        int prep = 0;
        for (Map.Entry<Menu, Integer> entry : ordini.get(orderId).menu.entrySet()) {
        	Menu k = entry.getKey();
        	Integer v = entry.getValue();
        	if(k!=null && v!=null)
        		prep+= k.getPrepTime() * v;
        }
        return 5+prep+15;
    }

    /**
     * Start the order preparation. The status goes from {@code CONFIRMED} to {@code PREPARATION}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>preparation time (max of all item preparation time)
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code CONFIRMED}
     */
    public int start(int orderId) throws DeliveryException {
    	if(orderId>=code || ordini.get(orderId)==null || ordini.get(orderId).state!=OrderStatus.CONFIRMED)
        	throw new DeliveryException();
        ordini.get(orderId).state=OrderStatus.PREPARATION;
        int prep = 0;
        for (Map.Entry<Menu, Integer> entry : ordini.get(orderId).menu.entrySet()) {
        	Menu k = entry.getKey();
        	Integer v = entry.getValue();
        	if(k!=null && v!=null)
        		prep+= k.getPrepTime() * v;
        }
        return prep+15;
    }

    /**
     * Begins the order delivery. The status goes from {@code PREPARATION} to {@code ON_DELIVERY}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code PREPARATION}
     */
    public int deliver(int orderId) throws DeliveryException {
    	if(orderId>=code || ordini.get(orderId)==null || ordini.get(orderId).state!=OrderStatus.PREPARATION)
        	throw new DeliveryException();
        ordini.get(orderId).state=OrderStatus.ON_DELIVERY;
        return 15;
    }
    
    /**
     * Complete the delivery. The status goes from {@code ON_DELIVERY} to {@code DELIVERED}
     * 
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code ON_DELIVERY}
     */
    public void complete(int orderId) throws DeliveryException {
    	if(orderId>=code || ordini.get(orderId)==null || ordini.get(orderId).state!=OrderStatus.ON_DELIVERY)
        	throw new DeliveryException();
        ordini.get(orderId).state=OrderStatus.DELIVERED;
    }
    
    /**
     * Retrieves the total amount for all orders of a customer.
     * 
     * @param customerId the customer ID
     * @return total amount
     */
    public double totalCustomer(int customerId){
        Customer c = clienti.get(customerId);
        if(c!=null) {
        	return	c.ordini.values().stream()
        			.filter(o->(o.state==OrderStatus.CONFIRMED ||
        						o.state==OrderStatus.ON_DELIVERY ||
        						o.state==OrderStatus.DELIVERED))
         			.mapToDouble(Order::getTotOrder).sum();
        }
        return 0;
    }
    
    /**
     * Computes the best customers by total amount of orders.
     *  
     * @return the classification
     */
    public SortedMap<Double,List<String>> bestCustomers(){
        SortedMap<Double,List<String>> mappa =
        		ordini.values().stream()
        		.filter(o->(o.state==OrderStatus.CONFIRMED ||
							o.state==OrderStatus.ON_DELIVERY ||
							o.state==OrderStatus.DELIVERED))
        		.collect(Collectors.groupingBy(
        				Order::getTotOrder,
        				TreeMap::new,
        				Collectors.mapping(a->a.getCustomerId().getName(), Collectors.toList())
        				));
        if(mappa.size()>0)
        	return mappa;
        return null;
    }

    /**
     * Computes the best items by total amount of orders.
     *  
     * @return the classification
     */
    public List<String> bestItems(){
    	return ordini.values().stream().
                filter(o->o.state!=OrderStatus.NEW).
                flatMap(o -> o.getItems().stream()).
                collect(Collectors.groupingBy(
                				e->e.getKey().getDescription(),
                				Collectors.summingDouble(e->e.getValue()*e.getKey().getPrice())
                        )).
                entrySet().stream().
                sorted(Comparator.comparing(e->-e.getValue())).
                map(r -> r.getKey() + ", " +  String.format("%.2f", r.getValue())).
                collect(Collectors.toList())
                ;
    }
    
    /**
     * Computes the most popular items by total quantity ordered.
     *  
     * @return the classification
     */
    public List<String> popularItems(){
    	 return ordini.values().stream().
                 filter(o->o.state!=OrderStatus.NEW).
                 flatMap(o -> o.getItems().stream()).
                 collect(Collectors.groupingBy(
                		 		e->e.getKey().getDescription(),
                                Collectors.summingInt(e->e.getValue())
                         )).
                 entrySet().stream().
                 sorted(Comparator.comparing(e->-e.getValue())).
                 map(r -> r.getKey() + ", " +  r.getValue()).
                 collect(Collectors.toList())
                 ;
    }

}
