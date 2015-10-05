import java.net.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine; 
public class Client {

	static DatagramSocket clientSocket;
	static TargetDataLine microphone;


	public static void main(String args[]) throws Exception 
	{ 
		clientSocket = new DatagramSocket(); 
		InetAddress IPAddress = InetAddress.getByName("localhost"); 
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		microphone = (TargetDataLine) AudioSystem.getLine(info);
		microphone.open(format);
		microphone.start();

		while(true) {
			byte[] audioData = new byte[microphone.getBufferSize()];
			microphone.read(audioData, 0, 4096); 
			DatagramPacket packetToSend = new DatagramPacket(audioData, audioData.length, IPAddress, 9876); 
			clientSocket.send(packetToSend);
		}
	} 
}
