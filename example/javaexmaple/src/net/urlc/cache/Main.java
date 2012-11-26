package net.urlc.cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ResponseCache;
import java.net.URL;
import java.net.URLConnection;

public class Main {
	public static void main(String[] args) throws IOException {
		ResponseCache.setDefault(new MemoryResponseCache());

		for (int i = 0; i < 3; i++) {
			URLConnection connection = new URL("http://www.cnnic.cn").openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			System.err.println(reader.readLine());

			reader.close();
		}
	}
}
