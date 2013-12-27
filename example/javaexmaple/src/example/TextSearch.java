package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TextSearch {

	public static void main(String[] args) throws IOException {
		System.err.println(count("项目服务器地址", "GBK", new FileInputStream("D:/服务器地址.txt")));
	}

	public static int count(String key, String charset, InputStream in) throws IOException {
		int count = 0;
		byte[] orign = key.getBytes(charset);
		byte[] buffer = new byte[orign.length < 1024 ? 1024 : 1024 + orign.length];
		int c = -1;
		int off = 0;

		while ((c = in.read(buffer, off, buffer.length - off)) != -1) {
			if (off != 0) {
				c += off;
			}
			off = 0;
			for (int i = 0; i < c;) {
				if (orign[0] == buffer[i]) {
					if (c - i < orign.length) {
						System.arraycopy(buffer, i, buffer, 0, c - i);
						off = c - i;
						break;
					}
					boolean isFind = true;
					for (int j = 0; j < orign.length; j++) {
						if (orign[j] != buffer[i + j]) {
							i += j;
							isFind = false;
							break;
						}
					}
					if (isFind) {
						count += 1;
						i += orign.length;
					}
				} else {
					i++;
				}
			}
		}

		return count;
	}
}
