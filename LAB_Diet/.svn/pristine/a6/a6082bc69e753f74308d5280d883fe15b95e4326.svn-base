package diet;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant in the take-away system
 *
 */
public class Restaurant {
	
	private String name;
	private Food food;
	private String[] times;
	private int numt = 0;

	private SortedMap<String,NutritionalElement> recipes = new TreeMap<>();
	private SortedMap<String,NutritionalElement> products = new TreeMap<>();
	private SortedMap<String,NutritionalElement> menues = new TreeMap<>();

	protected List<Order> orders = new LinkedList<>();
	
	/**
	 * Constructor for a new restaurant.
	 * 
	 * Materials and recipes are taken from
	 * the food object provided as argument.
	 * 
	 * @param name	unique name for the restaurant
	 * @param food	reference food object
	 */
	public Restaurant(String name, Food food) {
		this.name=name;
		this.food=food;
		this.recipes=food.recipesMAP();
		this.products=food.rawMaterialsMAP();
		this.products.putAll(food.productsMAP());
		times = new String[10];
	}
	
	/**
	 * gets the name of the restaurant
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Define opening hours.
	 * 
	 * The opening hours are considered in pairs.
	 * Each pair has the initial time and the final time
	 * of opening intervals.
	 * 
	 * for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00, 
	 * is thoud be called as {@code setHours("08:15", "14:00", "19:00", "00:00")}.
	 * 
	 * @param hm a list of opening hours
	 */
	public void setHours(String ... hm) {
		for(String s : hm) {
			if(s.length() != 5 && (s.length()!=4 || (s.length()==4&&s.charAt(1)!=':')) )
				System.err.println("Errore: sono stati inseriti degli orari di apertura errati!");
			String tmp = s;
			if(s.length()==4 && s.charAt(1)==':') {
				tmp = "0"+s;
			}
			times[numt] = tmp;
			numt++;
		}
		if(numt%2!=0)
			System.err.println("Errore: è stato inserito un numero di orari di apertura errato!");
	}
	
	public String[] getHours() {
		return times;
	}
	
	public Menu getMenu(String name) {
		return (Menu) menues.get(name);
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		for(NutritionalElement n : food.rawMaterials()) {
			this.products.putIfAbsent(n.getName(), n);
		}
		for(NutritionalElement n : food.products()) {
			this.products.putIfAbsent(n.getName(), n);
		}
		Menu m = new Menu(name, recipes, products);
		menues.put(name, m);
		return m;
	}

	/**
	 * Find all orders for this restaurant with 
	 * the given status.
	 * 
	 * The output is a string formatted as:
	 * <pre>
	 * Napoli, Judi Dench : (19:00):
	 * 	M6->1
	 * Napoli, Ralph Fiennes : (19:00):
	 * 	M1->2
	 * 	M6->1
	 * </pre>
	 * 
	 * The orders are sorted by name of restaurant, name of the user, and delivery time.
	 * 
	 * @param status the status of the searched orders
	 * 
	 * @return the description of orders satisfying the criterion
	 */
	public String ordersWithStatus(OrderStatus status) {
		StringBuffer s = new StringBuffer();
		orders.sort(orderComparator);
		for(Order o : orders) {
			if(o.getStatus().equals(status))
				s.append(o.toString());
		}
		return s.toString();
	}
	
	Comparator<Order> orderComparator = new Comparator<Order>() {
		@Override
		public int compare(Order a, Order b) {
			  if ( a.getRestaurant().getName().compareTo(b.getRestaurant().getName()) < 0 )
			    return -1;
			  if ( a.getRestaurant().getName().compareTo(b.getRestaurant().getName()) > 0 )
				    return 1;
			  if ( a.getUser().getFirstName().compareTo(b.getUser().getFirstName()) < 0 )
				  return -1;
			  if ( a.getUser().getFirstName().compareTo(b.getUser().getFirstName()) > 0 )
				  return 1;
			  if ( a.time.isBefore(b.time) )
				  return -1;
			  if ( a.time.isAfter(b.time) )
				  return 1;
			  return 0;
		}
	};
	
}
