package net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetworkInterfaceTester {
	public static void main(String[] args) throws Exception {
		Enumeration<NetworkInterface> enums = NetworkInterface
				.getNetworkInterfaces();
		while (enums.hasMoreElements()) {
			NetworkInterface inet = enums.nextElement();
			System.out.println(inet.getName());

			Enumeration<InetAddress> addrenums = inet.getInetAddresses();
			while (addrenums.hasMoreElements()) {
				System.out.println("    "
						+ addrenums.nextElement().getHostAddress());
			}
		}
	}
}
