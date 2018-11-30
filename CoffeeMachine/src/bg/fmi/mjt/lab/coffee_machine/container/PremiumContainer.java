package bg.fmi.mjt.lab.coffee_machine.container;

import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class PremiumContainer extends Container {
	
	private double water;
	private double coffee;
	private double milk;
	private double cacao;
	
	private boolean autoRefill;
	
	public PremiumContainer()	{
		
		this.water = 1000;
		this.coffee = 1000;
		this.milk = 1000;
		this.cacao = 300;
	}
	
	public PremiumContainer(double water, double coffee, double milk, double cacao) {
		
		if(water>1000) {this.water=1000;}
		else {this.water = water;}
		if(coffee>1000) {this.coffee=1000;}
		else{this.coffee = coffee;}
		if(milk>1000) {this.milk=1000;}
		else{this.milk = milk;}
		if(cacao>1000) {this.cacao=1000;}
		else{this.cacao = cacao;}
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
	
	public void setAutoRefill(boolean autoRefill)
	{
		this.autoRefill=autoRefill;
	}

	public boolean removeIngredients(Beverage beverage) {
		
		if(autoRefill == true) {
			return true;
		}
		
		else {
		
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

}
