package io;

import java.nio.ByteBuffer;

public class ByteBufferExample {
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		print(buffer);
		buffer.putInt(4);// 放入一个4个字节的整型 并且使position向右移四字节
		print(buffer);
		buffer.rewind();// 将position重置为0 使mark失效
		print(buffer);
		System.err.println("	" + buffer.getInt());// 取出一个4个字节的整型 并且使position向右移四字节
		buffer.clear();// 将position置为0 将limit设置为当前capacity 取消掉mark
		buffer.putInt(4);// 放入一个4个字节的整型
		buffer.putInt(8);// 放入一个4个字节的整型
		buffer.putInt(16);// 放入一个4个字节的整型
		buffer.flip();// 把position置为0 把当前位置设置为limit
		print(buffer);
		System.err.println("	" + buffer.getInt());
		print(buffer);
		buffer.compact();// 把position位置之前的数据删除掉后把position设置为remaining - position的位置 方便接下来写入数据并不会覆盖之前还未读取完的数据 另外取消掉limit
		print(buffer);
		buffer.putInt(32);
		buffer.flip();
		while (buffer.hasRemaining()) {
			System.err.println("	" + buffer.getInt());
		}
	}

	public static void print(ByteBuffer buffer) {
		System.err.printf("capacity: %d,remaining: %d,limit: %d,position: %d\r\n", buffer.capacity(), buffer.remaining(), buffer.limit(), buffer.position());
	}
}
