package net.udp;

import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPPortScanner {
	public static void main(String[] args) {
		for (int port = 1; port <= 65535; port++) {
			try {
				DatagramSocket server = new DatagramSocket(port);
				server.close();
			} catch (SocketException e) {
				System.err.println("There is a server on port " + port + ".");
			}
		}
	}
}
