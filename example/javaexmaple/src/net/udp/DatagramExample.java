package net.udp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DatagramExample {
	public static void main(String[] args) {
		String s = "This is a test";
		byte[] data = s.getBytes();

		try {
			InetAddress address = InetAddress.getLocalHost();
			int port = 7;
			
			DatagramPacket dp = new DatagramPacket(data, data.length, address, port);
			System.out.println("This packet is addressed to " + dp.getAddress() + " on port " + dp.getPort());
			System.out.println("There are " + dp.getLength() + " bytes of data in the packet");
			System.out.println(new String(dp.getData(), dp.getOffset(), dp.getLength()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
