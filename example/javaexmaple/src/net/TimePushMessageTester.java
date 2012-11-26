package net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TimePushMessageTester {
	public static void main(String[] args) {
		Thread tpush = new Thread() {
			public void run() {
				try {
					ServerSocket server = new ServerSocket(7777);
					Socket socket = server.accept();
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					while (true) {
						Thread.sleep(1000 * 5);
						out.write("五秒" + System.currentTimeMillis() + "\r\n");
						out.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread treceive = new Thread() {
			public void run() {
				try {
					Socket socket = new Socket(InetAddress.getLocalHost(), 7777);
					Scanner scanner = new Scanner(socket.getInputStream());
					while (true) {
						System.err.println(scanner.nextLine());// 获取不到数据的时候该线程会被挂起
						System.err.println("接受数据完毕");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		tpush.start();
		treceive.start();
	}
}
