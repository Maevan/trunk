package net.udp.server.necho;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPEchoServerWithChannels implements Runnable {
	public final static int DEFAULT_PORT = 7;
	public final static int MAX_PACKET_SIZE = 65507;

	private int port;
	private int size;

	public UDPEchoServerWithChannels() {
		this(DEFAULT_PORT, MAX_PACKET_SIZE);
	}

	public UDPEchoServerWithChannels(int port) {
		this(port, MAX_PACKET_SIZE);
	}

	public UDPEchoServerWithChannels(int port, int size) {
		this.port = port;
		this.size = size;
	}

	@Override
	public void run() {
		try {
			DatagramChannel channel = DatagramChannel.open();
			DatagramSocket socket = channel.socket();
			ByteBuffer buffer = ByteBuffer.allocateDirect(size);

			socket.bind(new InetSocketAddress(port));

			while (true) {
				SocketAddress client = channel.receive(buffer);
				buffer.flip();
				channel.send(buffer, client);
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
