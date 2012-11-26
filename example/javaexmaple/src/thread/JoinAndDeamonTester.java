package thread;

public class JoinAndDeamonTester {
	public static void main(String[] args) {
		System.err.println("task begin...");
		Thread task1 = new Thread(new JoinTask(5));
		Thread task2 = new Thread(new JoinTask(10));
		task1.start();
		try {
			task1.join();
			task2.setDaemon(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println("task end!");
	}
}

class JoinTask implements Runnable {
	private int i;

	public JoinTask(int i) {
		this.i = i;
	}

	public void run() {
		try {
			Thread.sleep(1000 * i);
			System.err.println("ok!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}