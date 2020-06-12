package bg.sofia.uni.fmi.mjt.spotify;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.BeforeClass;
import org.junit.Test;

public class CommunicationHandlerTest {	

	@Test
	public void loginTest() {
		try (PrintWriter inputWriter = new PrintWriter("InputTestFile.txt");
				BufferedReader inputReader = new BufferedReader(new FileReader("InputTestFile.txt"));
				PrintWriter outputWriter = new PrintWriter("OutputTestFile.txt");
				BufferedReader outputReader = new BufferedReader(new FileReader("OutputTestFile.txt"));) {

			inputWriter.println("login email pass");
			inputWriter.println("disconnect");
			inputWriter.close();

			CommunicationHandler communicationHandler = new CommunicationHandler(outputWriter, inputReader);
			communicationHandler.initialise();

			outputWriter.close();
			inputReader.close();

			String line = outputReader.readLine();
			assertEquals("Successfully logged in!", line);

		} catch (FileNotFoundException e) {
			System.err.println("InputTest or OutputTest file is not found");
			fail();
		} catch (IOException e) {
			System.err.println("Undefined behavior");
			fail();
		}
	}
	
	@Test
	public void failedLoginTest() {
		try (PrintWriter inputWriter = new PrintWriter("InputTestFile.txt");
				BufferedReader inputReader = new BufferedReader(new FileReader("InputTestFile.txt"));
				PrintWriter outputWriter = new PrintWriter("OutputTestFile.txt");
				BufferedReader outputReader = new BufferedReader(new FileReader("OutputTestFile.txt"));) {

			inputWriter.println("login email passssssssss");
			inputWriter.println("disconnect");
			inputWriter.close();

			CommunicationHandler communicationHandler = new CommunicationHandler(outputWriter, inputReader);
			communicationHandler.initialise();

			outputWriter.close();
			inputReader.close();

			String line = outputReader.readLine();
			assertEquals("Wrong Email or Password! Try again.", line);

		} catch (FileNotFoundException e) {
			System.err.println("InputTest or OutputTest file is not found");
			fail();
		} catch (IOException e) {
			System.err.println("Undefined behavior");
			fail();
		}
	}
	
	@Test
	public void failedRegisterTest() {
		try (PrintWriter inputWriter = new PrintWriter("InputTestFile.txt");
				BufferedReader inputReader = new BufferedReader(new FileReader("InputTestFile.txt"));
				PrintWriter outputWriter = new PrintWriter("OutputTestFile.txt");
				BufferedReader outputReader = new BufferedReader(new FileReader("OutputTestFile.txt"));) {

			inputWriter.println("register email pass");
			inputWriter.println("disconnect");
			inputWriter.close();

			CommunicationHandler communicationHandler = new CommunicationHandler(outputWriter, inputReader);
			communicationHandler.initialise();

			outputWriter.close();
			inputReader.close();

			String line = outputReader.readLine();
			assertEquals("Email: email is already used!", line);

		} catch (FileNotFoundException e) {
			System.err.println("InputTest or OutputTest file is not found");
			fail();
		} catch (IOException e) {
			System.err.println("Undefined behavior");
			fail();
		}
	}
	
	@Test
	public void addSongToPlaylistTest() {
		try (PrintWriter inputWriter = new PrintWriter("InputTestFile.txt");
				BufferedReader inputReader = new BufferedReader(new FileReader("InputTestFile.txt"));
				PrintWriter outputWriter = new PrintWriter("OutputTestFile.txt");
				BufferedReader outputReader = new BufferedReader(new FileReader("OutputTestFile.txt"));) {

			inputWriter.println("login email pass");
			inputWriter.println("create-playlist test");
			inputWriter.println("add-song-to Coldplay-Adventure_Of_A_Lifetime test");
			inputWriter.println("disconnect");
			inputWriter.close();

			CommunicationHandler communicationHandler = new CommunicationHandler(outputWriter, inputReader);
			communicationHandler.initialise();
			communicationHandler.communicate();

			outputWriter.close();
			inputReader.close();

			String line = outputReader.readLine();		
			line = outputReader.readLine();			
			line = outputReader.readLine();
			assertEquals("Successfully added song to the playlist!", line);

		} catch (FileNotFoundException e) {
			System.err.println("InputTest or OutputTest file is not found");
			fail();
		} catch (IOException e) {
			System.err.println("Undefined behavior");
			fail();
		}
	}
	
	
	@Test
	public void searchTest() {
		try (PrintWriter inputWriter = new PrintWriter("InputTestFile.txt");
				BufferedReader inputReader = new BufferedReader(new FileReader("InputTestFile.txt"));
				PrintWriter outputWriter = new PrintWriter("OutputTestFile.txt");
				BufferedReader outputReader = new BufferedReader(new FileReader("OutputTestFile.txt"));) {

			inputWriter.println("login email pass"); 
			inputWriter.println("search enr");
			inputWriter.println("disconnect");			
			inputWriter.close();

			CommunicationHandler communicationHandler = new CommunicationHandler(outputWriter, inputReader);
			communicationHandler.initialise();
			communicationHandler.communicate();

			outputWriter.close();
			inputReader.close();
			
			String line = outputReader.readLine();
			
			line = outputReader.readLine();
			assertEquals("Enrique_Iglesias_Bad_Bunny-EL_BANO", line);
			
			line = outputReader.readLine();
			assertEquals("Enrique_Iglesias-SUBEME_LA_RADIO_REMIX", line);

		} catch (FileNotFoundException e) {
			System.err.println("InputTest or OutputTest file is not found");
			fail();
		} catch (IOException e) {
			System.err.println("Undefined behavior");
			fail();
		}
	}

}
