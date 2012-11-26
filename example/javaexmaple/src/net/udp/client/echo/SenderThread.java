package net.udp.client.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SenderThread implements Runnable {

	private InetAddress server;
	private DatagramSocket socket;
	private boolean stopped = false;
	private int port;

	public SenderThread(InetAddress address, int port) throws SocketException {
		this.server = address;
		this.port = port;
		this.socket = new DatagramSocket();
		this.socket.connect(address, port);
	}

	public void halt() {
		this.stopped = true;
	}

	public DatagramSocket getSocket() {
		return socket;
	}

	@Override
	public void run() {
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true) {
				System.err.print("请输入:");
				if (stopped) {
					return;
				}
				String theLine = userInput.readLine();
				if (theLine.equals(".")) {
					break;
				}

				byte[] data = theLine.getBytes();

				DatagramPacket output = new DatagramPacket(data, data.length, server, port);
				socket.send(output);
				Thread.yield();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
