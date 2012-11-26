package net.mc;

public class Main {
	private final static int DEFAULT_PORT = 9875;
	private final static String DEFAULT_SERVER_HOST = "239.255.255.255";

	public static void main(String[] args) {
		new Thread(new MulticastSniffer(DEFAULT_PORT, DEFAULT_SERVER_HOST)).start();
		new Thread(new MulticastSniffer(DEFAULT_PORT, DEFAULT_SERVER_HOST)).start();
		new Thread(new MulticastSender(DEFAULT_PORT, DEFAULT_SERVER_HOST)).start();
	}
}
