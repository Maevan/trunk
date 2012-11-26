package net.udp.client.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ReceiverThread implements Runnable {
	private DatagramSocket socket;
	private boolean stopped = false;

	public ReceiverThread(DatagramSocket ds) throws SocketException {
		this.socket = ds;
	}

	public void halt() {
		this.stopped = true;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[65507];
		while (true) {
			if (stopped) {
				socket.close();
			}
			DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(dp);
				String s = new String(dp.getData(), 0, dp.getLength());

				System.out.println(s);
				Thread.yield();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
