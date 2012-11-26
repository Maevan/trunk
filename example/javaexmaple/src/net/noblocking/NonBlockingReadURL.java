package net.noblocking;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

public class NonBlockingReadURL {
	static Selector selector;

	public static void main(String args[]) {
		String host = "116.228.233.230";
		SocketChannel channel = null;

		try {
			// Setup
			InetSocketAddress socketAddress = new InetSocketAddress(host, 80);
			Charset charset = Charset.forName("UTF-8");
			CharsetDecoder decoder = charset.newDecoder();
			CharsetEncoder encoder = charset.newEncoder();

			// Allocate buffers
			ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			CharBuffer charBuffer = CharBuffer.allocate(1024);

			// Connect
			channel = SocketChannel.open(socketAddress);
			channel.configureBlocking(false);

			// Open Selector
			selector = Selector.open();

			// Register interest in when connection
			channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

			// Wait for something of interest to happen
			while (selector.select() != 0) {
				// Get set of ready objects
				Set<SelectionKey> readyKeys = selector.selectedKeys();
				Iterator<SelectionKey> readyItor = readyKeys.iterator();

				// Walk through set
				while (readyItor.hasNext()) {

					// Get key from set
					SelectionKey key = (SelectionKey) readyItor.next();

					// Remove current entry
					readyItor.remove();

					// Get channel
					SocketChannel keyChannel = (SocketChannel) key.channel();

					if (key.isWritable()) {
						// Send request
						StringBuffer sb = new StringBuffer();
						sb.append("GET http://116.228.233.230/cn/LtInfoController.cmd?action=remind HTTP/1.1\r\n");
						sb
								.append("Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/x-ms-application, application/x-ms-xbap, application/vnd.ms-xpsdocument, application/xaml+xml, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n");
						sb.append("Referer: http://116.228.233.230/\r\n");
						sb.append("Accept-Language: zh-cn\r\n");
						sb
								.append("User-Agent: Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C; .NET4.0E)\r\n");
						sb.append("Accept-Encoding: gzip, deflate\r\n");
						sb.append("Host: 116.228.233.230\r\n");
						sb.append("Connection: Keep-Alive\r\n");
						sb.append("Cookie: JSESSIONID=44B3124431A2752E8BDF59D0AFCA7CE5.tomcat1\r\n");
						String request = sb.toString() + "\r\n";

						keyChannel.write(encoder.encode(CharBuffer.wrap(request)));
					} else if (key.isReadable()) {
						// Read what's ready in response
						keyChannel.read(buffer);
						buffer.flip();

						// Decode buffer
						decoder.decode(buffer, charBuffer, false);

						// Display
						charBuffer.flip();
						System.out.print(charBuffer);

						// Clear for next pass
						buffer.clear();
						charBuffer.clear();
					}
				}
			}
		} catch (UnknownHostException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException ignored) {}
			}
		}
		System.out.println();
	}
}
