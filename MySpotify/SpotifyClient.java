import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SpotifyClient {

	private String serverResponse;

	private static Socket clientSocket;
	private static PrintWriter writer;
	private static BufferedReader reader;

	private static final int SERVER_PORT = 8080;
	private static final String SERVER_HOST = new String("localhost");

	private SourceDataLine line;
	private AudioInputStream ais;

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException {

		new SpotifyClient().run();
	}

	public void setServerResponse(String serverResponse) {
		this.serverResponse = serverResponse;
	}

	public synchronized void run() throws UnsupportedAudioFileException, IOException {

		// connect, disconnect???
		// logout???

		try (Scanner scanner = new Scanner(System.in)) {

			String message;

			while (true) {
				if (scanner.hasNextLine()) {
					message = scanner.nextLine();

					if (message.equals("connect")) {
						if (connect()) {
							break;
						}
					}
				}
			}

			while (true) {
				if (scanner.hasNextLine()) {
					message = scanner.nextLine();

					if (message.startsWith("play")) {
						play(message, scanner);
						continue;
					} else if (message.equals("disconnect")) {
						writer.println(message);
						try {
							clientSocket.close();
						} catch (IOException e) {
							System.err.println("Error closing the socket.");
						}
						break;
					}

					writer.println(message);
				}
			}
		}
	}

	private synchronized boolean connect() {

		try {
			clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
			writer = new PrintWriter(clientSocket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			System.out.println("Socket opened successfully.");

			SpotifyClientRunnable clientRunnable = new SpotifyClientRunnable(clientSocket, this);

			new Thread(clientRunnable).start();
			return true;

		} catch (IOException e) {

			System.out.println("Cannot connect to server on host: " + SERVER_HOST + "on port: " + SERVER_PORT
					+ ", make sure that the server is started");
		}

		return false;
	}

	private synchronized void play(String msg, Scanner scanner) throws UnsupportedAudioFileException, IOException {

		writer.println(msg);

		try {

			while (true) {

				if (serverResponse != null) {

					String[] tokens = serverResponse.split("\\s+");

					if (tokens.length != 7) {

						System.out.println("Error in playing the song!");
						break;
					}

					AudioFormat.Encoding encoding = new AudioFormat.Encoding(tokens[0]);
					float sampleRate = Float.parseFloat(tokens[1]);
					int sampleSizeInBits = Integer.parseInt(tokens[2]);
					int channels = Integer.parseInt(tokens[3]);
					int frameSize = Integer.parseInt(tokens[4]);
					float frameRate = Float.parseFloat(tokens[5]);
					boolean bigEndian = Boolean.parseBoolean(tokens[6]);

					AudioFormat decodedFormat = new AudioFormat(encoding, sampleRate, sampleSizeInBits, channels,
							frameSize, frameRate, bigEndian);
					
					InputStream in = clientSocket.getInputStream();
					
					ais = AudioSystem.getAudioInputStream(in);					
					
					DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
					line = (SourceDataLine) AudioSystem.getLine(info);

					if (line != null) {
						byte[] data = new byte[4096];
						int nBytesRead;
						
						line.open(decodedFormat);				
						line.start();
						
						while ((nBytesRead = ais.read(data, 0, data.length)) != -1) {
							line.write(data, 0, nBytesRead);
						}
					
						line.drain();
						line.stop();
						line.close();
						ais.close();
					}
				}
			}
		} catch (NumberFormatException e) {

			System.err.println("Error formatting the numbers.");

		} catch (LineUnavailableException e) {

			System.err.println("Not able to play the specified song!");

		}
	}

	private synchronized void stop() {
		try {
			line.drain();
			line.stop();
			line.close();
			ais.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
