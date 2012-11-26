package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

public class PropertyTester {
	public static void main(String[] args) throws IOException {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("util/properties/tpanworker.properties");

		Properties properties = new Properties();

		properties.load(new InputStreamReader(in));

		Set<Object> keys = properties.keySet();

		for (Object key : keys) {
			System.err.println("key: " + key + "    value:" + properties.getProperty(key.toString()));
		}
	}
}
