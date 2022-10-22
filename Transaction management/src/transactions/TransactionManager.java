package transactions;
import java.util.*;
import java.util.stream.Collectors;

//import static java.util.stream.Collectors.*;
//import static java.util.Comparator.*;

public class TransactionManager {
	
	private Map<String, List<String>> regioni = new TreeMap<>();
	private Map<String, List<String>> trasportatori = new TreeMap<>();
	private Map<String, Request> richieste = new TreeMap<>();
	private Map<String, Offer> offerte = new TreeMap<>();
	private Map<String, Transaction> transazioni = new TreeMap<>();
	
//R1
	public List<String> addRegion(String regionName, String... placeNames) { 
		if(placeNames.length>0 && regionName!=null && !regioni.containsKey(regionName)) {
			regioni.put(regionName, new LinkedList<>());
			for(String s : placeNames) {
				if(s!=null 
						&& !regioni.values().stream()
						.flatMap(a->a.stream())
						.collect(Collectors.toList())
						.contains(s) )
					regioni.get(regionName).add(s);
			}
			return regioni.get(regionName).stream().sorted().collect(Collectors.toList());
		}
		return null;
	}
	
	public List<String> addCarrier(String carrierName, String... regionNames) { 
		if(regionNames.length>0 && carrierName!=null && !trasportatori.containsKey(carrierName)) {
			trasportatori.put(carrierName, new LinkedList<>());
			for(String s : regionNames) {
				if(s!=null 
						&& !trasportatori.get(carrierName).contains(s)
						&& regioni.keySet()
						.contains(s))
					trasportatori.get(carrierName).add(s);
			}
			return trasportatori.get(carrierName).stream().sorted().collect(Collectors.toList());
		}
		return null;
	}
	
	public List<String> getCarriersForRegion(String regionName) { 
		return trasportatori.entrySet().stream()
				.filter(t->t.getValue().contains(regionName))
				.map(a->a.getKey())
				.sorted()
				.collect(Collectors.toList());
	}
	
//R2
	public void addRequest(String requestId, String placeName, String productId) 
			throws TMException {
		Boolean ok = false;
		for(List<String> list : regioni.values()) {
			if(list.contains(placeName)) {
				ok=true;
				break;
			}
		}
		if(ok==true && !richieste.containsKey(requestId)) {
			Request r = new Request(requestId, placeName, productId);
			richieste.put(requestId, r);
		}
		else {
			throw new TMException();
		}
	}
	
	public void addOffer(String offerId, String placeName, String productId) 
			throws TMException {
		Boolean ok = false;
		for(List<String> list : regioni.values()) {
			if(list.contains(placeName)) {
				ok=true;
				break;
			}
		}
		if(ok==true && !offerte.containsKey(offerId)) {
			Offer o = new Offer(offerId, placeName, productId);
			offerte .put(offerId, o);
		}
		else {
			throw new TMException();
		}
	}
	

//R3
	public void addTransaction(String transactionId, String carrierName, String requestId, String offerId) 
			throws TMException {
		if(transactionId!=null && carrierName!=null && requestId!=null && offerId!=null
				&& !transazioni.values().stream().map(a->a.request.requestId).collect(Collectors.toList()).contains(requestId)
				&& !transazioni.values().stream().map(a->a.offer.offerId).collect(Collectors.toList()).contains(offerId)
				&& richieste.containsKey(requestId) && offerte.containsKey(offerId)
				&& richieste.get(requestId).productId.equals(offerte.get(offerId).productId)
				&& trasportatori.containsKey(carrierName)
				&& trasportatori.get(carrierName).contains(this.getRegion(richieste.get(requestId).placeName))
				&& trasportatori.get(carrierName).contains(this.getRegion(offerte.get(offerId).placeName))
				) {

			Request request = richieste.get(requestId);
			Offer offer = offerte.get(offerId);
			Transaction t = new Transaction(transactionId, carrierName, request, offer);
			transazioni.put(transactionId, t);
			request.transazioni.put(transactionId, t);
			offer.transazioni.put(transactionId, t);
		}
		else
			throw new TMException();
	}
	
	private String getRegion(String placeName) {
		for(Map.Entry<String, List<String>> s : regioni.entrySet()) {
			if(s.getKey()!=null && s.getValue()!=null && s.getValue().contains(placeName)) {
				return s.getKey();
			}
		}
		return null;
	}

	public boolean evaluateTransaction(String transactionId, int score) {
		if(Integer.valueOf(score)!=null && transactionId!=null & transazioni.containsKey(transactionId)) {
			transazioni.get(transactionId).score=score;
		}
		if(score>=1 && score<=10)
			return true;
		return false;
	}
	
//R4   //regione a interessata da 3 transazioni con la regione b e c ma non con la D e la E
	public SortedMap<Long, List<String>> deliveryRegionsPerNT() {
		return transazioni.values().stream()
				.map(a->this.getRegion(a.request.placeName))
				.collect(Collectors.groupingBy(
						s->s,
						TreeMap::new,
						Collectors.counting()
						))
				.entrySet().stream()
				.collect(Collectors.groupingBy(
							s->s.getValue(),
							() -> new TreeMap<Long, List<String>>(Comparator.reverseOrder()),
							Collectors.mapping(s->s.getKey(), Collectors.toList())
						));
	}
	
	public SortedMap<String, Integer> scorePerCarrier(int minimumScore) {
		return transazioni.values().stream()
				.filter(a->a.score>=minimumScore)
				.collect(Collectors.groupingBy(
						a->a.carrierName,
						TreeMap::new,
						Collectors.summingInt(a->a.score)
						));
	}
	
	public SortedMap<String, Long> nTPerProduct() {
		return transazioni.values().stream()
				.collect(Collectors.groupingBy(
						a->a.request.productId,
						TreeMap::new,
						Collectors.counting()
						));
	}
	
	
}

