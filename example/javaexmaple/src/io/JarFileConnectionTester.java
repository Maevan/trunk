package io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;

public class JarFileConnectionTester {
	public static void main(String[] args) throws Exception {
		System.err.println(Thread.currentThread().getClass().getResource("/").getPath());
		JarURLConnection connection = (JarURLConnection) new URL("jar:file:///g:/mapbar-mgisx-api-proxy-0.0.1-SNAPSHOT.jar!/com/mapbar/mgisx/apiproxy/config/api_server_config.properties")
				.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.err.println(line);
		}
	}
}
