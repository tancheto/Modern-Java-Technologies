package bg.sofia.uni.fmi.mjt.spotify;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionRunnable implements Runnable {

	private Socket socket;

	ClientConnectionRunnable(Socket socket) {

		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			CommunicationHandler communicationUtility = new CommunicationHandler(socket.getOutputStream(), socket.getInputStream());

			communicationUtility.initialise();
			communicationUtility.communicate();

			socket.close();
		} catch (IOException e) {

			System.err.println("Error closing the socket.");
		}

	}

}