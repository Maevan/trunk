package net.udp.main;

import java.io.IOException;

import net.udp.client.echo.UDPEchoClient;
import net.udp.server.echo.UDPEchoServer;

public class EchoTest {
	public static void main(String[] args) throws IOException {
		int port = 9999;
		UDPEchoServer server = new UDPEchoServer(port);
		UDPEchoClient client = new UDPEchoClient("localhost", port);

		new Thread(server).start();
		new Thread(client).start();
	}
}
