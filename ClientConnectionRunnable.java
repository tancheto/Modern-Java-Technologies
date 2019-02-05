import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientConnectionRunnable implements Runnable {

	private Socket socket;

	ClientConnectionRunnable(Socket socket) {

		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			CommunicationUtility communicationUtility = new CommunicationUtility(socket);

			communicationUtility.initialise();
			communicationUtility.communicate();

			socket.close();
		} catch (IOException e) {

			System.err.println("Error closing the socket.");
		}

	}

}
