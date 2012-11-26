package net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ChargenClient {
	public final static int DEFAULT_PORT = 9999;

	public static void main(String[] args) {
		new Thread() {
			public void run() {
				try {
					ServerSocket server = new ServerSocket(DEFAULT_PORT);
					while (true) {
						Socket socket = server.accept();
						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
						out.write("得到相应信息了 满足了吧");
						out.flush();
						socket.close();
					}
				} catch (Exception e) {}
			}
		}.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		new Thread() {
			public void run() {
				SocketAddress address = null;
				try {
					address = new InetSocketAddress(InetAddress.getLocalHost(), DEFAULT_PORT);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				try {
					SocketChannel client = SocketChannel.open(address);
					ByteBuffer buffer = ByteBuffer.allocate(74);
					WritableByteChannel out = Channels.newChannel(System.out);

					while (client.read(buffer) != -1) {
						buffer.flip();
						out.write(buffer);
						buffer.clear();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}
}
