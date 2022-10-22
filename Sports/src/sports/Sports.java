package sports;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Facade class for the research evaluation system
 *
 */
public class Sports {

    private Map<String, Activity> attivita = new TreeMap<>();
	private Map<String, Category> categorie = new TreeMap<>();
	private Map<String, Prodotto> prodotti = new TreeMap<>();
	private List<Review> recensioni = new LinkedList<>();

	//R1
    /**
     * Define the activities types treated in the portal.
     * The method can be invoked multiple times to add different activities.
     * 
     * @param actvities names of the activities
     * @throws SportsException thrown if no activity is provided
     */
    public void defineActivities (String... activities) throws SportsException {
    	if(activities.length>0) {
    		for(String s : activities) {
    			if(s.length()>0) {
    				Activity a = new Activity(s);
    				attivita.put(s, a);
    			}
    		}
    	}
    	else 
    		throw new SportsException();
    }

    /**
     * Retrieves the names of the defined activities.
     * 
     * @return activities names sorted alphabetically
     */
    public List<String> getActivities() {
    	if(attivita.size()>0)
    		return attivita.values().stream()
        		.map(a->a.nome)
        		.sorted()
        		.collect(Collectors.toList());
    	return null;
    }


    /**
     * Add a new category of sport products and the linked activities
     * 
     * @param name name of the new category
     * @param activities reference activities for the category
     * @throws SportsException thrown if any of the specified activity does not exist
     */
    public void addCategory(String name, String... linkedActivities) throws SportsException {
    	if(name.length()>0 && linkedActivities.length>0) {
    		if(!categorie.containsKey(name)) {
    			Category c = new Category(name);
    			for(String s : linkedActivities) {
    				if(s.length()>0 && attivita.containsKey(s)) {
    					c.attivita.put(s, attivita.get(s));
    					attivita.get(s).categorie.put(name, c);
    					categorie.put(name, c);
    				}
    				else
    					throw new SportsException();
    			}
    		}
    	}
    }

    /**
     * Retrieves number of categories.
     * 
     * @return categories count
     */
    public int countCategories() {
        return categorie.size();
    }

    /**
     * Retrieves all the categories linked to a given activity.
     * 
     * @param activity the activity of interest
     * @return list of categories (sorted alphabetically)
     */
    public List<String> getCategoriesForActivity(String activity) {
        if(activity.length()>0 && attivita.containsKey(activity) && attivita.get(activity).categorie.size()>0)
        	return attivita.get(activity)
        		.categorie.values().stream()
        		.map(a->a.name)
        		.sorted()
        		.collect(Collectors.toList());
        else
        	return new LinkedList<>();
    }

    //R2
    /**
     * Add a research group and the relative disciplines.
     * 
     * @param name name of the research group
     * @param disciplines list of disciplines
     * @throws SportsException thrown in case of duplicate name
     */
    public void addProduct(String name, String activityName, String categoryName) throws SportsException {
    	if(name.length()>0 && prodotti.containsKey(name))
    		throw new SportsException();
    	if(activityName.length()>0 && categoryName.length()>0 &&
    			attivita.containsKey(activityName) && categorie.containsKey(categoryName)) {
    		Prodotto p = new Prodotto(name, attivita.get(activityName), categorie.get(categoryName));
    		prodotti.put(name, p);
    		Category c = categorie.get(categoryName);
    		Activity a = attivita.get(activityName);
    		if(!c.prodotti.containsKey(name))
    			c.prodotti.put(name, p);
    		if(!a.prodotti.containsKey(name))
    			a.prodotti.put(name, p);
    	}
    }

    /**
     * Retrieves the list of products for a given category.
     * The list is sorted alphabetically.
     * 
     * @param categoryName name of the category
     * @return list of products
     */
    public List<String> getProductsForCategory(String categoryName){
        if( categoryName.length()>0 && categorie.containsKey(categoryName)
        		&& categorie.get(categoryName).prodotti.size()>0 )
        	return categorie.get(categoryName)
        		.prodotti.keySet().stream()
        		.sorted()
        		.collect(Collectors.toList());
        return new LinkedList<>();
    }

    /**
     * Retrieves the list of products for a given activity.
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @return list of products
     */
    public List<String> getProductsForActivity(String activityName){
        if( activityName.length()>0 && attivita.containsKey(activityName) 
        		&& attivita.get(activityName).prodotti.size()>0 )
        	return attivita.get(activityName)
        		.prodotti.keySet().stream()
        		.sorted()
        		.collect(Collectors.toList());
        return new LinkedList<>();
    }

