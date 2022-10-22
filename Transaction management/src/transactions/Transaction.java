package transactions;

public class Transaction {

	String transactionId;
	String carrierName;
	Request request;
	Offer offer;
	int score;

	public Transaction(String transactionId, String carrierName, Request request, Offer offer) {
		this.transactionId=transactionId;
		this.carrierName=carrierName;
		this.request=request;
		this.offer=offer;
		this.score=0;
	}
	
	public String getPlace() {
		return request.placeName;
	}

}
