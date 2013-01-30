package example;

public class Test {
	public static void main(String[] args) {
		String s = "Le chat saute de la fenetre.";
		int begin = -1;
		int end = -1;
		for (int i = 0; i++ < 3;) {
			begin = end;
			end = s.indexOf(" ", end + 1);
		}

		System.err.println(begin + " " + end);
		System.err.println(s.charAt(begin));
	}
}
