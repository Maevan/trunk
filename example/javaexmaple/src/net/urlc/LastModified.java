package net.urlc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;

public class LastModified {

	public static final String domain = "http://www.ibiblio.org/xml";

	public static void main(String[] args) {
		try {
			URL u = new URL(domain);
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod("HEAD");
			System.out.println(u + "was last modified at " + new Date(connection.getLastModified()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
