package net.jhttp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class GetRequestProcessor implements Runnable {
	private static LinkedList<Socket> pool = new LinkedList<Socket>();

	private File rootdir;
	private String indexpage = "index.html";

	public GetRequestProcessor(File rootdir, String indexpage) {
		if (rootdir == null) {
			throw new IllegalArgumentException("rootdir must be not null");
		} else if (!rootdir.isDirectory()) {
			throw new IllegalArgumentException("rootdir must be a directory,not a file");
		} else {
			try {
				this.rootdir = rootdir.getCanonicalFile();
			} catch (IOException e) {
				this.rootdir = rootdir;
			}
			if (indexpage == null || indexpage.trim().equals("")) {
				this.indexpage = "index.html";
			} else {
				this.indexpage = indexpage;
			}
		}
	}

	public static void processRequest(Socket request) {
		synchronized (pool) {
			pool.add(request);
			pool.notifyAll();
		}
	}

	@Override
	public void run() {
		String root = rootdir.getPath();

		while (true) {
			Socket connection;
			synchronized (pool) {
				while (pool.isEmpty()) {
					try {
						pool.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				connection = pool.removeFirst();
			}
			Date now = new Date();
			String filename = null;
			String contentType = null;
			OutputStream raw = null;
			Writer out = null;
			BufferedReader in = null;
			try {
				raw = new BufferedOutputStream(connection.getOutputStream());
				out = new OutputStreamWriter(raw);
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				StringBuilder requestLine = new StringBuilder();
				String line = null;

				if ((line = in.readLine()) != null) {
					requestLine.append(line);
				}
				String get = requestLine.toString();

				StringTokenizer st = new StringTokenizer(get);
				String method = st.nextToken();
				String version = null;

				filename = st.nextToken();
				if (st.hasMoreTokens()) {
					version = st.nextToken();
				} else {
					version = "";
				}
				if (method.equals("GET")) {
					File theFile = new File(rootdir, filename.substring(1, filename.length()));
					if (theFile.isDirectory()) {
						if (!filename.endsWith("/")) {
							filename += "/";
						}
						filename += indexpage;
						theFile = new File(rootdir, filename.substring(1, filename.length()));
					}
					contentType = guessContentTypeFromName(filename);
					if (theFile.getCanonicalPath().startsWith(root)) {
						BufferedInputStream fis = new BufferedInputStream(new BufferedInputStream(new FileInputStream(theFile)));
						byte[] datas = new byte[1024];
						if (version.startsWith("HTTP/1.1")) {
							out.write("HTTP/1.1 200 OK\r\n");
							out.write("Date: " + now + "\r\n");
							out.write("Server: JHTTP/1.0 \r\n");
							out.write("Content-length: " + theFile.length());
							out.write("Content-type: " + contentType + "\r\n\r\n");
							out.flush();
						}
						int i = 0;
						while ((i = fis.read(datas, 0, datas.length)) != -1) {
							raw.write(datas, 0, i);
							raw.flush();
						}
						fis.close();

					} else {
						if (version.startsWith("HTTP ")) {
							out.write("HTTP/1.1 404 File Not Found\r\n");
							out.write("Date: " + now + "\r\n");
							out.write("Server: JHTTP/1.0\r\n");
							out.write("Content-type: text/html\r\n\r\n");
						}
						out.write("<HTML>\r\n");
						out.write("<HEAD><TITLE>File Not Found</TITLE>\r\n");
						out.write("</HEAD>\r\n");
						out.write("<BODY>");
						out.write("<H1>HTTP Error 404:File Not Found</H1>\r\n");
						out.write("</BODY></HTML>\r\n");
						out.flush();
					}
				} else {
					if (version.startsWith("HTTP ")) {
						out.write("HTTP/1.1 501 Not Implemented\r\n");
						out.write("Date: " + now + "\r\n");
						out.write("Server: JHTTP 1.0\r\n");
						out.write("Content-type: text/html\r\n\r\n");
					}
					out.write("<HTML>\r\n");
					out.write("<HEAD><TITLE>Not Implemented\r\n</TITLE>\r\n");
					out.write("</HEAD>\r\n");
					out.write("<BODY>");
					out.write("<H1>HTTP Error 501:Not Implemented</H1>\r\n");
					out.write("</BODY></HTML>");
				}
			} catch (IOException e) {
				if (raw != null) {
					try {
						raw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					raw = null;
					out = null;
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					in = null;
				}
			}
		}
	}

	public static String guessContentTypeFromName(String name) {
		if (name.endsWith(".html") || name.endsWith(".htm")) {
			return "text/html";
		} else if (name.endsWith(".txt") || name.endsWith(".java")) {
			return "text/plain";
		} else if (name.endsWith(".gif")) {
			return "image/gif";
		} else if (name.endsWith(".class")) {
			return "application/octet-stream";
		} else if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
			return "image/jpeg";
		} else {
			return "text/plain";
		}
	}
}
