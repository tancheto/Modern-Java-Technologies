package bg.sofia.uni.fmi.mjt.carstore.enums;

public enum Region {

	SOFIA("CB"), BURGAS("A"), VARNA("B"), PLOVDIV("PB"), RUSE("P"), GABROVO("EB"), VIDIN("BH"), VRATSA("BP");

	private String code;

	Region(String code) {
		this.code = code;
	}

	public String getPrefix() {
		return this.code;
	}

}
