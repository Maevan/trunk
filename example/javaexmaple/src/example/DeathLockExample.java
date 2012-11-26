package example;

import java.util.concurrent.atomic.AtomicInteger;

public class DeathLockExample {
	public static void main(String[] args) {
		final AtomicInteger x = new AtomicInteger(0);
		final AtomicInteger y = new AtomicInteger(0);

		new Thread() {
			public void run() {
				while (true) {
					synchronized (x) {
						synchronized (y) {
							x.addAndGet(1);
						}
					}
					System.err.println("x " + x.toString());
				}
			}
		}.start();

		new Thread() {
			public void run() {
				while (true) {
					synchronized (y) {
						synchronized (x) {
							y.addAndGet(1);
						}
					}
					System.err.println("y " + y.toString());
				}
			}
		}.start();
	}
}
