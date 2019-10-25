import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class CommunicationUtility {

	private Socket socket;

	private static Map<String, File> playlists;
	private static Map<String, Integer> allSongs;

	private static PrintWriter writer;
	private static BufferedReader reader;

	private boolean onlineUser;
	private boolean disconnectedUser;
	private boolean playingSong;

	private SourceDataLine line;
	private AudioInputStream din;

	CommunicationUtility(Socket socket) {

		try {
			writer = new PrintWriter(socket.getOutputStream(), true); // autoflush
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Communication through the socket failed.");
		}

		this.socket = socket;
		this.playlists = new ConcurrentHashMap<>();
		this.allSongs = new ConcurrentHashMap<>();

		setAllSongs();
		setPlaylists();
	}

	private void setAllSongs() {

		String line;

		try (BufferedReader br = new BufferedReader(new FileReader("./resources/AllSongs.txt"))) {

			while ((line = br.readLine()) != null) {

				allSongs.put(line, 0);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File AllSongs.txt is missing!");
		} catch (IOException e) {
			System.err.println("Reading from file AllSongs.txt failed!");
		}
	}

	private void setPlaylists() {

		File playlistDir = new File("./resources/playlists");

		File[] files = playlistDir.listFiles();

		if (files != null) {
			for (File playlist : files) {

				String name = playlist.getName().substring(0, playlist.getName().length() - 4);

				playlists.put(name, playlist);
			}
		}
	}

	public void initialise() {

		String message;

		try {
			while (true) {

				if ((message = reader.readLine()) != null) {

					if (message.startsWith("register")) {

						register(message);
					} else if (message.startsWith("login")) {

						if (login(message)) {
							break;
						}
					} else if (message.equals("disconnect")) {

						disconnect();
						break;
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Communication through the socket failed.");
		}
	}

	public void communicate() {

		String message;

		try {
			while (!disconnectedUser) {

				if ((message = reader.readLine()) != null) {

					if (message.startsWith("search")) {

						search(message);
					} else if (message.startsWith("top")) {

						listTop(message);
					} else if (message.startsWith("create-playlist")) {

						createPlaylist(message);
					} else if (message.startsWith("add-song-to")) {

						addSongTo(message);
					} else if (message.startsWith("show-playlist")) {

						showPlaylist(message);
					} else if (message.startsWith("play")) {

						play(message);
					} else if (message.equals("stop")) {

						stop();
					} else if (message.startsWith("disconnect")) {

						disconnect();
						break;
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Communication through the socket failed.");
		}
	}

	private void register(String msg) {

		String[] tokens = msg.split("\\s+");

		if (!tokens[0].equals("register") || tokens.length < 3) {

			writer.println("Wrong command! Try typing it again.");
			return;
		}

		String email = tokens[1];
		String password = tokens[2];

		registerUser(email, password);
	}

	private void registerUser(String email, String password) {

		// password-hashing???

		// appending to the end of the file and autoflushing
		try (PrintWriter listUsers = new PrintWriter(
				new BufferedWriter(new FileWriter("./resources/RegisteredUsers.txt", true)), true)) {

			if (!alreadyTaken(email)) {

				// MessageDigest digest = MessageDigest.getInstance("SHA-256");
				// byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				// String hashedPassword = Base64.getEncoder().encodeToString(hash);
				listUsers.println(email + " " + password);
				writer.println("User with email: " + email + " was successfully registered!");
				return;
			}

		} catch (FileNotFoundException e) {
			System.err.println("File RegisteredUsers.txt is missing!");
			throw new RuntimeException(e);
		} catch (IOException e1) {
			System.err.println("Writing to file RegisteredUsers.txt failed!");
		}
	}

	private boolean alreadyTaken(String email) {

		// hashmap?????????????

		try (BufferedReader br = new BufferedReader(new FileReader("./resources/RegisteredUsers.txt"))) {

			String line;
			while ((line = br.readLine()) != null) {

				if (line.startsWith(email)) {

					writer.println("Email: " + email + " is already used!");
					return true;
				}
			}

			return false;
		} catch (FileNotFoundException e) {
			System.err.println("File RegisteredUsers.txt is missing!");
		} catch (IOException e) {
			System.err.println("Reading from file RegisteredUsers.txt failed!");
		}

		writer.println("Failed registrating user with email: " + email);
		return true;
	}

	private boolean login(String msg) {

		String[] tokens = msg.split("\\s+");

		if (!tokens[0].equals("login") || tokens.length < 3) {

			writer.println("Wrong command! Try typing it again.");
			return false;
		}

		String email = tokens[1];
		String password = tokens[2];

		if (loginUser(email, password)) {

			return true;
		}

		return false;
	}

	private boolean loginUser(String email, String password) {

		// hashing the password???

		if (!alreadyLogged(email)) {

			String line;
			try (BufferedReader br = new BufferedReader(new FileReader("./resources/RegisteredUsers.txt"))) {

				// Cryptographic hash function for extra security.
				// MessageDigest digest = MessageDigest.getInstance("SHA-256");
				// byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				// String hashedPassword = Base64.getEncoder().encodeToString(hash);

				while ((line = br.readLine()) != null) {

					String[] tokens = line.split("\\s+");

					if (tokens[0].equals(email) && tokens[1].equals(password)) {

						onlineUser = true;
						writer.println("Successfully logged in!");
						return true;
					}
				}

				writer.println("Wrong Email or Password! Try again.");
				return false;
			} catch (FileNotFoundException e) {
				System.err.println("File RegisteredUsers.txt is missing!");
			} catch (IOException e) {
				System.err.println("Reading from file RegisteredUsers.txt failed!");
			}
		}

		return false; /////// ??? maybe true?
	}

	private boolean alreadyLogged(String email) {

		if (onlineUser) {
			writer.println("You are already logged in!");
			return true;
		}
		return false;
	}

	private void disconnect() {

		onlineUser = false;
		disconnectedUser = true;
		writer.println("Disconnected user.");
	}

	private void search(String msg) {

		boolean toPrint = false;

		String[] tokens = msg.split("\\s+");

		if (!tokens[0].equals("search") || tokens.length < 2) {

			writer.println("Wrong command! Try typing it again.");
			return;
		}

		for (String song : allSongs.keySet()) {

			toPrint = true;

			for (int i = 1; i < tokens.length; i++) {

				if (!song.toLowerCase().contains(tokens[i].toLowerCase())) {
					toPrint = false;
					break;
				}
			}

			if (toPrint) {
				writer.println(song);
			}
		}
	}

	private void listTop(String msg) {

		String[] tokens = msg.split("\\s+");

		if (!tokens[0].equals("top") || tokens.length != 2) {

			writer.println("Wrong command! Try typing it again.");
			return;
		}

		int num = Integer.parseInt(tokens[1]);

		top(num);
	}

	private void top(int num) {

		Map<String, Integer> result = allSongs.entrySet().stream()
				.sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		int place = 0;

		for (String song : result.keySet()) {
			place++;
			if (place > num) {
				break;
			}
			writer.println(place + ". " + song + " - listened " + allSongs.get(song) + " times.");
		}
	}

	private void createPlaylist(String msg) {

		String[] tokens = msg.split("\\s+");

		if (!tokens[0].equals("create-playlist") || tokens.length < 2) {

			writer.println("Wrong command! Try typing it again.");
			return;
		}

		String name = tokens[1];

		createFile(name);

	}

	private void createFile(String name) {
		try {
			File file = new File("./resources/playlists/" + name + ".txt");

			boolean isNew = file.createNewFile();

			if (isNew) {
				playlists.put(name, file);
				writer.println("Playlist has been created successfully!");
			} else {
				writer.println("Playlist with the name specified already exist!");
			}
		} catch (IOException e) {

			System.err.println("Creating the file failed!");
		}
	}

	private void play(String msg) {

		String[] tokens = msg.split("\\s+");

		if (!tokens[0].equals("play") || tokens.length != 2) {

			writer.println("Wrong command! Try typing it again.");
			return;
		}

		String song = tokens[1];

		if (!allSongs.containsKey(song)) {

			writer.println("The specified song does not present. Try again!");
			return;
		}

		playTheSong(song);
	}

	private void playTheSong(String song) {

		try {

			/*
			 * format = new AudioFormat(sampleRate, 16, 1, true, false); dataLineInfo = new
			 * DataLine.Info(SourceDataLine.class, format); sourceDataLine =
			 * (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			 * sourceDataLine.open(format); sourceDataLine.start();
			 * 
			 */

			File file = new File("./resources/" + song + ".wav");
			AudioInputStream in = AudioSystem.getAudioInputStream(file);
			

			AudioFormat audioFormat = in.getFormat();

			writer.println(audioFormat.getEncoding() + " " + audioFormat.getSampleRate() + " "
					+ audioFormat.getSampleSizeInBits() + " " + audioFormat.getChannels() + " "
					+ audioFormat.getFrameSize() + " " + audioFormat.getFrameRate() + " " + audioFormat.isBigEndian());

			/*
			AudioFormat baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			din = AudioSystem.getAudioInputStream(decodedFormat, in);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
			line = (SourceDataLine) AudioSystem.getLine(info);
			if (line != null) {
				line.open(decodedFormat);
				byte[] data = new byte[4096];
				// Start
				line.start();
				int nBytesRead;
				while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
					out.write(data, 0, nBytesRead);
				}
				// Stop
				line.drain();
				line.stop();
				line.close();
				din.close();
				
			}*/
			
			
			/*
			 * 
			 * byte buffer[] = new byte[2048]; int count; while ((count = in.read(buffer))
			 * != -1) { out.write(buffer, 0, count); }
			 * 
			 * 
			 * DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			 * DataLine dataLine = (SourceDataLine) AudioSystem.getLine(info);
			 * 
			 * if (dataLine != null) { dataLine.open(audioFormat); byte[] data = new
			 * byte[4096]; // Start dataLine.start(); int nBytesRead; while ((nBytesRead =
			 * audio.read(data, 0, data.length)) != -1) { dataLine.write(data, 0,
			 * nBytesRead); } // Stop dataLine.drain(); dataLine.stop(); dataLine.close();
			 * audio.close();
			 */
			int num = allSongs.get(song);
			num++;
			allSongs.put(song, num);

			playingSong = true;

		} catch (UnsupportedAudioFileException e) {
			System.err.println("The audio format is not supported.");
		} catch (IOException e) {
			System.err.println("Reading the file failed.");
		}
	}

	private void stop() {

		if (!playingSong) {

			writer.println("No song is being played!");
			return;
		}

		writer.println("stop");

		playingSong = false;
	}

	private void addSongTo(String msg) {

		String[] tokens = msg.split("\\s+");

		if (!tokens[0].equals("add-song-to") || tokens.length != 3) {

			writer.println("Wrong command! Try typing it again.");
			return;
		}

		String song = tokens[1];
		String playlist = tokens[2];

		if (!allSongs.containsKey(song)) {
			writer.println("The specified song does not present. Try again!");
			return;
		}
		if (!playlists.containsKey(playlist)) {

			writer.println("The specified playlist does not present. Try again!");
			return;
		}

		add(song, playlist);
	}

	private void add(String song, String playlist) {

		File file = playlists.get(playlist);

		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {

			pw.println(song);
			writer.println("Successfully added song to the playlist!");

		} catch (IOException e) {

			System.err.println("Writing to file" + file.getName() + "failed!");
		}
	}

	private void showPlaylist(String msg) {

		String[] tokens = msg.split("\\s+");

		if (!tokens[0].equals("show-playlist") || tokens.length != 2) {

			writer.println("Wrong command! Try typing it again.");
			return;
		}

		String playlist = tokens[1];

		show(playlist);
	}

	private void show(String playlist) {

		// File file = playlists.get(playlist);

		writer.println("Playlist " + playlist);

		try (BufferedReader br = new BufferedReader(new FileReader("./resources/playlists/" + playlist + ".txt"))) {

			String line;
			while ((line = br.readLine()) != null) {

				writer.println(line);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File" + playlist + ".txt is missing!");
		} catch (IOException e) {
			System.err.println("Reading from file " + playlist + ".txt failed!");
		}
	}

}
