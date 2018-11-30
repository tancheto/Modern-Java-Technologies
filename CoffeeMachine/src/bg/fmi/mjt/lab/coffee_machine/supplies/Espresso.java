package bg.fmi.mjt.lab.coffee_machine.supplies;

public class Espresso implements Beverage {
	
	public String getName() {
		
		return "Espresso";
	}

	public double getMilk() {
		
		return 0;
	}

	public double getCoffee() {
		
		return 10;
	}

	public double getWater() {

		return 30;
	}

	public double getCacao() {
		
		return 0;
	}

}
