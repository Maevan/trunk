package net.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTester implements Runnable {

	@Override
	public void run() {
		Socket socket = null;

		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			socket = new Socket("localhost", 5000);

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.trim().equals("bye")) {
					break;
				} else {
					System.out.println(line);
				}
			}
			out.write("exit\r\n");
			out.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
