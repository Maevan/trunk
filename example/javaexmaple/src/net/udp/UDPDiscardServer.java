package net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPDiscardServer implements Runnable {
	public void run() {
		byte[] buffer = new byte[65507];
		DatagramSocket server = null;
		DatagramPacket packet = null;
		try {
			server = new DatagramSocket(10001);
			packet = new DatagramPacket(buffer, buffer.length);
			while (true) {
				server.receive(packet);
				String s = new String(packet.getData(), 0, packet.getLength(), "UTF-8");

				System.out.println(packet.getAddress() + " at port " + packet.getPort() + " says " + s);
				packet.setLength(buffer.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
