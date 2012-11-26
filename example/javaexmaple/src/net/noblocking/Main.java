package net.noblocking;

import java.nio.ByteBuffer;

public class Main {
	public static void main(String[] args) {
		// new Thread(new ChargenServer()).start();
		// new Thread(new ChargenSocket()).start();
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.put((byte) 0);
		buffer.put((byte) 1);
		buffer.put((byte) 2);
		buffer.put((byte) 3);
		buffer.put((byte) 4);
		buffer.put((byte) 5);
		buffer.put((byte) 6);
		buffer.put((byte) 7);

		for (int i = 0; i < buffer.capacity(); i++) {
			System.out.print(" " + buffer.get(i));
		}
		System.out.println();
		ByteBuffer cbuffer = buffer.duplicate();

		for (int i = 0; i < cbuffer.capacity(); i++) {
			if (cbuffer.get(i) % 2 == 0) {
				cbuffer.put(i, (byte) (cbuffer.get(i) + 10));
			}
			System.out.print(" " + cbuffer.get(i));
		}
		System.out.println();
		cbuffer.position(2);

		ByteBuffer sbuffer = cbuffer.slice();
		for (int i = 0; i < sbuffer.capacity(); i++) {
			System.out.print(" " + sbuffer.get(i));
		}
	}
}
