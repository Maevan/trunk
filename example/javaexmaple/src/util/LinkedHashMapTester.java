package util;

import java.util.LinkedHashMap;
import java.util.Random;

public class LinkedHashMapTester {
	public static void main(String[] args) {
		Random genrator = new Random();
		LinkedHashMap<String, String> dict = new LinkedHashMap<String, String>();// 保证插入顺序
		for (int i = 0; i < 10; i++) {
			dict.put(i + "_" + genrator.nextInt(), "Hello");
		}
		System.err.println("--------------------------------------");
		for (String key : dict.keySet()) {
			System.err.println("key:" + key);
		}
	}
}
