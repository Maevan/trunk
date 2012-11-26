package net.noblocking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChargenSocket implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(1000 * 3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SocketAddress address = null;
		Selector selector = null;

		try {
			selector = Selector.open();
			address = new InetSocketAddress(InetAddress.getLocalHost(), 19);
			for (int i = 0; i < 10; i++) {
				SocketChannel socket = SocketChannel.open(address);
				ByteBuffer buffer = ByteBuffer.allocate(74);

				socket.configureBlocking(false);
				socket.register(selector, SelectionKey.OP_READ).attach(buffer);
			}
			
			while (selector.keys().size() != 0) {
				selector.select();
				
				Set<SelectionKey> keys = selector.selectedKeys();
				for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext();) {
					SelectionKey key = it.next();
					it.remove();
					
					SocketChannel socket = (SocketChannel) key.channel();
					ByteBuffer buffer = (ByteBuffer) key.attachment();
					if (buffer.position() >= 74) {
						buffer.clear();
					} else {
						socket.read(buffer);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
