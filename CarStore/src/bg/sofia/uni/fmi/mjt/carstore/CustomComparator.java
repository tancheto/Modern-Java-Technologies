package bg.sofia.uni.fmi.mjt.carstore;

import java.util.Comparator;

import bg.sofia.uni.fmi.mjt.carstore.car.Car;

public class CustomComparator implements Comparator<Car> {

	@Override
	public int compare(Car car1, Car car2) {
		if (car1.getModel().ordinal() < car2.getModel().ordinal()) {
			return -1;
		}

		else if (car1.getModel().ordinal() > car2.getModel().ordinal()) {
			return 1;
		}

		else {
			if (car1.getYear() < car2.getYear()) {
				return -1;
			}

			else if (car1.getYear() > car2.getYear()) {
				return 1;
			}

			else {
				return 0;
			}
		}
	}

}