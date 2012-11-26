package example;

public class TestLoop {
	public static void main(String[] args) {
		int i = 0;
		int y = 0;
		for (i = 0; i < 10; i++) {
			for (y = 0; y < 10; y++) {
				if (y == 5) {
					break;
				}
			}
			System.err.println(i);
		}

		System.err.println(i + "," + y);
	}
}
