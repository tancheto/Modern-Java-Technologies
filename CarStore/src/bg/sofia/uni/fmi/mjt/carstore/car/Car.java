package bg.sofia.uni.fmi.mjt.carstore.car;

import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

public abstract class Car {

	protected String regNum;
	protected Model model;
	protected int price;
	protected int year;
	protected EngineType engineType;
	protected Region region;

	/**
	 * Returns the model of the car.
	 */
	public Model getModel() {

		return this.model;
	}

	/**
	 * Returns the year of manufacture of the car.
	 */
	public int getYear() {

		return this.year;
	}

	/**
	 * Returns the price of the car.
	 */
	public int getPrice() {
		return this.price;
	}

	/**
	 * Returns the engine type of the car.
	 */
	public EngineType getEngineType() {
		return this.engineType;
	}

	/**
	 * Returns the unique registration number of the car.
	 */
	public String getRegistrationNumber() {

		return this.regNum;
	}

}
