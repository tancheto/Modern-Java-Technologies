import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SpotifyServer {

	public static final int SERVER_PORT = 8080;

	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

			while (true) {

				Socket clientSocket = serverSocket.accept();

				ClientConnectionRunnable runnable = new ClientConnectionRunnable(clientSocket);

				new Thread(runnable).start();

			}

		} catch (IOException e) {
			System.err.println("Maybe another server is running on port " + SERVER_PORT);
			throw new RuntimeException(e);
		}
	}
}
