package net;

import java.net.InetAddress;
import java.util.Scanner;

public class HostLookup {
	public static void main(String[] args) {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				System.out.println(lookup(args[i]));
			}
		} else {
			Scanner scanner = new Scanner(System.in);
			System.out
					.println("Enter name and IP address. Enter \"exit\" to quit.");

			try {
				while (true) {
					String host = scanner.nextLine();
					if (host.equalsIgnoreCase("exit")
							|| host.equalsIgnoreCase("quit")) {
						break;
					}
					System.out.println(lookup(host));
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public static String lookup(String host) {
		InetAddress node;
		try {
			node = InetAddress.getByName(host);
		} catch (Exception e) {
			return "Cannot find host " + host;
		}
		if (isHostName(host)) {
			return node.getHostAddress();
		} else {
			return node.getHostName();
		}
	}

	public static boolean isHostName(String host) {
		if (host.indexOf(':') != -1) {
			return false;
		}
		char[] ca = host.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			if (!Character.isDigit(ca[i])) {
				if (ca[i] != '.') {
					return true;
				}
			}
		}
		return false;
	}
}
