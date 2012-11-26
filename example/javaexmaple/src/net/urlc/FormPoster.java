package net.urlc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import net.QueryString;

public class FormPoster {
	private URL url;
	private QueryString query = null;

	public FormPoster(URL url) {
		if (!url.getProtocol().toLowerCase().startsWith("http")) {
			throw new IllegalArgumentException("Posting only works for http URLs");
		}
		this.url = url;
		this.query = new QueryString("random", UUID.randomUUID().toString());
	}

	public void add(String name, String value) {
		query.add(name, value);
	}

	public URL getURL() {
		return this.url;
	}

	public InputStream post() throws IOException {
		URLConnection uc = url.openConnection();

		uc.setDoOutput(true);

		OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");

		out.write(query.toString());
		out.write("\r\n");
		out.flush();
		out.close();

		return uc.getInputStream();
	}

	public static void main(String[] args) {
		URL url = null;
		try {
			url = new URL("http://www.cafeaulait.org/books/jnp3/postquery.phtml");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		FormPoster poster = new FormPoster(url);

		poster.add("name", "Elliotte Rusty Harold");
		poster.add("email", "elharo@metalab.unc.edu");
		try {
			InputStreamReader r = new InputStreamReader(poster.post());
			int c;
			while ((c = r.read()) != -1) {
				System.err.print((char) c);
			}
			System.out.println();
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
