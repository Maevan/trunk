package net.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTester implements Runnable {

	@Override
	public void run() {
		ServerSocket server = null;
		try {
			server = new ServerSocket(5000);
			
			BufferedReader in = null;
			BufferedWriter out = null;
			while (true) {
				Socket socket = server.accept();
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				while (true) {
					out.write("hello to client\r\n");
					out.write("i am server\r\n");
					out.write("bye\r\n");
					out.flush();
					if (in.readLine().trim().equals("exit")) {
						break;
					}
				}
				socket.close();
				System.err.println("第一次请求结束...按照你这个代码同时只能接受一个请求...");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
