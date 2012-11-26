package example;

import java.lang.reflect.Array;

public class AreaTest {
	public static void main(String[] args) {
		char[] dictionaries = "abcde".toCharArray();
		char[] buff = new char[dictionaries.length];
		int limit = 1 << dictionaries.length;
		System.err.println(Integer.toBinaryString(limit));
		for (int mask = 1; mask < limit; mask++) {
			int pos = 0;
			for (int submask = 1, i = 0; i < dictionaries.length; submask <<= 1, i++) {
				if ((mask & submask) != 0) {
					buff[pos++] = dictionaries[i];
				}
			}
			System.out.println(new String(buff, 0, pos));
		}
		System.err.println(((Object) buff).getClass() + "?");
	}
}
