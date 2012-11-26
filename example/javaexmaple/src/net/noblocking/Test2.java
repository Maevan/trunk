package net.noblocking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Test2 {
	public static void main(String[] args) throws IOException {
		new Thread(new EchoServer()).start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Selector sender = Selector.open();
		Selector receiver = Selector.open();

		SocketChannel channel = SocketChannel.open(new InetSocketAddress(InetAddress.getLocalHost(), 8087));
		channel.configureBlocking(false);
		channel.register(receiver, SelectionKey.OP_READ);
		channel.register(sender, SelectionKey.OP_WRITE);
		
	}
}
