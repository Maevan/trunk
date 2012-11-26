package example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherExample {
	public static void main(String[] args) {
		Matcher matcher = Pattern.compile("([\\u4e00-\\u9fa5]+)\\!([\\u4e00-\\u9fa5]+)\\?([\\u4e00-\\u9fa5]+)").matcher("我在!马路边?捡到一分钱");
		while (matcher.find()) {
			System.out.println(matcher.group(0));
			System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));
			System.out.println(matcher.group(3));
		}
	}
}
