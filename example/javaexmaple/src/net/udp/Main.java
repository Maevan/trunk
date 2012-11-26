package net.udp;

public class Main {
	public static void main(String[] args) {
		new Thread(new UDPDiscardServer()).start();
		new Thread(new UDPiscardClient()).start();
	}
}
