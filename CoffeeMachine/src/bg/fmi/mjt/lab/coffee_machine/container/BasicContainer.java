package bg.fmi.mjt.lab.coffee_machine.container;

import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class BasicContainer extends Container{
	
	private double water;
	private double coffee;
	private double milk;
	private double cacao;
	
	public BasicContainer()	{
		
		this.water=600;
		this.coffee=600;
		this.milk=0;
		this.cacao=0;
	}
	
	public BasicContainer(double water, double coffee, double milk, double cacao) {
		
		if(water>600) {this.water=600;}
		else {this.water=water;}
		if(coffee>600) {this.coffee=600;}
		else{this.coffee=coffee;}
		this.milk=0;
		this.cacao=0;
	}

	public double getCurrentWater() {
		
		return this.water;
	}

	public double getCurrentMilk() {
		
		return this.milk;
	}

	public double getCurrentCoffee() {
		
		return this.coffee;
	}

	public double getCurrentCacao() {
		
		return this.cacao;
	}	
	
	public boolean removeIngredients(Beverage beverage) {
		
		if(this.water>=beverage.getWater()) {
			this.water-=beverage.getWater();			
			}
		else {
			this.water=0;
			return false;
		}
		
		if(this.milk>=beverage.getMilk()) {
			this.milk-=beverage.getMilk();			
			}
		else {
			this.milk=0;
			return false;
		}
		
		if(this.coffee>=beverage.getCoffee()) {
			this.coffee-=beverage.getCoffee();			
			}
		else {
			this.coffee=0;
			return false;
		}
		
		if(this.cacao>=beverage.getCacao()) {
			this.cacao-=beverage.getCacao();			
			}
		else {
			this.cacao=0;
			return false;
		}

		return true;
	}

}
