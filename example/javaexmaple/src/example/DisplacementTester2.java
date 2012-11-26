package example;

public class DisplacementTester2 {
	enum Feature {
		FAT, LAZY, JOKES, SHAMELESS, MEAN, CUNNING, LOVE
	}

	public static int FAT_I = 1, LAZY_I = 1 << 1, JOKES_I = 1 << 2,
			SHAMELESS_I = 1 << 3, MEAN_I = 1 << 4, CUNNING_I = 1 << 5,
			LOVE_I = 1 << 6;

	public static void main(String[] args) {
		describePerson(FAT_I | SHAMELESS_I);
		describePerson(Feature.FAT, Feature.SHAMELESS);
	}

	/**
	 * 位移的解决方式
	 * */
	public static void describePerson(int state) {
		System.err.println();
		if ((state | FAT_I) == state) {
			System.err.print(" 胖");
		}
		if ((state | LAZY_I) == state) {
			System.err.print(" 懒惰");
		}
		if ((state | JOKES_I) == state) {
			System.err.print(" 恶搞");
		}
		if ((state | SHAMELESS_I) == state) {
			System.err.print(" 无耻");
		}
		if ((state | MEAN_I) == state) {
			System.err.print(" 卑劣");
		}
		if ((state | CUNNING_I) == state) {
			System.err.print(" 狡猾");
		}
		if ((state | LOVE_I) == state) {
			System.err.print(" 有爱");
		}
	}

	/**
	 * 你的解决方式
	 * */
	public static void describePerson(Feature... features) {
		for (Feature feature : features) {
			switch (feature) {
			case FAT:
				System.err.print(" 胖");
				break;
			case LAZY:
				System.err.print(" 懒惰");
				break;
			case JOKES:
				System.err.print(" 恶搞");
				break;
			case SHAMELESS:
				System.err.print(" 无耻");
				break;
			case MEAN:
				System.err.print(" 卑劣");
				break;
			case CUNNING:
				System.err.print(" 狡猾");
				break;
			case LOVE:
				System.err.print(" 有爱");
				break;
			}
		}
	}
}
