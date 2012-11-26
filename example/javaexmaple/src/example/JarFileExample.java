package example;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileExample {
	public static void main(String[] args) throws IOException {
		JarFile jf = new JarFile("F:/LIBS/spring-framework-3.1.1.RELEASE/dist/org.springframework.core-3.1.1.RELEASE.jar");
		Enumeration<JarEntry> entries = jf.entries();

		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			System.err.println(entry.getName());
		}
	}
}
