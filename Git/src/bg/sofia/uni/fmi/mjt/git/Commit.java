package bg.sofia.uni.fmi.mjt.git;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Commit {

	private Date date;
	private String dateStr;
	private String hash;
	private String message;

	private Map<String, File> commitFiles;

	public Commit(String message) {

		this.date = new Date();
		this.message = message;
		try {
			this.hash = this.hexDigest(this.getDate() + this.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		this.commitFiles = new HashMap<>();
	}

	public String getHash() {

		return this.hash;
	}

	public String getMessage() {

		return this.message;
	}

	public String getDate() {

		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm yyyy", Locale.US);
		dateStr = formatter.format(this.date);

		return this.dateStr;
	}

	public String hexDigest(String input) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		return convertBytesToHex(bytes);
	}

	private String convertBytesToHex(byte[] bytes) {
		StringBuilder hex = new StringBuilder();
		for (byte current : bytes) {
			hex.append(String.format("%02x", current));
		}

		return hex.toString();
	}

	public Map<String, File> getCommitFiles() {
		return this.commitFiles;
	}

}
