package groups;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class GroupHandling {
	
	private Map<String, Product> prodotti = new TreeMap<>();
	private Map<String, Supplier> fornitori = new TreeMap<>();
	private Map<String, Gruppo> gruppi = new TreeMap<>();
	private Map<String, Cliente> clienti = new TreeMap<>();
	private List<Bid> offerte = new LinkedList<>();

	//R1	
	public void addProduct (String productTypeName, String... supplierNames) 
			throws GroupHandlingException {
		if(!prodotti.containsKey(productTypeName) && supplierNames.length>0) {
			Product p = new Product(productTypeName);
			prodotti.put(productTypeName, p);
			for(String s : supplierNames) {
				Supplier su = new Supplier(s);
				p.addSupplier(su);
				if(!fornitori.containsKey(s))
					fornitori.put(s, su);
				fornitori.get(s).addProduct(p);
			}
		}
		else{
			throw new GroupHandlingException();
		}
	}
	
	public List<String> getProductTypes (String supplierName) {
		return prodotti.values().stream()
				.filter(a->a.getFornitori().containsKey(supplierName)==true)
				.map(a->a.getType())
				.sorted()
				.collect(Collectors.toList());
	}
	
//R2	
	public void addGroup (String name, String productName, String... customerNames) 
			throws GroupHandlingException {
		if(!gruppi.containsKey(name) && prodotti.containsKey(productName) &&
				customerNames.length>0 && name!=null && productName!=null) {
			Gruppo g = new Gruppo(name, prodotti.get(productName));
			gruppi.put(name, g);
			for(String s : customerNames) {
				Cliente c = new Cliente(s);
				if(!clienti.containsKey(s)) {
					clienti.put(s, c);
				}
				g.addCliente(c);
				c.addGruppo(g);
			}
			
		}
		else {
			throw new GroupHandlingException();
		}
	}
	
	public List<String> getGroups (String customerName) {
        return gruppi.values().stream()
        		.map(a->a.getName())
        		.sorted()
        		.collect(Collectors.toList());
	}

//R3
	public void setSuppliers (String groupName, String... supplierNames)
			throws GroupHandlingException {
		if(gruppi.containsKey(groupName) && supplierNames.length>0 && groupName!=null) {
			Gruppo g = gruppi.get(groupName);
			for(String s : supplierNames) {
				Supplier su = fornitori.get(s);
				if(su==null || !su.getProdotti().keySet().contains(g.getTypeProduct()) ) {
					throw new GroupHandlingException();
				}
			}
			for(String s : supplierNames) {
				Supplier su = fornitori.get(s);
				g.addSupplier(su);
				su.addGruppo(g);
			}
		} else {
			throw new GroupHandlingException();
		}
	}
	
	public void addBid (String groupName, String supplierName, int price)
			throws GroupHandlingException {
		if(gruppi.containsKey(groupName) && fornitori.containsKey(supplierName) && gruppi.get(groupName).getSuppliers().containsKey(supplierName)) {
			Bid b = new Bid(gruppi.get(groupName), fornitori.get(supplierName), price);
			offerte.add(b);
			gruppi.get(groupName).addBid(b);
			prodotti.get(gruppi.get(groupName).getTypeProduct()).addBid(b);
			fornitori.get(supplierName).addBid(b);
		}
		else {
			throw new GroupHandlingException();
		}
	}
	
	public String getBids (String groupName) {
		if(offerte.size()>0)
	        return offerte.stream()
	        		.sorted(Comparator.comparing(Bid::getPrice).thenComparing(b->b.getSupplier().getName()))
	        		.map(a->a.getSupplier().getName()+":"+a.getPrice())
	        		.reduce( (risultatoIntermedio , elemento) -> risultatoIntermedio + "," + elemento )
	        		.orElse(null)
	        		;
		return null;
	}
	
	
//R4	
	public void vote (String groupName, String customerName, String supplierName)
			throws GroupHandlingException {
		if(groupName!=null 
				&& customerName!=null 
				&& supplierName!=null 
				&& gruppi.containsKey(groupName) 
				&& clienti.containsKey(customerName)
				&& fornitori.containsKey(supplierName) 
				&& gruppi.get(groupName).getCustomers().containsKey(customerName)
				&& gruppi.get(groupName).getSuppliers().containsKey(supplierName)) {
			int found=0;
			Bid bid = null;
			for(Bid b : offerte) {
				if(b.getGruppo().getName().equals(groupName)
						&& b.getSupplier().getName().equals(supplierName)) {
					bid = b;
					found=1;
				}
			}
			if(found==1) {
				clienti.get(customerName).addBid(bid);
				bid.addCustomer(clienti.get(customerName));
				bid.increaseVotes();
			}
			else {
				throw new GroupHandlingException();
			}
		} else {
			throw new GroupHandlingException();
		}
	}
	
	public String  getVotes (String groupName) {
        String s = offerte.stream()
        		.filter(a->a.getGruppo().getName()==groupName)
        		.filter(a->a.getVotes()>0)
        		.map(a->a.getSupplier().getName()+":"+a.getVotes())
        		.sorted()
        		.reduce((a,b)-> a+","+b)
        		.orElse("");
        if(s.length()>0)		
        	return s;
        return null;
	}
	
	public String getWinningBid (String groupName) {
        String s = offerte.stream()
        		.filter(a->a.getGruppo().getName()==groupName)
        		.sorted(Comparator.comparing(Bid::getVotes).reversed().thenComparing(Bid::getPrice))
        		.map(a->a.getSupplier().getName()+":"+a.getVotes())
        		.findFirst().get();
        if(s.length()>0)
        	return s;
        return null;
    }
	
//R5
	public SortedMap<String, Integer> maxPricePerProductType() { //serve toMap
        SortedMap<String,Integer> mappa = gruppi.values().stream()
				.flatMap(g -> g.getBids().stream())
        		.collect(Collectors.groupingBy(
        				Bid::getTypeProd,
        				TreeMap::new,
        				Collectors.collectingAndThen(
        						Collectors.maxBy(Comparator.comparing(Bid::getPrice)),
								o -> o.get().getPrice()
								)
        				));
        if(mappa.size()>0)
        	return mappa;
        return null;
	}
	
	public SortedMap<Integer, List<String>> suppliersPerNumberOfBids() {
        SortedMap<Integer, List<String>> mappa = 
        		fornitori.values().stream()
        		.filter(a->a.getBids().size()>0)
        		.sorted(Comparator.comparing(Supplier::getName))
        		.collect(Collectors.groupingBy(
        				a->a.getBids().size(),
        				 () -> new TreeMap<Integer, List<String>>(Comparator.reverseOrder()),
        				Collectors.mapping(Supplier::getName, Collectors.toList())
        				));
        if(mappa.size()>0)
        	return mappa;
        return null;
	}
	
	public SortedMap<String, Long> numberOfCustomersPerProductType() {
        SortedMap<String,Long> mappa =
        		gruppi.values().stream()
				.filter(g -> g.getCustomers().values().size()>0)
        		.collect(Collectors.groupingBy(
        				a->a.getTypeProduct(),
        				TreeMap::new,
        				Collectors.summingLong(g->g.getCustomers().size())
        				))
        		;
        if(mappa.size()>0)
        	return mappa;
        return null;
	}
	
}
