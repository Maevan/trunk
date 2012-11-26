package net.udp.server;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Map;

public class FileTransferServer implements Runnable {

	public FileTransferServer(int port, int bufferSize) throws SocketException {
		this.bufferSize = bufferSize;
		this.socket = new DatagramSocket(port);
	}

	public FileTransferServer(int port) throws SocketException {
		this(port, RECEIVE_DATA_LENGTH);
	}

	@Override
	public void run() {
		byte[] buffer = new byte[bufferSize];
		while (true) {
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(incoming);
				System.err.println("收到数据包 长度:" + incoming.getLength());
				DatagramPacket response = handle(incoming);
				if (response != null) {
					socket.send(response);
				}
			} catch (IOException e) {}
		}
	}

	public DatagramPacket handle(DatagramPacket incoming) {
		ByteArrayOutputStream memory = new ByteArrayOutputStream();
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(incoming.getData()));
		DataOutputStream out = new DataOutputStream(memory);

		boolean isInit = false;

		int index;
		try {
			index = in.readInt();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		StringBuilder id = new StringBuilder();
		char c;
		try {
			while ((c = in.readChar()) != '\n')
				id.append(c);

			int type = in.read();

			if (type == 0) {
				if (!isInit) {
					StringBuilder name = new StringBuilder();

					while ((c = in.readChar()) != '\n')
						name.append(c);

					int count = in.readInt();

					states.put(id.toString(), new State(name.toString(), count));

					out.writeInt(index);
					out.write(1);
					System.err.println("头部信息获取完毕 id:" + id + " index:" + index);
				}
			} else if (type == 1) {
				int size = in.readInt();
				byte[] data = new byte[size];

				in.readFully(data);

				if (states.containsKey(id)) {
					State state = states.get(id);
					state.append(index, data);

					if (state.hasRemain()) {
						out.writeInt(index);
						out.write(1);
					} else {
						out.writeInt(index);
						out.write(2);
					}
					System.err.println("部分数据获取完毕 id:" + id + " index:" + index);
				} else {
					out.writeInt(index);
					out.write(0);
					System.err.println("指定的ID不存在 id:" + id + " index:" + index);
				}
			} else {
				out.writeInt(index);
				out.write(0);
				System.err.println("type类型不正确 id:" + id + " index:" + index);
			}
		} catch (IOException e) {
			try {
				out.writeInt(index);
				out.write(0);
			} catch (IOException e1) {}
		}
		byte[] result = memory.toByteArray();

		return new DatagramPacket(result, result.length, incoming.getAddress(), incoming.getPort());
	}

	private static final int RECEIVE_DATA_LENGTH = 128;
	private static final String TEMP_DIR = "E:/";

	private Map<String, State> states;
	private int bufferSize;
	private DatagramSocket socket;

	static class State {
		private String name;
		private int count;
		private int remain;
		private ArrayList<byte[]> datas;
		private byte[] states;

		public State(String name, int count) {
			this.name = name;
			this.count = count;
			this.remain = this.count;
			this.datas = new ArrayList<byte[]>(count);
			this.states = new byte[count];
		}

		public void append(int index, byte[] data) {
			if (!isReceive(index)) {
				states[index - 1] = 1;
				remain--;
				datas.set(index, data);
			}
		}

		public boolean flush() {
			BufferedOutputStream out = null;
			try {
				out = new BufferedOutputStream(new FileOutputStream(TEMP_DIR + name));
				for (byte[] data : datas) {
					out.write(data);
					out.flush();
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {}
			}
		}

		public boolean isReceive(int index) {
			return states[index - 1] != 0;
		}

		public boolean hasRemain() {
			return remain == 0;
		}
	}
}
