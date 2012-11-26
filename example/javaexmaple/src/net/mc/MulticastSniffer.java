package net.mc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSniffer implements Runnable {
	private int port;
	private String server;

	public MulticastSniffer(int port, String server) {
		this.port = port;
		this.server = server;
	}

	@Override
	public void run() {
		InetAddress group = null;
		try {
			group = InetAddress.getByName(server);
			MulticastSocket ms = new MulticastSocket(port);

			ms.joinGroup(group);

			byte[] buffer = new byte[8192];

			while (true) {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				ms.receive(dp);

				String s = new String(dp.getData());
				System.err.println(Thread.currentThread().getId() + " get a data :" + s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
