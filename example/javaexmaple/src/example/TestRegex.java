package example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {
	public static void main(String[] args) {
		// String str =
		// "ee,tr,fun(a,fk,ererw,ttt(aabc,eee,we,rr,),adf,rrrr,erwer,werqqqq,erttte,tttt,bbb,),adf,ade";
		// String regx =
		// "([a-z]{2}),([a-z]{2}),(fun\\(.+\\)),([a-z]{3}),([a-z]{3})";
		//
		// Pattern p = Pattern.compile(regx);
		// Matcher m = p.matcher(str);
		// if (m.find()) {
		// System.out.println(m.group(1));
		// System.out.println(m.group(2));
		// System.out.println(m.group(3));
		// System.out.println(m.group(4));
		// System.out.println(m.group(5));
		// }

		String data = "<1xxx1><ul><1xxx1><1yyy1><1xxx1><1xxx1></ul>";
		String regex = "<ul>(<\\d+[a-zA-Z]+\\d+>)+</ul>";

		Matcher matcher = Pattern.compile(regex).matcher(data);
		while (matcher.find()) {
			String data2 = matcher.group();
			String regex2 = "(?:<\\d+)([a-zA-Z]+)(?:\\d+>)";
			Matcher matcher2 = Pattern.compile(regex2).matcher(data2);
			while(matcher2.find()){
				System.err.println(matcher2.group(1));
			}
		}
	}
}
