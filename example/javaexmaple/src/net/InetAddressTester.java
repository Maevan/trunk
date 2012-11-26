package net;

import java.net.InetAddress;

public class InetAddressTester {
	public static void main(String[] args) throws Exception {
		InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
		for (InetAddress address : addresses) {
			System.err.println(address);
		}
	}
}
