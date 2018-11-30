package bg.sofia.uni.fmi.mjt.carstore.car;

import java.util.Random;

public class RegNumGenerator {

	String regNum;

	static int number = 1000;

	public RegNumGenerator(String prefix) {

		Random rand1 = new Random();
		int n1 = rand1.nextInt(25) + 65;
		char ch1 = (char) n1;

		Random rand2 = new Random();
		int n2 = rand2.nextInt(25) + 65;
		char ch2 = (char) n2;

		this.regNum = prefix + number + ch1 + ch2;

		number++;
	}

	public String getRegNum() {

		return this.regNum;
	}
}
