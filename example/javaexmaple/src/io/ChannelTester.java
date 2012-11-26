package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChannelTester {
	public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) throws IOException {
		System.err.println(df.format(new Date(System.currentTimeMillis())));

		File rfile = new File("D:/TDDOWNLOAD/17173_SC2-WingsOfLiberty-zhCN-Installer.rar");
		File wfile = new File("E:/17173_SC2-WingsOfLiberty-zhCN-InstallerCopy.rar");

		FileChannel rchannel = new FileInputStream(rfile).getChannel();
		FileChannel wchannel = new FileOutputStream(wfile).getChannel();
		ByteBuffer[] buffers = new ByteBuffer[1024];

		for (int i = 0; i < buffers.length; i++) {
			buffers[i] = ByteBuffer.allocate(1024);
		}
		boolean isLoop = true;
		while (isLoop) {
			for (ByteBuffer buffer : buffers) {
				buffer.rewind();
			}
			isLoop = rchannel.read(buffers) != -1L;
			for (ByteBuffer buffer : buffers) {
				buffer.flip();
			}
			wchannel.write(buffers);
		}
		rchannel.close();
		wchannel.close();
	}
}