    /**
     * Retrieves the list of products for a given activity and a set of categories
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @param categoryNames names of the categories
     * @return list of products
     */
    public List<String> getProducts(String activityName, String... categoryNames){
    	if(activityName.length()>0 && categoryNames.length>0) {
	        List<String> lista = new LinkedList<>();
    		if(!attivita.containsKey(activityName) || attivita.get(activityName).prodotti.size()==0)
    			return lista;
	        for(Prodotto p : prodotti.values()) {
	        	if(p!=null && p.attivita.getNome().equals(activityName)) {
	        		int found=0;
	        		for(String s : categoryNames) {
	        			if(s.length()>0 && s.equals(p.categoria.name))
							found=1;
	        		}
	        		if(found==1)
	        			lista.add(p.name);
	        	}
	        }
	        return lista;
    	}
    	return new LinkedList<>();
    }

    //    //R3
    /**
     * Add a new product rating
     * 
     * @param productName name of the product
     * @param userName name of the user submitting the rating
     * @param numStars score of the rating in stars
     * @param comment comment for the rating
     * @throws SportsException thrown numStars is not correct
     */
    public void addRating(String productName, String userName, int numStars, String comment) throws SportsException {
    	if(numStars<0 || numStars>5)
    		throw new SportsException();
    	if( productName.length()>0 && userName.length()>0 && comment.length()>0 
    			&& prodotti.containsKey(productName) ) {
    		Review r = new Review(prodotti.get(productName), userName, comment, numStars);
    		recensioni.add(r);
    		prodotti.get(productName).recensioni.add(r);
    	}
    }



    /**
     * Retrieves the ratings for the given product.
     * The ratings are sorted by descending number of stars.
     * 
     * @param productName name of the product
     * @return list of ratings sorted by stars
     */
    public List<String> getRatingsForProduct(String productName) {
    	if( productName.length()>0 && prodotti.containsKey(productName)
    			&& prodotti.get(productName).recensioni.size()>0 ) {
    		return prodotti.get(productName).recensioni.stream()
        		.sorted(Comparator.comparing(Review::getStars).reversed())
        		.map(a->a.numStars+" : "+a.comment)
        		.collect(Collectors.toList());
    	}
    	return new LinkedList<>();
    }


    //R4
    /**
     * Returns the average number of stars of the rating for the given product.
     * 
     * 
     * @param productName name of the product
     * @return average rating
     */
    public double getStarsOfProduct (String productName) {
        if( productName.length()>0 && prodotti.containsKey(productName)
        		&& prodotti.get(productName).recensioni.size()>0 ) {
        	int sum=0, voti=0;
        	for(Review r : prodotti.get(productName).recensioni) {
        		if(r!=null) {
        			sum+=r.numStars;
        			voti++;
        		}
        	}
        	return (double) sum/voti;
        }
        return 0;
    }

    /**
     * Computes the overall average stars of all ratings
     *  
     * @return average stars
     */
    public double averageStars() {
    	if(recensioni.size()>0) {
	    	int sum=0, voti=0;
	    	for(Review r : recensioni) {
	    		if(r!=null) {
	    			sum+=r.numStars;
	    			voti++;
	    		}
	    	}
	    	return (double) sum/voti;
    	}
    	return 0;
    }

    //R5 Statistiche
    /**
     * For each activity return the average stars of the entered ratings.
     * 
     * Activity names are sorted alphabetically.
     * 
     * @return the map associating activity name to average stars
     */
    public SortedMap<String, Double> starsPerActivity() {
        SortedMap<String, Double> mappa = new TreeMap<>();
        for(Activity a : attivita.values()) {
        	if(a!=null) {
        		int sum=0, voti=0;
            	for(Prodotto p : a.prodotti.values()) {
            		if(p!=null) {
            			sum+=this.getStarsOfProduct(p.name);
            			voti++;
            		}
            	}
            	if(sum!=0 && voti!=0) {
            		double media = (double) sum/voti;
            		mappa.put(a.nome, media);
            	}
        	}
        }
        
        if(mappa.size()>0)
        	return mappa;
        return new TreeMap<String,Double>();
    }

    /**
     * For each average star rating returns a list of
     * the products that have such score.
     * 
     * Ratings are sorted in descending order.
     * 
     * @return the map linking the average stars to the list of products
     */
    public SortedMap<Double, List<String>> getProductsPerStars () {
        SortedMap<Double, List<String>> mappa = new TreeMap<>(Comparator.reverseOrder());
        for(Prodotto p : prodotti.values()) {
        	if(p!=null && p.recensioni.size()>0) {
        		double stars = this.getStarsOfProduct(p.name);
        		if(!mappa.containsKey(stars))
        			mappa.put(stars, new LinkedList<>());
        		mappa.get(stars).add(p.name);
        	}
        }
        SortedMap<Double, List<String>> mappa2 = new TreeMap<>(Comparator.reverseOrder());
        for(SortedMap.Entry<Double, List<String>> a : mappa.entrySet()) {
        	if(a!=null && a.getKey()!=null && a.getValue()!=null) {
        		List<String> lista = mappa.get(a.getKey()).stream()
        				.sorted()
        				.collect(Collectors.toList());
        		mappa2.put(a.getKey(), lista);
        	}
        }
        if(mappa2.size()>0)
        	return mappa2;
        return new TreeMap<Double, List<String>>();
    }

}