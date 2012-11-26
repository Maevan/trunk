package net.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPiscardClient implements Runnable {
	public void run() {
		BufferedReader reader = null;
		DatagramSocket socket = null;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			socket = new DatagramSocket(10000, InetAddress.getLocalHost());
			while (true) {
				String message = reader.readLine();
				if (message.equals(".")) {
					break;
				}
				message += "\r\n";
				byte[] data = message.getBytes("UTF-8");
				socket.send(new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 10001));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
