package bg.sofia.uni.fmi.mjt.git;

public class Result {

	private String message;

	private boolean success;

	public Result(String sentence, String[] arrStr, boolean success) {

		this.message = new String("");

		String res = new String("");

		for (String str : arrStr) {

			if (str == arrStr[0]) {
				res = res.concat(str);
			} else {
				res = res + ", " + str;
			}
		}

		this.message = sentence.replaceAll("\\{\\}", res);

		this.success = success;
	}

	public Result(String sentence, boolean success) {

		this.message = sentence;

		this.success = success;
	}

	public boolean isSuccessful() {

		return this.success;
	}

	public String getMessage() {

		return this.message;
	}

}
