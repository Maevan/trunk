package example;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UTF8Decode {
	public static void main(String[] args) throws Exception {
		Matcher m = Pattern.compile("=([\\w]{2})").matcher("=e4=b8=ad=e6=96=87");
		String result = "";
		int len3 = 0x7 << 5;
		int len2 = 0x3 << 4;
		int head = 0x1 << 7;
		while (m.find()) {
			Integer c = Integer.parseInt(m.group(1), 16);
			int len = 0;
			if ((c | len3) == c) {
				c |= len3;
				len = 2;
			} else if ((c | len2) == c) {
				c |= len2;
				len = 1;
			}
			for (int i = 0; i < len; i++) {
				m.find();
				Integer suffix = Integer.parseInt(m.group(1), 16) ^ head;
				c = c << 6;
				c |= suffix;
			}
			result += (char) c.intValue();
		}
		System.err.println(result);
	}
}
