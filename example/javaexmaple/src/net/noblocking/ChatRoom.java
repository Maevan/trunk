package net.noblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ChatRoom {
	public static void main(String[] args) {
		ChatRoom room = new ChatRoom();
		room.start(6969);
	}

	public void start(int port) {
		new Thread(new Server(port)).start();
		new Thread(new Sender()).start();
	}

	private void close(SelectableChannel channel) {
		if (channel == null)
			return;
		try {
			channel.close();
		} catch (IOException e) {}
	}

	private LinkedBlockingDeque<String> messages = new LinkedBlockingDeque<>();
	private HashMap<String, Session> sessions = new HashMap<>();

	private static final String WELECOME_MESSAGE = "Please Enter Your Name: ";

	class Sender implements Runnable {
		@Override
		public void run() {
			while (true) {
				String message = null;
				try {
					message = messages.pollFirst(5, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					break;
				}
				if (message != null) {
					String[] nameAndDetail = message.split("@");
					Session session = sessions.get(nameAndDetail[0]);
					if (session == null) {
						// TODO 离线列表之类的
					} else {
						session.write(nameAndDetail[1]);
					}
				}
			}
		}
	}

	class Server implements Runnable {
		public Server(int port) {
			this.port = port;
		}

		private Integer port;

		@Override
		public void run() {
			ServerSocketChannel server = null;
			Selector selector = null;
			try {
				server = ServerSocketChannel.open();
				server.configureBlocking(false);
				server.bind(new InetSocketAddress(port));
				System.err.println("Server is start, listen " + port);
				selector = Selector.open();

				server.register(selector, SelectionKey.OP_ACCEPT);
			} catch (IOException e) {
				close(server);
				throw new RuntimeException(e);
			}
			while (true) {
				try {
					selector.select();
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
				Set<SelectionKey> keys = selector.selectedKeys();
				for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext();) {
					SelectionKey key = it.next();

					if (key.isAcceptable()) {
						SocketChannel client = null;
						try {
							client = server.accept();
							client.configureBlocking(false);
						} catch (IOException e) {
							continue;
						}
						try {
							System.err.println("Get a client: " + client.getRemoteAddress());
							client.register(selector, SelectionKey.OP_WRITE, ByteBuffer.wrap(WELECOME_MESSAGE.getBytes()));
						} catch (IOException e) {
							e.printStackTrace();
							close(client);
						}
					} else if (key.isReadable()) {
						Session session = (Session) key.attachment();
						if (session.append()) {
							key.cancel();
						}
					} else if (key.isWritable()) {
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer towrite = (ByteBuffer) key.attachment();

						while (towrite.hasRemaining()) {
							try {
								client.write(towrite);
							} catch (IOException e) {
								close(client);
								e.printStackTrace();
							}
						}
						try {
							client.register(selector, SelectionKey.OP_READ, new Session(client));
						} catch (ClosedChannelException e) {
							close(client);
						}
						//key.cancel();
					}
					it.remove();
				}
			}
		}
	}

	class Session {
		private String name;
		private SocketChannel channel;
		private String last = "";

		public Session(SocketChannel channel) {
			this.channel = channel;
		}

		public boolean append() {
			if (channel == null || !channel.isOpen()) {
				return false;
			}
			ByteBuffer legacy = ByteBuffer.allocate(0);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				try {
					channel.read(buffer);
				} catch (IOException e) {
					close(channel);
					e.printStackTrace();
				}

				if (buffer.remaining() != buffer.capacity()) {
					break;
				}
				buffer.flip();
				legacy = ByteBuffer.allocate(legacy.limit() + buffer.limit()).put(buffer);
			}
			buffer.flip();
			legacy = ByteBuffer.allocate(legacy.limit() + buffer.limit()).put(buffer);
			legacy.flip();
			CharBuffer message = legacy.asCharBuffer();

			StringBuilder sb = new StringBuilder(last);
			while (message.hasRemaining()) {
				char cur;
				for (cur = message.get(); message.hasRemaining() && cur != '\n';) {
					sb.append(cur);
				}
				if (cur == '\n') {
					if (sb.length() > 0) {
						String m = sb.substring(0, sb.length() - 2);
						if (m.matches("\\w+@[\\w\\d]+")) {
							if (name == null) {
								write("Please input your name first.");
							}
							messages.addLast(m);
						} else if (name == null) {
							if (m.matches("\\w+")) {

							} else if (sessions.containsKey(name)) {
								write("'" + name + "' is already exisits");
							}
							name = m;
							write("Your Name is '" + name + "'");
						} else if (m.equals("list")) {
							// TODO 此时应该调用write方法将所有在线的用户全部输出
						} else {
							write("Illegal message[" + m + "]");
						}
					}
				} else {
					last = message.toString();
				}
			}
			return true;
		}

		public boolean write(String message) {
			ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
			while (buffer.hasRemaining()) {
				int count;
				try {
					count = channel.write(buffer);
				} catch (IOException e) {
					e.printStackTrace();
					close(channel);
					channel = null;
					return false;
				}
				if (count == 0) {
					Selector selector = null;
					try {
						selector = Selector.open();
						channel.register(selector, SelectionKey.OP_WRITE);

						if (selector.select(5000) == 0) {
							close(channel);
							channel = null;
							return false;
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							selector.close();
						} catch (IOException e) {}
					}
				}
			}

			return true;
		}
	}
}
