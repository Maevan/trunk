package net.ssl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import static net.ssl.MySSLSocketFactory.*;

public class SSLSocketTester implements Runnable {
	private int port = 9090;
	public String protocol = "SSL";
	public String algorithm = "SunX509";
	public String type = "JKS";
	public String keyPath = "net/ssl/keys/jnp3e.keys";
	public char[] pwd = "4845849".toCharArray();

	public SSLSocketTester() {

	}

	public SSLSocketTester(int port, String protocol, String algorithm, String type, String keyPath, char[] pwd) {
		this.port = port;
		this.protocol = protocol;
		this.algorithm = algorithm;
		this.type = type;
		this.keyPath = keyPath;
		this.pwd = pwd;
	}

	public void run() {
		try {
			SSLSocket socket = getSSLSocket(InetAddress.getLocalHost(), port, getSSLContext(protocol, algorithm, type, keyPath, pwd));

			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			Scanner scanner = new Scanner(System.in);
			ReaderTask readerTask = new ReaderTask(socket.getInputStream());

			new Thread(readerTask).start();
			while (true) {
				String line = scanner.nextLine();

				out.println(line);
				out.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class ReaderTask implements Runnable {
		private BufferedReader reader;

		public ReaderTask(InputStream in) {
			this.reader = new BufferedReader(new InputStreamReader(in));
		}

		public void run() {
			try {
				while (true) {
					System.err.println(reader.readLine());
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
