package bg.sofia.uni.fmi.mjt.stylechecker;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class StyleCheckerTest {

	@Test
	public void wildcardsCheck() {

		StyleChecker checker = new StyleChecker();

		ByteArrayInputStream input = new ByteArrayInputStream("import java.util.*;".getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(input, output);
		String actual = new String(output.toByteArray());

		assertEquals("// FIXME Wildcards are not allowed in import statements\nimport java.util.*;", actual.trim());
	}

	@Test
	public void statementsPerLineCheck() {

		StyleChecker checker = new StyleChecker();

		ByteArrayInputStream input = new ByteArrayInputStream("sayHi();EverythingIsFine();".getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(input, output);
		String actual = new String(output.toByteArray());

		assertEquals("// FIXME Only one statement per line is allowed\nsayHi();EverythingIsFine();", actual.trim());

		try {

			input.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	@Test
	public void statementsPerLineCheckTwo() {

		StyleChecker checker = new StyleChecker();

		ByteArrayInputStream input = new ByteArrayInputStream("sayHi();;;;;".getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(input, output);
		String actual = new String(output.toByteArray());

		assertEquals("sayHi();;;;;", actual.trim());

		try {

			input.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	@Test
	public void openingBracketCheck() {

		StyleChecker checker = new StyleChecker();

		ByteArrayInputStream input = new ByteArrayInputStream(("howAreYou()\\{\\}").getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(input, output);
		String actual = new String(output.toByteArray());

		assertEquals("howAreYou()\\{\\}", actual);

		try {

			input.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	@Test
	public void openingBracketCheckTwo() {

		StyleChecker checker = new StyleChecker();

		ByteArrayInputStream input = new ByteArrayInputStream(("hey()\n\\{getMessage();\\}").getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(input, output);
		String actual = new String(output.toByteArray());

		assertEquals("hey()\n// FIXME Opening brackets should be placed "
				+ "on the same line as the declaration\n\\{getMessage();\\}", actual);

		try {

			input.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	@Test
	public void lengthOfLineCheckMoreThanHundredCharacters() {

		StyleChecker checker = new StyleChecker();

		ByteArrayInputStream input = new ByteArrayInputStream(("oRF89wyf7OTBleZSfR7d2bINHlCsMIU5wsO0ddmbyXvkdHZl"
				+ "1QTZ0R4mF512e0pXJj2QfsUQmFfZ86nvviQ1HfNSQm1X71BS90hzaaa\r\n").getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(input, output);
		String actual = new String(output.toByteArray());

		assertEquals("// FIXME Length of line should not exceed 100 characters\noRF89w"
				+ "yf7OTBleZSfR7d2bINHlCsMIU5wsO0ddmbyXvkdHZl1QTZ0R4mF512e0pXJj2Q"
				+ "fsUQmFfZ86nvviQ1HfNSQm1X71BS90hzaaa", actual);

		try {

			input.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	@Test
	public void lengthOfLineCheck() {

		StyleChecker checker = new StyleChecker(new ByteArrayInputStream("line.length.limit=130".getBytes()));

		ByteArrayInputStream input = new ByteArrayInputStream(
				("oRF89wyf7OTBleZSfR7d2bINHlCsMIU5wsO0ddmbyXvkdHZl1QTZ0R4mF512"
						+ "e0pXJj2QfsUQmFfZ86nvviQ1HfNSQm1X71BS90hzaaa\r\n").getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(input, output);
		String actual = new String(output.toByteArray());

		assertEquals("oRF89wyf7OTBleZSfR7d2bINHlCsMIU5wsO0ddmbyXvkdHZl1QTZ0R4mF512"
				+ "e0pXJj2QfsUQmFfZ86nvviQ1HfNSQm1X71BS90hzaaa", actual);

		try {

			input.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	@Test
	public void multipleErrorsCheck() {

		StyleChecker checker = new StyleChecker();

		ByteArrayInputStream input = new ByteArrayInputStream(
				("sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();"
						+ "sayHello();sayHello();sayHello();sayHello();sayHello();").getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		checker.checkStyle(input, output);
		String actual = new String(output.toByteArray());

		assertEquals("// FIXME Only one statement per line is allowed\n"
				+ "// FIXME Length of line should not exceed 100 characters\n"
				+ "sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();"
				+ "sayHello();sayHello();sayHello();sayHello();sayHello();", actual);

		try {

			input.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

}
