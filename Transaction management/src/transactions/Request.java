package transactions;

import java.util.Map;
import java.util.TreeMap;

public class Request {

	String requestId;
	String placeName;
	String productId;
	
	Map<String, Transaction> transazioni = new TreeMap<>();

	public Request(String requestId, String placeName, String productId) {
		this.requestId=requestId;
		this.placeName=placeName;
		this.productId=productId;
	}

}
