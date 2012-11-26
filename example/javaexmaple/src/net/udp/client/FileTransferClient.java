package net.udp.client;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class FileTransferClient implements Runnable {
	public FileTransferClient(String host, int port, int bufferSize, String filePath) {
		this.host = host;
		this.port = port;
		this.bufferSize = bufferSize;
		this.filePath = filePath;
	}

	public FileTransferClient(String host, int port, String filePath) {
		this(host, port, DEFAULT_SEND_DATA_LENGTH, filePath);
	}

	@Override
	public void run() {
		DatagramSocket client = null;

		try {
			File file = new File(filePath);
			client = new DatagramSocket();

			int index = 0;
			String id = UUID.randomUUID().toString();
			String name = file.getName();

			Map<Integer, DatagramPacket> datas = getDataDatagramPackets(file, index + 1, id);

			boolean isSuccess = false;
			for (int i = 0; i < RETRY_NUM; i++) {
				client.send(getHeaderDatagramPacket(index, id, name, datas.size()));

				DatagramPacket responsep = new DatagramPacket(new byte[RESPONSE_LENGTH], RESPONSE_LENGTH);
				try {
					client.receive(responsep);
				} catch (IOException e) {
					try {
						TimeUnit.SECONDS.sleep(RETRY_INTERVAL_SECONDS);
					} catch (InterruptedException e1) {}
					continue;
				}
				if (validator(responsep) == -1) {
					isSuccess = true;
					break;
				}
			}

			if (isSuccess) {
				for (Entry<Integer, DatagramPacket> data : datas.entrySet()) {
					client.send(data.getValue());
				}

				while (true) {
					DatagramPacket responsep = new DatagramPacket(new byte[RESPONSE_LENGTH], RESPONSE_LENGTH);
					try {
						client.receive(responsep);
					} catch (IOException e) {}

					int result = validator(responsep);
					if (result > 0) {
						client.send(datas.get(result));
					} else if (result == -2) {
						break;
					} else {
						continue;
					}
				}
				System.err.println("发送成功");
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public int validator(DatagramPacket response) throws IOException {
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(response.getData()));
		int index = in.readInt();
		int r = in.read();

		if (r == 0) {
			return index;
		} else if (r == 1) {
			return -1;
		} else if (r == 2) {
			return -2;
		} else {
			return -3;
		}
	}

	public DatagramPacket getHeaderDatagramPacket(int index, String id, String name, int length) throws SocketException {
		ByteArrayOutputStream memoryout = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(memoryout);

		try {
			out.writeInt(index);
			out.writeUTF(id);
			out.writeChar('\n');
			out.write(0);
			out.writeUTF(name);
			out.writeChar('\n');
			out.writeInt(length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = memoryout.toByteArray();
		byte[] data = new byte[bufferSize];
		System.arraycopy(buf, 0, data, 0, buf.length);

		return new DatagramPacket(data, data.length, new InetSocketAddress(host, port));

	}

	public Map<Integer, DatagramPacket> getDataDatagramPackets(File file, int index, String id) {
		BufferedInputStream in = null;
		Map<Integer, DatagramPacket> datas = new HashMap<Integer, DatagramPacket>();
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			while (in.available() > 0L) {
				ByteArrayOutputStream memoryout = new ByteArrayOutputStream();
				DataOutputStream dout = new DataOutputStream(memoryout);

				dout.writeInt(index++);
				dout.writeUTF(id);
				dout.writeChar('\n');
				dout.write(1);
				
				byte[] buffer = new byte[bufferSize - memoryout.size()];

				dout.writeInt(in.read(buffer));
				dout.write(buffer);

				buffer = memoryout.toByteArray();
				datas.put(index, new DatagramPacket(buffer, buffer.length, new InetSocketAddress(host, port)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}
		return datas;
	}

	private static final int DEFAULT_SEND_DATA_LENGTH = 128;
	private static final int RESPONSE_LENGTH = 5;
	private static final int RETRY_NUM = 5;
	private static final int RETRY_INTERVAL_SECONDS = 5;

	private String host;
	private int port;
	private int bufferSize;
	private String filePath;
}
