package bg.sofia.uni.fmi.mjt.carstore.car;

import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

public class SportsCar extends Car {

	public SportsCar(Model model, int year, int price, EngineType engineType, Region region) {

		RegNumGenerator regNumGen = new RegNumGenerator(region.getPrefix());

		this.model = model;
		this.year = year;
		this.price = price;
		this.engineType = engineType;
		this.region = region;
		this.regNum = regNumGen.getRegNum();
	}

}
