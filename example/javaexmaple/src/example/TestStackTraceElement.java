package example;

public class TestStackTraceElement {
	public static void main(String[] args) {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();

		for (StackTraceElement element : elements) {
			System.err.println(element.getFileName() + " " + element.getMethodName() + " " + element.getLineNumber());
		}
	}
}
