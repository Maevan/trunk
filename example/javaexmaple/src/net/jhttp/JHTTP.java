package net.jhttp;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JHTTP extends Thread {
	private File rootdir;
	private String indexpage = "index.html";
	private ServerSocket server;
	private int numThreads = 50;

	public JHTTP(File rootdir, int port, String indexpage) throws IOException {
		if (rootdir != null && rootdir.isDirectory()) {
			this.rootdir = rootdir;
			this.indexpage = indexpage;
			this.server = new ServerSocket(port);
		} else {
			throw new IOException(rootdir + " is not a directory");
		}
	}

	public JHTTP(File rootdir, int port) throws IOException {
		this(rootdir, port, "index.html");
	}

	public JHTTP(File rootdir) throws IOException {
		this(rootdir, 80);
	}

	public void run() {
		for (int i = 0; i < numThreads; i++) {
			Thread t = new Thread(new GetRequestProcessor(rootdir, indexpage));
			t.start();
		}
		while (true) {
			try {
				Socket request = server.accept();
				GetRequestProcessor.processRequest(request);
			} catch (IOException e) {
			}
		}
	}

}
