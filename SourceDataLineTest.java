import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SourceDataLineTest {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		SourceDataLine line = null;
		//FloatControl volumeControl = null;
		//BooleanControl control = null;
		AudioInputStream din = null;

		File file = new File("./resources/Arctic_Monkeys-Do_I_Wanna_Know.wav");
		AudioInputStream in = AudioSystem.getAudioInputStream(file);
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
				line.write(data, 0, nBytesRead);
			}
			// Stop
			line.drain();
			line.stop();
			line.close();
			din.close();
		}

		/*
		 * AudioInputStream stream = AudioSystem.getAudioInputStream(new
		 * File("./resources/Enrique_Iglesias_Bad_Bunny-EL_BANO.wav")); SourceDataLine
		 * dataLine = AudioSystem.getSourceDataLine(stream.getFormat());
		 * dataLine.open(); dataLine.start();
		 * 
		 * while (true) {
		 * 
		 * System.out.println("eho"); }
		 */
	}
}
