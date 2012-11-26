package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DatetimeServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(2587);
			while (true) {
				Socket socket = serverSocket.accept();

				PrintWriter out = new PrintWriter(socket.getOutputStream());
				out.println(new Date());
				
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
