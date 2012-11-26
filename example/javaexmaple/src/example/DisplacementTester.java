package example;

public class DisplacementTester {
	public static void main(String[] args) {
		int i = 2;
		byte b = 1;
		Object o = Integer.MAX_VALUE + 200L;
		System.err.println(o);
		System.err.println(o.getClass());
		System.err.println(i << 1);// 向左移位
		System.err.println(i >> 1);// 向右移位
		System.err.println(i << 1);
		System.err.println(i & 1);// 与操作符
		System.err.println(i | 1);// 或操作符
		System.err.println(i ^ 1);// 异或操作符(二进制比较两位不同的时候返回1 否则为0)
	}
}
