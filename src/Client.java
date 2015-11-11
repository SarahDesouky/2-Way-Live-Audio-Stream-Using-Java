import java.net.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine; 
import java.util.Scanner;

public class Client {

	static DatagramSocket clientSocket;
	static TargetDataLine microphone;

	public static void main(String args[]) throws Exception 
	{ 
		clientSocket = new DatagramSocket();
		String s;
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the destination's IP address:");
		s = in.nextLine();
		String [] ip = s.split("\\.");
		byte [] address =new byte[ip.length];
		for(int i=0;i<ip.length;i++){
			address[i] = (byte)Integer.parseInt(ip[i]);
		}
		InetAddress IPAddress = InetAddress.getByAddress(address);
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		microphone = (TargetDataLine) AudioSystem.getLine(info);
		microphone.open(format);
		new ClientThread().start();
		microphone.start();
		while(true) {			
			byte[] audioData = new byte[2048];
			microphone.read(audioData, 0, 2048);
			DatagramPacket packetToSend = new DatagramPacket(audioData, audioData.length, IPAddress, 9876); 
			clientSocket.send(packetToSend);
		}
	} 
}
