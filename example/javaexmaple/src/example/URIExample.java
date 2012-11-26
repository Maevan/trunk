package example;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLConnection;

public class URIExample {
	public static void main(String[] args) {
		URI uri = null;
		URLConnection connection = null;// 获取URLConnection对象
		BufferedInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			uri = new URI(IMAGE_URL);// 创建URI对象
			connection = uri.toURL().openConnection();
			in = new BufferedInputStream(connection.getInputStream());
			out = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int len;

			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}

			byte[] data = out.toByteArray();
			// 这里进行你想要的其他操作
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {}
			}
		}
	}

	static final String IMAGE_URL = "http://img.hb.aicdn.com/bd631af751b83488838dfb0f8a7a986956409bf0dbc4-itg5Hz_fw554";
}
