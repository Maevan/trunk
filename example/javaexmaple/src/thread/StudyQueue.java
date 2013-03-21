package thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class StudyQueue {
	static final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();// 使用这个队列会自动帮我们同步

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(50);

		for (int i = 0; i < 20; i++) {
			executor.submit(new Consumer());
		}
		for (int i = 0; i < 30; i++) {
			executor.submit(new Producer());
		}
	}

	static class Consumer implements Runnable {
		Random random = new Random();

		public void run() {
			while (true) {
				try {
					// 处理抓取结果
					// Thread.sleep(random.nextInt(2000));
					System.err.println("Consum " + queue.take());
				} catch (InterruptedException e) {}
			}
		}
	}

	static class Producer implements Runnable {
		Random random = new Random();

		public void run() {
			int count = 0;
			while (true) {
				// 抓取任务
				// Thread.sleep(random.nextInt(2000));
				System.err.println("Produce " + ++count);
				queue.add(count);
			}
		}
	}
}
