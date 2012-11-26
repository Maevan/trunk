package example;

public class TestConstructor {
	public static void main(String[] args) {
		HelloWorld h = new HelloWorld();
		System.err.println(h.str);
	}
}

class Base {
	public Base() {
		t();
	}

	public void t() {
	}
}

class HelloWorld extends Base {
	public String str = "Hello World";

	public HelloWorld() {
		str = "fuck";
	}

	@Override
	public void t() {
		System.err.println(str);
	}
}