package net.urlc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class SourceViewer2 {
	public static final String DEFAULT_HOST = "http://www.baidu.com";

	public static void main(String[] args) throws IOException {
		URL url = new URL(DEFAULT_HOST);

		URLConnection connection = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"GBK"));

		String line = null;
		while ((line = reader.readLine()) != null) {
			System.err.println(line);
		}
	}
}
