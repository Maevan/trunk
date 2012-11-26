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

public class Intgen {
	public static int DEFAULT_PORT = 1919;

	public static void main(String[] args) {
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
			e.printStackTrace();
			return;
		}

		while (true) {
			try {
				selector.select();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			Set<SelectionKey> keys = selector.selectedKeys();

			for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext();) {
				SelectionKey key = it.next();
				it.remove();

				try {
					if (key.isAcceptable()) {
						ServerSocketChannel s = (ServerSocketChannel) key.channel();
						SocketChannel client = s.accept();
						ByteBuffer buffer = ByteBuffer.allocate(4);

						System.err.println("Accepted connection from " + client);

						buffer.putInt(0);
						buffer.flip();

						client.configureBlocking(false);
						client.register(selector, SelectionKey.OP_WRITE).attach(buffer);
					} else if (key.isWritable()) {
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						if (buffer.hasRemaining()) {
							buffer.rewind();
							int value = buffer.getInt();
							buffer.clear();
							buffer.putInt(++value);
							buffer.flip();
						}
						client.write(buffer);
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
