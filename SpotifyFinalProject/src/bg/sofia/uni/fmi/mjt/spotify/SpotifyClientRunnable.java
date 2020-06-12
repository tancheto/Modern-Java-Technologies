package bg.sofia.uni.fmi.mjt.spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;

public class SpotifyClientRunnable implements Runnable {

	private Socket clientSocket;
	private BufferedReader reader;

	SpotifyClientRunnable(Socket clientSocket) {
		try {

			this.clientSocket = clientSocket;
			this.reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			
		} catch (IOException e) {

			System.out.println("Problem with client socket ocurred.");
		}
	}

	@Override
	public void run() {

		while (true) {

			if (clientSocket.isClosed()) {

				System.out.println("Client socket is closed, stop waiting for server messages.");
				return;
			}

			try {
				responseHandler();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	private void responseHandler() throws IOException {

		String response = reader.readLine();

		if (response.equals("streaming")) {

			playTheSong();

		}

		System.out.println(response);
	}

	private void playTheSong() {

		String audio;
		String[] tokens;

		try {

			while (true) {

				if ((audio = reader.readLine()) != null) {

					tokens = audio.split("\\s+");
					break;
				}
			}

			AudioFormat.Encoding encoding = new AudioFormat.Encoding(tokens[0]);
			float sampleRate = Float.parseFloat(tokens[1]);
			int sampleSizeInBits = Integer.parseInt(tokens[2]);
			int channels = Integer.parseInt(tokens[3]);
			int frameSize = Integer.parseInt(tokens[4]);
			float frameRate = Float.parseFloat(tokens[5]);
			boolean isBigEndian = Boolean.parseBoolean(tokens[6]);

			System.out.println(encoding);
			System.out.println(sampleRate);
			System.out.println(sampleSizeInBits);
			System.out.println(channels);
			System.out.println(frameSize);
			System.out.println(frameRate);
			System.out.println(isBigEndian);

			AudioFormat decodedFormat = new AudioFormat(encoding, sampleRate, sampleSizeInBits, channels, frameSize,
					frameRate, isBigEndian);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
