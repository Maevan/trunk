package example;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

public class HuffmanTester {
	public static void main(String[] args) {
		byte[] detail = "AABBCCDDE".getBytes();
		String[] codetable = encode(detail);

		byte[] edetail = compress(detail, codetable);
		byte[] ndetail = decompress(edetail, codetable);
	}

	public static byte[] decompress(byte[] edata, String[] codetable) {
		String data = new String();
		int i;
		char[] buffer = new char[8];
		for (i = 0; i < edata.length - 1; i++) {
			Arrays.fill(buffer, '0');
			char[] src = Integer.toBinaryString(edata[i] & 0xff).toCharArray();

			System.arraycopy(src, 0, buffer, buffer.length - src.length, src.length);
			data += new String(buffer);
		}
		data = new String(data.toCharArray(), 0, data.length() - edata[i]);

		SortedMap<String, Integer> map = new TreeMap<String, Integer>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int result = o2.length() - o1.length();
				return result == 0 ? 1 : result;
			}
		});

		for (i = 0; i < codetable.length; i++) {
			if (codetable[i] != null) {
				map.put(codetable[i], i);
			}
		}

		int[] ct = new int[255];
		i = 0;

		for (java.util.Map.Entry<String, Integer> entry : map.entrySet()) {
			System.err.println(entry.getKey() + ":" + ((char) entry.getValue().intValue()));
			data = data.replace(entry.getKey(), "" + ((char) (i + 75)));
			ct[i] = entry.getValue();
			i++;
		}

		ByteArrayOutputStream memoryout = new ByteArrayOutputStream();
		for (char c : data.toCharArray()) {
			memoryout.write(ct[c]);
		}

		return memoryout.toByteArray();
	}

	public static byte[] compress(byte[] data, String[] codetable) {
		String edata = new String();
		ByteArrayOutputStream memoryout = new ByteArrayOutputStream();
		for (byte b : data) {
			edata += codetable[b & 0xff];
			if (edata.length() > 8) {
				memoryout.write(Integer.parseInt(edata.substring(0, 8), 2) & 0xff);
				edata = edata.substring(8);
			}
		}
		byte fill = (byte) (8 - edata.length());

		int length = 8 - edata.length();

		for (int i = 0; i < length; i++) {
			edata += "0";
		}

		byte eb = (byte) Integer.parseInt(edata, 2);

		memoryout.write(eb & 0xff);
		memoryout.write(fill & 0xff);

		return memoryout.toByteArray();
	}

	private static String[] encode(byte[] data) {
		int[] count = new int[255];
		int values = 0;

		for (byte d : data) {
			if (count[d & 0xff] == 0) {
				values++;
			}
			count[d & 0xff]++;
		}

		Entry[] codetable = new Entry[values];
		int codecount = 0;

		for (int i = 0; i < count.length; i++) {
			if (count[i] == 0) {
				continue;
			}

			codetable[codecount++] = new Entry(i, count[i]);
		}

		Arrays.sort(codetable, new Comparator<Entry>() {
			@Override
			public int compare(Entry o1, Entry o2) {
				return o1.getValue() - o2.getValue();
			}
		});

		return encode(codetable);
	}

	private static String[] encode(Entry[] entries) {
		String left = "0";
		String right = "1";

		boolean isLeft = true;
		String[] codetable = new String[255];

		int i;
		for (i = entries.length - 1; i > 3; i--) {
			if (isLeft) {
				codetable[entries[i].getKey()] = left + "0";
				left += "1";
				isLeft = false;
			} else {
				codetable[entries[i].getKey()] = right + "1";
				right += "0";
				isLeft = true;
			}
		}

		codetable[entries[i--].getKey()] = left + "0";
		codetable[entries[i--].getKey()] = left + "1";
		codetable[entries[i--].getKey()] = right + "0";
		codetable[entries[i--].getKey()] = right + "1";

		return codetable;
	}

	static class Entry {
		public Entry(Integer key, Integer value) {
			this.key = key;
			this.value = value;
		}

		public String toString() {
			return key + " , " + value;
		}

		private Integer key;
		private Integer value;

		public Integer getKey() {
			return key;
		}

		public void setKey(Integer key) {
			this.key = key;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
	}
}
