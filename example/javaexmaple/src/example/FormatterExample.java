package example;

import java.util.Formatter;

public class FormatterExample {
	public static void main(String[] args) {
		Formatter formatter = new Formatter();

		for (int i = 0; i < 100; i++) {
			formatter.format("%5d", i);
		}
		String result = formatter.toString();
		int index = 0;
		while (index < result.length()) {
			System.err.println(result.substring(index, (index += 5)));
		}
	}
}
