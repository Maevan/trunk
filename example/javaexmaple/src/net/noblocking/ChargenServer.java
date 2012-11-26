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

public class ChargenServer implements Runnable {
	final public static int DEFAULT_PORT = 19;

	@Override
	public void run() {
		byte[] rotation = new byte[95 * 2];

		for (byte i = ' '; i <= '~'; i++) {
			rotation[i - ' '] = i;
			rotation[i + 95 - ' '] = i;
		}

		ServerSocketChannel serverChannel = null;
		Selector selector = null;

		try {
			serverChannel = ServerSocketChannel.open();

			serverChannel.socket().bind(new InetSocketAddress(DEFAULT_PORT));
			serverChannel.configureBlocking(false);

			selector = Selector.open();

			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		boolean isLoop = true;

		while (isLoop) {
			try {
				selector.select();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Set<SelectionKey> keys = selector.selectedKeys();

			for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext();) {
				SelectionKey key = it.next();
				it.remove();
				try {
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();

						SocketChannel client = server.accept();
						client.configureBlocking(false);

						System.out.println("Accepted connection from " + client);

						ByteBuffer buffer = ByteBuffer.allocate(74);

						buffer.put(rotation, 0, 72);
						buffer.put((byte) '\r');
						buffer.put((byte) '\n');
						buffer.flip();
						client.register(selector, SelectionKey.OP_WRITE).attach(buffer);
					} else if (key.isWritable()) {
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						if (!buffer.hasRemaining()) {
							buffer.rewind();
							int first = buffer.get();
							buffer.rewind();
							int position = first - ' ' + 1;
							buffer.put(rotation, position, 72);
							buffer.put((byte) '\r');
							buffer.put((byte) '\n');
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
