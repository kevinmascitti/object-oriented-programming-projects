package diet;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
    
	private String name;
	private Food food;
	private NutritionalElement[] ingredients;
	private double[] quantities;
	private int tot = 0;
	private double gtot = 0;
	
	private double calories = 0;
	private double proteins = 0;
	private double carbs = 0;
	private double fat = 0;
	
	
	public Recipe (String name, Food food) {
		this.name=name;
		this.food=food;
		ingredients = new NutritionalElement[30];
		quantities = new double[30];
	}

	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		ingredients[tot] = food.getRawMaterial(material);
		quantities[tot] += quantity;
		gtot += quantity;
		calories += ingredients[tot].getCalories() * quantity / 100;
		proteins += ingredients[tot].getProteins() * quantity / 100;
		carbs += ingredients[tot].getCarbs() * quantity / 100;
		fat += ingredients[tot].getFat() * quantity / 100;
		tot++;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getCalories() {
		return calories * 100 / gtot;
	}

	@Override
	public double getProteins() {
		return proteins * 100 / gtot;
	}

	@Override
	public double getCarbs() {
		return carbs * 100 / gtot;
	}

	@Override
	public double getFat() {
		return fat * 100 / gtot;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		StringBuffer a = new StringBuffer();
		for(int i = 0; i < tot; i++) {
			a.append(ingredients[i].getName()).append(" : ").append(quantities[i]).append("\n");
		}
		return a.toString();
	}
}
