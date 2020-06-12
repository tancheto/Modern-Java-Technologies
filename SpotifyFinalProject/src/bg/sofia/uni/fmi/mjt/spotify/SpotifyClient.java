package bg.sofia.uni.fmi.mjt.spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.sound.sampled.UnsupportedAudioFileException;

import bg.sofia.uni.fmi.mjt.spotify.enums.Command;

public class SpotifyClient {

	private static Socket clientSocket;
	private static PrintWriter writer;
	private static BufferedReader reader;

	private static final int SERVER_PORT = 8080;
	private static final String SERVER_HOST = new String("localhost");

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException {

		new SpotifyClient().run();
	}

	public synchronized void run() throws UnsupportedAudioFileException, IOException {

		try (Scanner scanner = new Scanner(System.in)) {

			String message;

			while (true) {
				if (scanner.hasNextLine()) {
					message = scanner.nextLine();

					if (message.trim().equals(Command.CONNECT.toString())) {
						if (isConnected()) {
							break;
						}
					}
				}
			}

			while (true) {
				if (scanner.hasNextLine()) {
					message = scanner.nextLine();

					if (message.trim().equals(Command.DISCONNECT.toString())) {
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

	private synchronized boolean isConnected() {

		try {
			clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
			writer = new PrintWriter(clientSocket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			System.out.println("Socket opened successfully.");

			SpotifyClientRunnable clientRunnable = new SpotifyClientRunnable(clientSocket);

			new Thread(clientRunnable).start();
			return true;

		} catch (IOException e) {

			System.out.println("Cannot connect to server on host: " + SERVER_HOST + "on port: " + SERVER_PORT
					+ ", make sure that the server is started");
		}

		return false;
	}

}