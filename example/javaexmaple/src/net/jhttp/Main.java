package net.jhttp;

import java.io.File;
import java.io.IOException;
import java.net.ProxySelector;

public class Main {
	public static void main(String[] args) {
		try {
			new JHTTP(new File("D:/Tomcat6/webapps/examples"), 80).start();
			ProxySelector.setDefault(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
