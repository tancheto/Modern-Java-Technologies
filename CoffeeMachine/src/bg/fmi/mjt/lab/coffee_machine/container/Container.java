package bg.fmi.mjt.lab.coffee_machine.container;

import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public abstract class Container {
	
	/**
	* Returns the current quantity (in milliliters) of the water in the container
	*/
	public abstract double getCurrentWater();

	/**
	* Returns the current quantity (in milliliters) of the milk in the container
	*/
	public abstract double getCurrentMilk();

	/**
	* Returns the current quantity (in grams) of the coffee in the container
	*/
	public abstract double getCurrentCoffee();

	/**
	* Returns the current quantity (in grams) of the cacao in the container
	*/
	public abstract double getCurrentCacao();

	public abstract boolean removeIngredients(Beverage beverage);

	public void setAutoRefill(boolean autoRefill) {
		
	}
}
