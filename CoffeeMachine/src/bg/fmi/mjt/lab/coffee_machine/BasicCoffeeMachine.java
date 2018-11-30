package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.BasicContainer;
import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class BasicCoffeeMachine implements CoffeeMachine{
	
	private Container container;

	public BasicCoffeeMachine()
	{
		this.container = new BasicContainer();
	}

	public Product brew (Beverage beverage) {	
		
		if(this.container.removeIngredients(beverage) == true)
		{
			return new Product(beverage, 1, false);		
		}
		
		else
		{
			return null;
		}
	}

	public Container getSupplies() {
		
		return this.container;
	}

	public void refill() {
		
		this.container = new BasicContainer();		
	}


}
