package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class Product {
	
	private Beverage beverage;
	private int quantity;
	private static int luckNum = 0;
	private boolean hasLuck=false;
	String [] luck= {"If at first you don't succeed call it version 1.0.",
			 "Today you will make magic happen!",
			 "Have you tried turning it off and on again?",
			 "Life would be much more easier if you had the source code."
			 };
	
	public Product(Beverage beverage, int quantity, boolean hasLuck)
	{
		this.beverage = beverage; 
		this.quantity = quantity;
		this.hasLuck = hasLuck;
	}

	public String getName()
	{
		return this.beverage.getName();
	}

	public int getQuantity()
	{
		return this.quantity;
	}
	
	public String getLuck()	{
		if(hasLuck)
		{
		Product.luckNum++;
		
		if(Product.luckNum % 4 == 0)
		{
			return luck[3];
		}
		
		return luck[(Product.luckNum % 4) -1];		
		}
		
		return null;
	}
}
