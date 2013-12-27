package net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import sun.misc.BASE64Encoder;

public class WebSocketServer {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(DEFAULT_PORT);
			while (true) {
				Session session = new Session(server.accept());
				if (session.isInit()) {
					SESSION_MAP.put(new Session(server.accept()), null);
				}
			}
		} catch (IOException e) {}
	}

	public static String getSecWebSocketAccept(String key) {
		byte[] buffer = digest.digest((key + WEBSOCKET_MAGIC_NUMBER).getBytes());
		return BASE64_ENCODER.encode(buffer);
	}

	static class Session implements Runnable {
		public Session(Socket client) {
			this.client = client;
			try {
				in = new BufferedInputStream(client.getInputStream());
				out = new BufferedOutputStream(client.getOutputStream());
				this.isInit = true;
			} catch (IOException e) {
				this.isInit = false;
				try {
					client.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} finally {}
			new Thread(this).start();
		}

		@Override
		public void run() {
			Location location = getLocation();
			Map<String, String> headers = getHeaders();
			if (headers.containsKey("Upgrade")) {
				upgrade = headers.get("Upgrade");
			}
			if (upgrade.equals("websocket")) {
				websocket(location, headers);
			} else {
				normal(location, headers);
			}
		}

		public void normal(Location location, Map<String, String> headers) {
			// TODO 待实现
		}

		public void websocket(Location location, Map<String, String> headers) {
			String key = headers.get("Sec-WebSocket-Key");
			String acceptKey = getSecWebSocketAccept(key);
			ResponseLocation response = new ResponseLocation(101, location.getVersion(), "Switching Protocols");
			headers = new HashMap<String, String>();
			headers.put("Upgrade", upgrade);
			headers.put("Connection", "Upgrade");
			headers.put("Sec-WebSocket-Accept", acceptKey);
			headers.put("Sec-WebSocket-Origin", "null");
			headers.put("Sec-WebSocket-Location", "ws://" + HOST + ":" + DEFAULT_PORT + "/");

			try {
				response(response, headers, out);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			Thread sender = new Thread() {
				@Override
				public void run() {
					try {
						while (!client.isClosed()) {
							String message = MESSAGE_QUEUE.take();
							byte[] data = message.getBytes();
							out.write(IS_FIN | OPCODE_TEXT);
							if (data.length < 126) {
								out.write(data.length);
							}
							
							for (int i = 0; i < data.length; i++) {
								out.write(data[i]);
							}
							out.flush();
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			Thread receiver = new Thread() {
				public void run() {
					try {
						while (!client.isClosed()) {
							boolean isFin = false;
							boolean isMask = false;
							byte[] mask = new byte[4];
							int type = -1;
							int length = -1;

							ByteArrayOutputStream buffer = new ByteArrayOutputStream();

							int i = in.read();
							do {
								isFin = (IS_FIN | i) == i;
								if (i << 4 != 0) {
									if (type != -1) {
										throw new RuntimeException("Illegal Opcode");
									}
									if ((i | OPCODE_TEXT) == i) {
										type = OPCODE_TEXT;
									} else if ((i | OPCODE_BINARY) == i) {
										type = OPCODE_BINARY;
									} else if ((i | OPCODE_CLOSED) == i) {
										type = OPCODE_CLOSED;
									} else {
										throw new RuntimeException("Illegal Opcode");
									}
								}
								if (type == OPCODE_CLOSED) {
									break;
								}
								i = in.read();
								isMask = (IS_MASK | i) == i;
								i = isMask ? i ^ (1 << 7) : i;
								if (i == 126) {
									for (int x = 0; x < 2; x++) {
										i <<= 8;
										i |= in.read();
									}
								} else if (i == 127) {
									for (int x = 0; x < 8; x++) {
										i <<= 8;
										i |= in.read();
									}
								}
								length = i;
								if (isMask) {
									int count = 0;
									int off = 0;
									i = -1;
									while ((i = in.read(mask, off, 4)) != -1) {
										count += i;
										if (count == 4) {
											break;
										}
										off = count - 1;
									}
								}

								if (isMask) {
									for (int x = 0; x < length;) {
										i = in.read();
										if (i == -1) {
											continue;
										}
										buffer.write(i ^ mask[x % 4]);
										x++;
									}
								} else {
									byte[] b = new byte[1024];
									i = -1;
									while ((i = in.read(b, 0, length > b.length ? b.length : length)) != -1) {
										length -= i;
										buffer.write(b, 0, i);
										if (length == 0) {
											break;
										}
									}
								}
							} while (!isFin);
							switch (type) {
							case OPCODE_TEXT:
								MESSAGE_QUEUE.put("from server:" + new String(buffer.toByteArray()));
								break;
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						try {
							client.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			};
			sender.start();
			receiver.start();
		}

		public String toWebSocketMessage() {
			return null;
		}

		public void response(ResponseLocation location, Map<String, String> headers, OutputStream out, InputStream in) throws IOException {
			StringBuilder message = new StringBuilder();
			message.append(location.getVersion()).append(" ").append(location.getResponsecode()).append(" ").append(location.getDescription()).append("\r\n");

			for (Entry<String, String> entry : headers.entrySet()) {
				message.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
			}
			message.append("\r\n");
			byte[] responseLine = message.toString().getBytes();
			out.write(responseLine);
			out.flush();

			if (in != null) {
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = in.read(buffer, 0, buffer.length)) != -1) {
					out.write(buffer, 0, len);
				}
				out.flush();
			}
		}

		public void response(ResponseLocation location, Map<String, String> headers, OutputStream out) throws IOException {
			response(location, headers, out, null);
		}

		public Map<String, String> getHeaders() {
			Map<String, String> headers = new HashMap<String, String>();
			if (headers.isEmpty()) {
				String line = null;
				while ((line = readLine()) != null) {
					String[] keyvalue = line.split(":\\s");
					headers.put(keyvalue[0], keyvalue[1]);
				}
			}
			return headers;
		}

		public Location getLocation() {
			String line = readLine();
			if (line == null) {
				return null;
			} else {
				String[] props = line.split("\\s");
				return new Location(props[0], props[1], props[2]);
			}
		}

		public boolean isInit() {
			return isInit;
		}

		private String readLine() {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int b1 = -1;
			int b2 = -1;
			try {
				while ((b2 = in.read()) != -1) {
					if (b1 == '\r' && b2 == '\n') {
						break;
					}
					b1 = b2;
					out.write(b2);
				}
				byte[] buffer = out.toByteArray();
				if (buffer.length == 1) {
					return null;
				}
				return new String(buffer, 0, buffer.length - 1);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		private BufferedOutputStream out;
		private BufferedInputStream in;
		private Socket client;
		private boolean isInit;
		private String upgrade;
	}

	static class WebSocketFrame {
		private boolean isFin;
		private boolean isMask;
		private byte[] masks;
		private byte opcode;
		private int length;

		public byte getOpcode() {
			return opcode;
		}

		public void setOpcode(byte opcode) {
			this.opcode = opcode;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

	}

	static class ResponseLocation {
		private final Integer responsecode;
		private final String version;
		private final String description;

		public ResponseLocation(Integer responsecode, String version, String description) {
			this.responsecode = responsecode;
			this.version = version;
			this.description = description;
		}

		public Integer getResponsecode() {
			return responsecode;
		}

		public String getVersion() {
			return version;
		}

		public String getDescription() {
			return description;
		}
	}

	static class Location {
		private final String method;
		private final String path;
		private final String version;

		public Location(String method, String path, String version) {
			this.method = method;
			this.path = path;
			this.version = version;
		}

		public String getMethod() {
			return method;
		}

		public String getPath() {
			return path;
		}

		public String getVersion() {
			return version;
		}
	}

	private static final int IS_FIN = 0x1 << 7;
	private static final int IS_RSV_1 = 0x2;
	private static final int IS_RSV_2 = 0x4;
	private static final int IS_RSV_3 = 0x8;

	private static final int OPCODE_GO = 0x0;
	private static final int OPCODE_TEXT = 0x1;
	private static final int OPCODE_BINARY = 0x2;
	private static final int OPCODE_CLOSED = 0x9;

	private static final int IS_MASK = 0x1 << 7;

	private static MessageDigest digest = null;
	private static final String WEBSOCKET_MAGIC_NUMBER = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
	private static final String HOST = "localhost";
	private static final Integer DEFAULT_PORT = 8080;
	private static final BASE64Encoder BASE64_ENCODER = new BASE64Encoder();
	private static final ConcurrentMap<Session, String> SESSION_MAP = new ConcurrentHashMap<WebSocketServer.Session, String>();
	private static final BlockingQueue<String> MESSAGE_QUEUE = new ArrayBlockingQueue<String>(500);

	static {
		try {
			digest = MessageDigest.getInstance("sha-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
