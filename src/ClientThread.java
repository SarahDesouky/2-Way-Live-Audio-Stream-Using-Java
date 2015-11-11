import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class ClientThread extends Thread {

	//receive 

	static SourceDataLine speakers;

	public ClientThread(){

	}

	public void run() {

		try {
			DatagramSocket serverSocket = new DatagramSocket(9877); 

			AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
			speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			speakers.open(format);
			speakers.start();
			while(true) 
			{ 
				byte[] receiveData = new byte[2048];
				DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length); 
				serverSocket.receive(receivedPacket); 
				byte[] AudioReceived = receivedPacket.getData(); 
				speakers.write(AudioReceived, 0, receivedPacket.getLength());
			}
		}catch(Exception e){}
	}

}
