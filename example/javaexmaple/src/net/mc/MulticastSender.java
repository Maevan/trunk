package net.mc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender implements Runnable {
	private int port;
	private String server;

	public MulticastSender(int port, String server) {
		this.port = port;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			InetAddress address = InetAddress.getByName(server);
			byte[] data = "Here's some multicast data\r\n".getBytes();
			DatagramPacket dp = new DatagramPacket(data, data.length, address, port);

			MulticastSocket ms = new MulticastSocket();
			ms.joinGroup(address);
			for (int i = 1; i < 10; i++) {
				ms.send(dp);
			}
			ms.leaveGroup(address);
			ms.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
