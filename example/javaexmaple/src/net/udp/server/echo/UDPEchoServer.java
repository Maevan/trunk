package net.udp.server.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

import net.udp.server.UDPServer;

public class UDPEchoServer extends UDPServer {
	public final static int DEFAULT_PORT = 7;

	public UDPEchoServer() throws SocketException {
		this(DEFAULT_PORT);
	}

	public UDPEchoServer(int port) throws SocketException {
		super(port);
	}

	@Override
	public void respond(DatagramPacket request) {
		DatagramPacket outgoing = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
		
		try {
			socket.send(outgoing);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
