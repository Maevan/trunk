package net.udp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public abstract class UDPServer implements Runnable {
	private int bufferSize;
	protected DatagramSocket socket;

	public UDPServer(int port, int bufferSize) throws SocketException {
		this.bufferSize = bufferSize;
		this.socket = new DatagramSocket(port);
	}

	public UDPServer(int port) throws SocketException {
		this(port, 8192);
	}

	@Override
	public void run() {
		byte[] buffer = new byte[bufferSize];
		while (true) {
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(incoming);
				respond(incoming);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public abstract void respond(DatagramPacket request);

}
