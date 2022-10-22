package warehouse;

public class Order {

	private String code;
	private Product prod;
	private int quantity;
	private Supplier supp;
	private boolean delivered;

	public Order(String code, Product prod, int quantity, Supplier supp) {
		this.code=code;
		this.prod=prod;
		this.quantity=quantity;
		this.supp=supp;
		delivered=false;
	}

	public String getCode(){
		return code;
	}
	
	public boolean delivered(){
		return delivered;
	}
	
	public Product getProd() {
		return prod;
	}

	public int getQuantity() {
		return quantity;
	}

	public Supplier getSupp() {
		return supp;
	}
	
	public String getSuppName() {
		return supp.getNome();
	}

	public void setDelivered() throws MultipleDelivery {
		if(delivered==false) {
			delivered=true;
			prod.setQuantity(prod.getQuantity()+quantity);
			prod.increaseDelivered();
		}
		else
			throw new MultipleDelivery();
	}
	
	public String toString(){
		return "Order "+code+" for "+quantity+" of "+
				prod.getCode()+" : "+prod.getDescription()+" from "+supp.getNome();
	}
}
