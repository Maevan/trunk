package net.udp.main;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.udp.client.FileTransferClient;
import net.udp.server.FileTransferServer;

public class FileTransferTest {
	public static void main(String[] args) throws IOException, InterruptedException {
		new Thread(new FileTransferServer(35555)).start();
		TimeUnit.SECONDS.sleep(1);
		new Thread(new FileTransferClient("127.0.0.1", 35555, "D:/密码.txt"));
	}
}
