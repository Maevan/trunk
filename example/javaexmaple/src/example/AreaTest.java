package example;

import java.lang.reflect.Array;

public class AreaTest {
	public static void main(String[] args) throws ClassNotFoundException {
		System.err.println("????????");
		Class<?> clazz = AreaTest.class.getClassLoader().loadClass("example.T");
		Class.forName("example.T");
	}
}

class T {
	static {
		System.err.println("!!!!!!!!");
		f();
	}

	public static void f() {
		throw new NoClassDefFoundError();
	}
}