package bg.sofia.uni.fmi.mjt.stylechecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

public class StyleChecker {

	private static final int LIMIT = 100;

	private int lineLimit;
	private boolean statementPerLine;
	private boolean lengthLine;
	private boolean wildcardImport;
	private boolean openingBracket;

	private int counterErrors;
	private int counterLines;

	public StyleChecker() {

		this.lineLimit = LIMIT;
		this.statementPerLine = true;
		this.lengthLine = true;
		this.wildcardImport = true;
		this.openingBracket = true;

		this.counterErrors = 0;
		this.counterLines = 0;
	}

	public StyleChecker(InputStream inputStream) {

		this.counterErrors = 0;
		this.counterLines = 0;

		Properties properties = new Properties();

		properties.setProperty("line.length.limit", "100");
		properties.setProperty("statements.per.line.check.active", "true");
		properties.setProperty("length.of.line.check.active", "true");
		properties.setProperty("wildcard.import.check.active", "true");
		properties.setProperty("opening.bracket.check.active", "true");

		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.lineLimit = Integer.parseInt(properties.getProperty("line.length.limit"));
		this.statementPerLine = Boolean.parseBoolean(properties.getProperty("statements.per.line.check.active"));
		this.lengthLine = Boolean.parseBoolean(properties.getProperty("length.of.line.check.active"));
		this.wildcardImport = Boolean.parseBoolean(properties.getProperty("wildcard.import.check.active"));
		this.openingBracket = Boolean.parseBoolean(properties.getProperty("opening.bracket.check.active"));
	}

	public void checkStyle(InputStream source, OutputStream output) {

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(source));
			String line = null;

			while ((line = reader.readLine()) != null) {

				counterLines++;

				if (checkStatementsPerLine(line, output)) {
					counterErrors++;
				}
				if (checkWildcardImport(line, output)) {
					counterErrors++;
				}
				if (checkOpeningBracket(line, output)) {
					counterErrors++;
				}
				if (checkLengthOfLine(line, output)) {
					counterErrors++;
				}

				output.write((line).getBytes());
				counterErrors = 0;
			}

			source.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean checkStatementsPerLine(String line, OutputStream output) throws IOException {

		if (this.statementPerLine) {

			String[] statements = line.split(";");
			int counter = 0;

			for (String statement : statements) {

				if (statement.length() > 0) {

					if ((statement.charAt(0) > 'a' && statement.charAt(0) < 'z')
							|| (statement.charAt(0) > 'A' && statement.charAt(0) < 'Z')) {
						counter++;
					}

					if (counter > 1) {
						if (this.counterErrors == 0 && this.counterLines != 1) {
							output.write("\n".getBytes());
						}
						output.write("// FIXME Only one statement per line is allowed\n".getBytes());
						return true;
					}

				}
			}

		}

		return false;

	}

	public boolean checkWildcardImport(String line, OutputStream output) throws IOException {
		if (this.wildcardImport) {

			if (line.length() > 2) {
				if (line.charAt(line.length() - 2) == '*') {

					if (this.counterErrors == 0 && this.counterLines != 1) {
						output.write("\n".getBytes());
					}

					output.write("// FIXME Wildcards are not allowed in import statements\n".getBytes());
					return true;
				}

			}
		}

		return false;
	}

	public boolean checkOpeningBracket(String line, OutputStream output) throws IOException {
		if (this.openingBracket) {

			String trimmedLine = line.trim();

			if (trimmedLine.length() > 1) {
				if (trimmedLine.charAt(1) == '{') {

					if (this.counterErrors == 0 && this.counterLines != 1) {
						output.write("\n".getBytes());
					}

					output.write("// FIXME Opening brackets should be placed on the same line as the declaration\n"
							.getBytes());
					return true;
				}

			}
		}

		return false;
	}

	public boolean checkLengthOfLine(String line, OutputStream output) throws IOException {

		if (this.lengthLine) {

			if (line.contains("import")) {

				return false;

			} else {

				String trimmedLine = line.trim();

				if (trimmedLine.length() > lineLimit) {

					if (this.counterErrors == 0 && this.counterLines != 1) {
						output.write("\n".getBytes());
					}

					output.write(
							("// FIXME Length of line should not exceed " + lineLimit + " characters\n").getBytes());
					return true;
				}

			}

		}

		return false;
	}

}
