package net.ssl;

import static net.ssl.MySSLSocketFactory.getSSLContext;
import static net.ssl.MySSLSocketFactory.getSSLServerSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocket;

public class SSLServerSocketTester implements Runnable {
	private int port = 9090;
	private String protocol = "SSL";
	private String algorithm = "SunX509";
	private String type = "JKS";
	private String keyPath = "net/ssl/keys/jnp3e.keys";
	private char[] pwd = "4845849".toCharArray();
	private final LinkedList<String> messages = new LinkedList<String>();
	private final LinkedList<Socket> sockets = new LinkedList<Socket>();

	public SSLServerSocketTester() {}

	public SSLServerSocketTester(int port, String protocol, String algorithm, String type, String keyPath, char[] pwd) {
		this.port = port;
		this.protocol = protocol;
		this.algorithm = algorithm;
		this.type = type;
		this.keyPath = keyPath;
		this.pwd = pwd;
	}

	@Override public void run() {
		try {
			ExecutorService executor = Executors.newCachedThreadPool();
			SSLServerSocket server = getSSLServerSocket(port, getSSLContext(protocol, algorithm, type, keyPath, pwd));

			String[] supported = server.getEnabledCipherSuites();
			String[] anoncipherSuitesSupported = new String[supported.length];

			int numAnonCipherSuitesSupported = 0;

			for (int i = 0; i < supported.length; i++) {
				if (supported[i].indexOf("_anon_") > 0) {
					anoncipherSuitesSupported[numAnonCipherSuitesSupported++] = supported[i];
				}
			}

			String[] oldEnabled = server.getEnabledCipherSuites();
			String[] newEnabled = new String[oldEnabled.length + numAnonCipherSuitesSupported];

			System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
			System.arraycopy(anoncipherSuitesSupported, 0, newEnabled, oldEnabled.length, numAnonCipherSuitesSupported);

			server.setEnabledCipherSuites(newEnabled);

			new Thread(new SenderTask(messages, sockets)).start();

			while (true) {
				Socket socket = server.accept();

				synchronized (sockets) {
					sockets.addLast(socket);
					executor.execute(new ReceiverTask(socket, messages));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class SenderTask implements Runnable {
		private LinkedList<Socket> sockets;
		private LinkedList<String> messages;
		private ExecutorService executor = Executors.newFixedThreadPool(20);

		public SenderTask(LinkedList<String> messages, LinkedList<Socket> sockets) {
			this.messages = messages;
			this.sockets = sockets;
		}

		public void run() {
			try {
				while (true) {
					String message = null;
					synchronized (messages) {
						if (messages.isEmpty()) {
							messages.wait();
						}
						message = messages.removeFirst();
					}

					final String fmessage = message;
					synchronized (sockets) {
						for (final Iterator<Socket> it = sockets.iterator(); it.hasNext();) {
							final Socket socket = it.next();

							executor.execute(new Runnable() {
								public void run() {
									try {
										PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

										out.println(fmessage);
										out.flush();
									} catch (IOException e) {
										e.printStackTrace();
										it.remove();
									}
								}
							});
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static class ReceiverTask implements Runnable {
		private BufferedReader reader = null;
		private Socket socket = null;
		private LinkedList<String> messages = null;

		public ReceiverTask(Socket socket, LinkedList<String> messages) {
			this.socket = socket;
			this.messages = messages;
		}

		public void run() {
			try {
				reader = new BufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
				String line = null;

				while ((line = reader.readLine()) != null) {
					synchronized (messages) {
						messages.addLast(line);
						messages.notifyAll();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public char[] getPwd() {
		return pwd;
	}

	public void setPwd(char[] pwd) {
		this.pwd = pwd;
	}

}
