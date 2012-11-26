package util;

import java.lang.reflect.Array;

public class ArrayUtils {
	public static Object resizeArray(Object oldarray, int newsize) {
		int oldSize = Array.getLength(oldarray);
		Class<?> componentType = oldarray.getClass().getComponentType();
		Object newarray = Array.newInstance(componentType, newsize);
		int preserveLength = Math.min(oldSize, newsize);

		if (preserveLength > 0) {
			System.arraycopy(oldarray, 0, newarray, 0, preserveLength);
		}
		return newarray;
	}

	public static void main(String[] args) {
		int[] ints = { 1, 2, 3, 4, 5, 6 };

		ints = (int[]) resizeArray(ints, 5);

		for (int i : ints) {
			System.out.println(i);
		}
		ints = (int[]) resizeArray(ints, 6);
		System.out.println("-------------------------------");
		for (int i : ints) {
			System.out.println(i);
		}
	}
}
