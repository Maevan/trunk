package example;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class SearchFileUtils {
	public static List<File> doIt(File root, String prefix, String suffix) {
		if (!root.isDirectory()) {
			throw new RuntimeException("It's not directory!");
		}
		List<File> result = new LinkedList<File>();
		File[] files = root.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				result.addAll(doIt(file, prefix, suffix));
			} else if (file.getName().startsWith(prefix) && file.getName().endsWith(suffix)) {
				result.add(file);
			}
		}
		return result;
	}
}
