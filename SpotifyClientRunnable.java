import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SpotifyClientRunnable implements Runnable {

	private Socket clientSocket;
	private SpotifyClient client;

	SpotifyClientRunnable(Socket clientSocket, SpotifyClient client) {

		this.clientSocket = clientSocket;
		this.client = client;
	}

	@Override
	public void run() {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

			while (true) {

				if (clientSocket.isClosed()) {

					System.out.println("Client socket is closed, stop waiting for server messages.");
					return;
				}
				
				responseHandler(reader);
			}
		} catch (IOException e) {

			System.out.println("Problem with client socket ocurred.");
		}

	}
	
	void responseHandler(BufferedReader reader) throws IOException{
		
		String response = reader.readLine();
		
		String[] tokens = response.split("\\s+");
		
		if(tokens.length == 7) {
			client.setServerResponse(response);
		}
		
		System.out.println(response);
	}

}
