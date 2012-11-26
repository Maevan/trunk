package io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class RandomAccessStreamChannelTester {
	public static void main(String[] args) throws IOException {
		String detail = "";
		RandomAccessFile stream = new RandomAccessFile(new File("D:/lm.txt"), "r");
		FileChannel channel = stream.getChannel();

		int bufferSize = 267;
		byte[] data = new byte[bufferSize];
		long length = stream.length();
		long pos = length - bufferSize;
		int loopCount = 10;

		ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
		stream.seek(pos);

		while (loopCount-- != 0 && channel.read(buffer) != -1) {
			buffer.clear();
			buffer.get(data);
			detail = new String(data, 0, (int) (stream.getFilePointer() - pos)) + detail;

			buffer.rewind();
			pos -= bufferSize * 2;
			stream.seek(pos);
		}

		buffer.clear();
		stream.close();
		System.err.println(detail);
	}
}
