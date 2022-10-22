package diet;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Represents an order in the take-away system
 */
public class Order {
	
	private User u;
	private Restaurant r;
	protected LocalTime time;
	
	private class Menuquant {
		Menu m;
		int q;
		public Menuquant(Menu menu, int quantita) {
			m = menu;
			q = quantita;
		}
	}
	
	private List<Menuquant> menuquants = new ArrayList<>();
	private int tot;
	
	
	public Order(User u, Restaurant r, LocalTime time) {
		this.u=u;
		this.r=r;
		this.time=time;
	}
	
	public User getUser() {
		return u;
	}

	public Restaurant getRestaurant() {
		return r;
	}

	/**
	 * Defines the possible order status
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED;
	}
	/**
	 * Defines the possible valid payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD;
	}
	
	private OrderStatus status = OrderStatus.ORDERED;
	private PaymentMethod payment = PaymentMethod.CASH;
		
	/**
	 * Total order price
	 * @return order price
	 */
	public double Price() {
		return -1.0;
	}
	
	/**
	 * define payment method
	 * 
	 * @param method payment method
	 */
	public void setPaymentMethod(PaymentMethod method) {
		payment = method;
	}
	
	/**
	 * get payment method
	 * 
	 * @return payment method
	 */
	public PaymentMethod getPaymentMethod() {
		return payment;
	}
	
	/**
	 * change order status
	 * @param newStatus order status
	 */
	public void setStatus(OrderStatus newStatus) {
		status = newStatus;
	}
	
	/**
	 * get current order status
	 * @return order status
	 */
	public OrderStatus getStatus(){
		return status;
	}
	
	/**
	 * Add a new menu with the relative order to the order.
	 * The menu must be defined in the {@link Food} object
	 * associated the restaurant that created the order.
	 * 
	 * @param menu     name of the menu
	 * @param quantity quantity of the menu
	 * @return this order to enable method chaining
	 */
	public Order addMenus(String menu, int quantity) {
		Menu m = r.getMenu(menu);
		Menuquant menuquant = new Menuquant(m, quantity);
		int found = 0;
		for(Menuquant mq : menuquants) {
			if((mq.m).equals(m)) {
				mq.q = quantity;
				found=1;
				break;
			}
		}
		if(found==0)
			menuquants.add(menuquant);
		
		return this;
	}
	
	/**
	 * Converts to a string as:
	 * <pre>
	 * RESTAURANT_NAME, USER_LAST_NAME USER_FIRST_NAME : DELIVERY(HH:MM):
	 * 	MENU_NAME_1->MENU_QUANTITY_1
	 * 	...
	 * 	MENU_NAME_k->MENU_QUANTITY_k
	 * </pre>
	 */
	@Override
	public String toString() {
		menuquants.sort( (Menuquant a, Menuquant b)->(a.m.getName()).compareTo(b.m.getName()) );
		StringBuffer s = new StringBuffer();
		s.append(r.getName()+", "+u.getFirstName()+" "+u.getLastName()+" : ("+time.toString()+"):\n");
		for(Menuquant mq : menuquants) {
			s.append("\t"+(mq.m).getName()+"->"+(mq.q)+"\n");
		}
		return s.toString();
	}
	
}
