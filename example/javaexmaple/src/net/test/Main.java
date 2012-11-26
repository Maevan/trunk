package net.test;

public class Main {
	public static void main(String[] args){
		new Thread(new ServerTester()).start();
		new Thread(new SocketTester()).start();
	}
}
