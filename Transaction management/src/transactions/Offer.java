package transactions;

import java.util.Map;
import java.util.TreeMap;

public class Offer {

	String offerId;
	String placeName;
	String productId;
	
	Map<String, Transaction> transazioni = new TreeMap<>();

	public Offer(String offerId, String placeName, String productId) {
		this.offerId=offerId;
		this.placeName=placeName;
		this.productId=productId;
	}

}
