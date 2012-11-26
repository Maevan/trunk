package net.noblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer implements Runnable {
	public static int DEFAULT_PORT = 8087;

	public void run() {
		System.err.println("Listening for connections on port " + DEFAULT_PORT);

		ServerSocketChannel server;
		Selector selector;

		try {
			selector = Selector.open();
			server = ServerSocketChannel.open();

			server.socket().bind(new InetSocketAddress(DEFAULT_PORT));
			server.configureBlocking(false);
			server.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			return;
		}
		while (true) {
			try {
				selector.select();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

			Set<SelectionKey> keys = selector.selectedKeys();
			for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext();) {
				SelectionKey key = it.next();
				it.remove();
				try {
					if (key.isAcceptable()) {
						server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();

						System.err.println("Accepted connection from " + client);

						client.configureBlocking(false);
						client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ).attach(ByteBuffer.allocate(100));
					} else {
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						if (key.isReadable()) {
							client.read(buffer);
						}
						if (key.isWritable()) {
							buffer.flip();
							client.write(buffer);
							buffer.compact();
							System.err.println("写完");
						}
					}
				} catch (IOException e) {
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException e1) {}
				}
			}
		}
	}
}
