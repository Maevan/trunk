package util;

import java.io.OutputStream;
import java.io.PrintWriter;

public class PrintUtils {
	public static final OutputStream DEFAULT_OUT = System.out;

	public static final String DEFAULT_SPLIT = " ";
	public static final String DEFAULT_END = "\r\n";

	public static final ThreadLocal<OutputStream> outpool = new ThreadLocal<OutputStream>();
	public static final ThreadLocal<String> endpool = new ThreadLocal<String>();
	public static final ThreadLocal<String> splitpool = new ThreadLocal<String>();

	public static void setOut(OutputStream out) {
		outpool.set(out);
	}

	public static void setEnd(String end) {
		endpool.set(end);
	}
	
	public static void setSplit(String split){
		splitpool.set(split);
	}

	/**
	 * 按照指定格式输出内容
	 * 
	 * @param args
	 *            输出内容
	 */
	public static void println(Object... args) {
		if (args == null || args.length == 0) {
			return;
		}
		OutputStream out = outpool.get();
		String end = endpool.get();
		String split = splitpool.get();
		PrintWriter writer = null;

		if (out == null) {
			out = DEFAULT_OUT;
			outpool.set(out);
		}

		if (end == null) {
			end = DEFAULT_END;
			endpool.set(end);
		}

		if (split == null) {
			split = DEFAULT_SPLIT;
			splitpool.set(split);
		}

		writer = new PrintWriter(out);

		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				writer.append(split);
			}
			if (args[i] == null) {
				writer.append("null");
			} else {
				writer.append(args[i].toString());
			}
		}

		writer.append(end);
		writer.flush();
	}

	public static void main(String[] args) {
		setEnd("\\");
		setSplit(" ");
		println(1, 2, 3);
	}
}
