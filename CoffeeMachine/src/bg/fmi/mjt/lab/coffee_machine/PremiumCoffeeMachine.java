package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.container.PremiumContainer;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class PremiumCoffeeMachine implements CoffeeMachine {
	
	private Container container;
	
	public PremiumCoffeeMachine()
	{
		this.container = new PremiumContainer();
	}

	/**
	* @param autoRefill - if true, it will automatically refill the container
	* if there are not enough ingredients to make the coffee drink
	*/
	public PremiumCoffeeMachine(boolean autoRefill)
	{
		this.container = new PremiumContainer();
		this.container.setAutoRefill(autoRefill);
	}	
	
	public Product brew(Beverage beverage) {
		
		if(this.container.removeIngredients(beverage)==true)
		{
			return new Product(beverage, 1, true);		
		}
		
		else
		{
			return null;
		}
	}
	
	/**
	* If quantity is <= 0 or the quantity is not supported for
	* the particular Coffee Machine the method returns null
	*/
	public Product brew(Beverage beverage, int quantity) {
		
		if((quantity<=0 || quantity>3) || this.container.removeIngredients(beverage)==false)
		{
			return null;
		}
		
		else
		{
			return new Product(beverage, quantity, true);
		}
	}

	public Container getSupplies() {
		
		return this.container;
	}

	public void refill() {
		
		this.container = new PremiumContainer();	
	}

}
