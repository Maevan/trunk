package util;

import java.lang.reflect.Field;

public class StringTester {
	public static void main(String[] args) throws Exception {
		String s = "abc";
		System.err.println(s);
		change(s);
		System.err.println(s);
	}

	@SuppressWarnings("unchecked")
	public static void change(String str) throws Exception {
		Class<String> clazz = (Class<String>) str.getClass();
		Field field = clazz.getDeclaredField("value");
		field.setAccessible(true);

		char[] c = (char[]) field.get(str);
		c[0] = 'd';
	}
}
