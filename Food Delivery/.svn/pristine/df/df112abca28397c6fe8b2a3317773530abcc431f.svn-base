package delivery;

import java.util.Map;
import java.util.TreeMap;

public class Customer {

	private int id;
	private String name;
	private String address;
	private String phone;
	private String email;
	Map<Integer, Order> ordini = new TreeMap<>();

	public Customer(int id, String name, String address, String phone, String email) {
		this.id=id;
		this.name=name;
		this.address=address;
		this.phone=phone;
		this.email=email;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}
	
	@Override
	public String toString() {
		return name+", "+address+", "+phone+", "+email;
	}

}
