package io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessStreamTester {
	public static void main(String[] args) {
		RandomAccessFile stream = null;
		ByteArrayOutputStream out = null;
		File file = null;
		try {
			file = new File("D:/test.txt");
			stream = new RandomAccessFile(file, "rw");
			out = new ByteArrayOutputStream();

			stream.seek(stream.length() - stream.length() / 100);
			byte[] b = new byte[1024];

			int bcount = 0;
			while ((bcount = stream.read(b)) != -1) {
				out.write(b, 0, bcount);
			}
			System.out.println(new String(out.toByteArray(), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
