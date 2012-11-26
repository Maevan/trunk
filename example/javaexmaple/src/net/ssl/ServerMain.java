package net.ssl;

public class ServerMain {
	public static void main(String[] args) {
		new Thread(new SSLServerSocketTester()).start();
	}
}
