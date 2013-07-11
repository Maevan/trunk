package example;

public class Test2 {
	public static void main(String[] args) throws Exception {
		byte[] bytes = "操".getBytes();
		for (byte b : bytes) {
			System.err.print(Integer.toBinaryString(b).substring(24, 32) + " ");
		}
		System.err.println((int) '操');
		System.err.println(Integer.parseInt("0110010011001101", 2));
		int y = A2.x;

	}
	
	interface A1{
		int i =1;
	}
	interface A2 extends A1{
		int x = 1;
	}
}

