package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class FormTester {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket(InetAddress.getByName("203.119.80.111"), 8080);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		out.write("GET /monitorup/ex.action?startTime=2011-05-01&endTime=2011-05-11 HTTP/1.1\r\n" + "Accept: */*\r\n" + "Accept-Language: en-US,zh-cn;q=0.5\r\n"
				+ "Host: 203.119.80.111:8080\r\n" + "Pragma: no-cache\r\n" + "Cookie: JSESSIONID=DB33FFE47D472B96243213A8DA95457B\r\n\r\n");
		out.flush();
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String line = null;
		while ((line = in.readLine()) != null) {
			System.err.println(line);
		}
		socket.close();
	}
}
