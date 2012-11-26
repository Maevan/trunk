package io;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTester {
	public static void main(String[] args) throws Exception {
		File txtFile = new File("D:/testdata.txt");
		FileChannel channel = new FileInputStream(txtFile).getChannel();
		MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, txtFile.length());

		byte[] b = new byte[1024];
		System.err.println(buffer.get(b, 0, b.length));
		System.err.println(new String(b, "utf-8"));
		channel.close();
	}
}
