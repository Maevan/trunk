package net.urlc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;

public class JarURLConnectionTester {
	// 格式 jar:[url]!/[Jar内具体资源名]
	public static final String TEST_FILE = "jar:file:///E%3A/prolib/quartz/quartz-1.6.3.jar!/overview.html";

	public static void main(String[] args) throws IOException {
		JarURLConnection uc = (JarURLConnection) new URL(TEST_FILE).openConnection();// 根据URL判断返回具体URLConnection的子类
		BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));

		String line = null;

		while ((line = reader.readLine()) != null) {
			System.err.println(line);
		}
	}
}
