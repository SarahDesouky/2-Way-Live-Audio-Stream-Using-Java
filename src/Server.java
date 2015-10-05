import java.net.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine; 

public class Server {
	
	static SourceDataLine speakers;
	
	public static void main(String args[]) throws Exception 
	{ 
		DatagramSocket serverSocket = new DatagramSocket(9876); 
		 
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        speakers.open(format);
        speakers.start();
		while(true) 
		{ 
			byte[] receiveData = new byte[4096];
			DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length); 
			serverSocket.receive(receivedPacket); 
			byte[] AudioReceived = receivedPacket.getData(); 
			speakers.write(AudioReceived, 0, receivedPacket.getLength());
		} 
	} 
}
