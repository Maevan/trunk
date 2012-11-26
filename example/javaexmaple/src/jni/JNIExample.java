package jni;

/**
 * 教程地址 http://www.ibm.com/developerworks/cn/education/java/j-jni/index.html
 * http://en.wikipedia.org/wiki/Java_Native_Interface
 * 
 * @author zhaojp
 * 
 */
public class JNIExample {
	public native int intMethod(int n);

	public native boolean booleanMethod(boolean bool);

	public native String stringMethod(String str);

	public native int intArrayMethod(int[] intArray);

	public static void main(String[] args) {
		System.err.println(System.getProperty("java.library.path"));
		System.loadLibrary("JNIExample");

		JNIExample example = new JNIExample();

		int square = example.intMethod(5);
		boolean bool = example.booleanMethod(true);
		String text = example.stringMethod("JAVA");
		int sum = example.intArrayMethod(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });

		System.err.println("intMethod:" + square);
		System.err.println("booleanMethod:" + bool);
		System.err.println("stringMethod:" + text);
		System.err.println("intArrayMethod:" + sum);
	}
}
