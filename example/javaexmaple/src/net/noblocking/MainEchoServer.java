package net.noblocking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MainEchoServer {
	public static void main(String[] args) {
		new Thread(new EchoServer()).start();

		try {
			final Socket socket = new Socket(InetAddress.getLocalHost(), 8087);
			new Thread() {
				public void run() {
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						while (true) {
							System.err.println(reader.readLine());
						}
					} catch (IOException e) {}
				}
			}.start();

			new Thread() {
				public void run() {
					Scanner scanner = new Scanner(System.in);
					String line = null;
					BufferedWriter writer = null;
					try {
						writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
						while ((line = scanner.nextLine()) != null) {
							writer.write(line);
							writer.write("\r\n");
							writer.flush();
						}
					} catch (IOException e) {}
				}
			}.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
