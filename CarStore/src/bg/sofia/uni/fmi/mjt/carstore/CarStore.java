package bg.sofia.uni.fmi.mjt.carstore;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import bg.sofia.uni.fmi.mjt.carstore.car.Car;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.exception.CarNotFoundException;

public class CarStore {

	private Map<String, Car> storeCars;

	public CarStore() {
		this.storeCars = new HashMap<>();

	}

	/**
	 * Adds the specified car in the store.
	 * 
	 * @return true if the car was added successfully to the store
	 */
	public boolean add(Car car) {

		if (car == null) {
			throw new CarNotFoundException("Car not found.");
		} else {

			if (!storeCars.containsKey(car.getRegistrationNumber())) {
				this.storeCars.put(car.getRegistrationNumber(), car);
				return true;
			}

			return false;
		}
	}

	/**
	 * Adds all of the elements of the specified collection in the store.
	 * 
	 * @return true if the store cars are changed after the execution (i.e. at least
	 *         one new car is added to the store)
	 */
	public boolean addAll(Collection<Car> cars) {

		boolean flag = false;

		Iterator<Car> it = cars.iterator();

		while (it.hasNext()) {
			Car car = it.next();

			if (car == null) {
				throw new CarNotFoundException("Car not found.");
			} else {

				if (!this.storeCars.containsKey(car.getRegistrationNumber())) {
					this.storeCars.put(car.getRegistrationNumber(), car);
					flag = true;
				}
			}
		}

		return flag;
	}

	/**
	 * Removes the specified car from the store.
	 * 
	 * @return true if the car is successfully removed from the store
	 */
	public boolean remove(Car car) {

		if (car == null) {
			throw new CarNotFoundException("Car not found.");
		} else {

			if (this.storeCars.containsKey(car.getRegistrationNumber())) {
				this.storeCars.remove(car.getRegistrationNumber());
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns all cars of a given model. The cars need to be sorted by year of
	 * manufacture (in ascending order).
	 */
	public Collection<Car> getCarsByModel(Model model) {

		Iterator<Car> it = storeCars.values().iterator();

		List<Car> cars = new ArrayList<>();

		while (it.hasNext()) {
			Car car = it.next();

			if (car.getModel() == model) {
				cars.add(car);
			}
		}

		return cars;
	}

	/**
	 * Finds a car from the store by its registration number.
	 * 
	 * @throws CarNotFoundException if a car with this registration number is not
	 *                              found in the store
	 **/
	public Car getCarByRegistrationNumber(String registrationNumber) {

		return this.storeCars.get(registrationNumber);
	}

	/**
	 * Returns all cars sorted by their default order*
	 **/
	public Collection<Car> getCars() {

		List<Car> cars = new ArrayList<>(storeCars.values());

		Collections.sort(cars, new CustomComparator());

		return cars;
	}

	/**
	 * Returns all cars sorted according to the order induced by the specified
	 * comparator.
	 */
	public Collection<Car> getCars(Comparator<Car> comparator) {

		List<Car> cars = new ArrayList<>(storeCars.values());

		Collections.sort(cars, comparator);

		return cars;
	}

	/**
	 * Returns all cars sorted according to the given comparator and boolean flag
	 * for order.
	 * 
	 * @param isReversed if true the cars should be returned in reversed order
	 */
	public Collection<Car> getCars(Comparator<Car> comparator, boolean isReversed) {
		List<Car> cars = new ArrayList<>(storeCars.values());

		Collections.sort(cars, comparator.reversed());

		return cars;

	}

	/**
	 * Returns the total number of cars in the store.
	 */
	public int getNumberOfCars() {

		return storeCars.size();
	}

	/**
	 * Returns the total price of all cars in the store.
	 */
	public int getTotalPriceForCars() {

		int price = 0;

		Iterator<Car> it = storeCars.values().iterator();

		while (it.hasNext()) {
			Car car = it.next();
			price += car.getPrice();
		}

		return price;
	}

}