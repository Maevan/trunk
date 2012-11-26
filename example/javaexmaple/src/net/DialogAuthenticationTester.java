package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.URL;

public class DialogAuthenticationTester {
	public static void main(String[] args) throws Exception {
		Authenticator.setDefault(DialogAuthenticator.getInstance());
		URL url = new URL("http://localhost:8080/manager/html");
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.err.println(line);
		}
	}
}
