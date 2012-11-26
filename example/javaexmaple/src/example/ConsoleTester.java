package example;

import java.io.Console;

public class ConsoleTester {

	public static void main(String[] args) {
		boolean success = false;
		int count = 0;
		Console cons;
		char[] passwd;
		cons = System.console();
		while (true) {
			System.out.print("输入密码：");
			passwd = cons.readPassword();
			count++;
			String password = new String(passwd);
			if (password.equals("Tiger123")) {
				success = true;
				System.out.println(count + "次输入结果正确！");
				break;
			} else {
				System.out.println(count + "次输入密码" + password + "不正确");
			}
			if (count == 3) {
				System.out.println(count + "次输入的结果都不正确");
				System.exit(0);
			}
		}
		if (success) {
			System.out.println("你好，欢迎你！");
		}
	}

}
