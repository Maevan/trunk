package example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

public class ArrayBlockingQueueTester {
	public static void main(String[] args) {
		final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(50000);
		final CountDownLatch latch = new CountDownLatch(50000);

		for (int i = 0; i < 50000; i++) {
			queue.add(i);
		}

		for (int i = 0; i < 5; i++) {
			new Thread() {
				public void run() {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {}
					while (latch.getCount() > 0) {
						System.out.println("count: " + latch.getCount() + " queue size:" + queue.size());
						Integer i = queue.poll();
						if (i == null) {
							continue;
						}

						if (i % 2 == 0) {
							latch.countDown();
						} else {
							queue.offer(++i);
						}
					}
				}
			}.start();
		}
	}
}