package net.urlc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SourceViewer3 {
	static final String domain = "http://www.oreilly.com";

	public static void main(String[] args) {
		try {
			HttpURLConnection connection = (HttpURLConnection) (new URL(domain).openConnection());
			String response = connection.getResponseMessage();
			int code = connection.getResponseCode();

			System.out.println("HTTP/1.x " + code + " " + response);

			for (int j = 1;; j++) {
				String header = connection.getHeaderField(j);
				String key = connection.getHeaderFieldKey(j);

				if (header == null || key == null) {
					break;
				}

				System.err.println(key + " : " + header);
			}

			Reader r = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));

			int c;
			
			while ((c = r.read()) != -1) {
				System.out.print((char) c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
