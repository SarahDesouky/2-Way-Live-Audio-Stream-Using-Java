import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class ServerThread extends Thread {

	//send
	static DatagramSocket clientSocket;
	static TargetDataLine microphone;
	public ServerThread(){

	}

	public void run(){
		try {
			clientSocket = new DatagramSocket(); 
			//byte [] address = {(byte)192, (byte)168, (byte)1, (byte)10};
			//InetAddress IPAddress = InetAddress.getByAddress(address);
			InetAddress IPAddress = Server.Ip;
			System.out.println(Server.Ip);
			AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			microphone = (TargetDataLine) AudioSystem.getLine(info);
			microphone.open(format);
			microphone.start();

			while(true) {
				byte[] audioData = new byte[4096];
				microphone.read(audioData, 0, 4096); 
				DatagramPacket packetToSend = new DatagramPacket(audioData, audioData.length, IPAddress, 9877); 
				clientSocket.send(packetToSend);
			}
		}
		catch(Exception e) {}
	}

}
