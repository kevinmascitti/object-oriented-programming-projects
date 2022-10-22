package delivery;

public class Menu {

	private String description;
	private double price;
	private String category;
	private int prepTime;

	public Menu(String description, double price, String category, int prepTime) {
		this.description=description;
		this.price=price;
		this.category=category;
		this.prepTime=prepTime;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getCategory() {
		return category;
	}

	public int getPrepTime() {
		return prepTime;
	}
	
	@Override
	public String toString() {
		return "["+category+"] "+description+" : "+String.format("%.2f", price) ;
	}

}
