package thread;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {
	public static void main(String[] args) {
		ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(2);
		executor.scheduleWithFixedDelay(new Task("德问是个好论坛.."), 0, 5, TimeUnit.SECONDS);
		executor.scheduleWithFixedDelay(new Task("管理都是大坏蛋.."), 20, 5, TimeUnit.SECONDS);// 延迟20秒后再执行
	}

	static class Task implements Runnable {
		/**
		 * run
		 */
		public void run() {
			System.out.println(message);
		}

		public Task(String message) {
			this.message = message;
		}

		public final String message;
	}
}
