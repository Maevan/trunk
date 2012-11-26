package net.ssl;

public class SocketMain {
	public static void main(String[] args) {
		new Thread(new SSLSocketTester()).start();
	}
}
