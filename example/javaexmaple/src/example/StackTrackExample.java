package example;

public class StackTrackExample {
	public static void main(String[] args) {
		Foo<String> f = new Foo<String>();
		f.printClassName();
	}
}

class Foo<T> {
	public void printClassName() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();

		throw new RuntimeException();
		// System.err.println(elements[1]);
	}
}
