package net.udp.client.echo;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPEchoClient implements Runnable {
	public static final int DEFAULT_PORT = 7;
	public static final String DEFAULT_HOSTNAME = "localhost";

	public InetAddress server;
	public int port;

	public UDPEchoClient() throws UnknownHostException {
		this(DEFAULT_HOSTNAME);
	}

	public UDPEchoClient(String hostname) throws UnknownHostException {
		this(hostname, DEFAULT_PORT);
	}

	public UDPEchoClient(String hostname, int port) throws UnknownHostException {
		this.server = InetAddress.getByName(hostname);
		this.port = port;
	}

	@Override
	public void run() {
		try {
			SenderThread sender = new SenderThread(server, port);
			ReceiverThread receiver = new ReceiverThread(sender.getSocket());

			new Thread(sender).start();
			new Thread(receiver).start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
