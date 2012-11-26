package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTester {
	private static final ReentrantLock REENTRANT_LOCK = new ReentrantLock(false);
	private static final Condition CONDITION_1 = REENTRANT_LOCK.newCondition();
	private static final Condition CONDITION_2 = REENTRANT_LOCK.newCondition();

	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new Task1());
		Thread t2 = new Thread(new Task2());
		t1.start();
		TimeUnit.SECONDS.sleep(2);
		t2.start();
		TimeUnit.SECONDS.sleep(2);
		t2.interrupt();// 取消线程2的阻塞

		for (int i = 0; i < 5; i++) {
			new Thread(new Task2()).start();
		}
		for (int i = 0; i < 5; i++) {
			new Thread(new Task3()).start();
		}
		TimeUnit.SECONDS.sleep(5);// 等待五秒 让上面的线程全部进入等待状态
		REENTRANT_LOCK.lock();
		CONDITION_1.signalAll();// 唤醒等待在条件1上的线程
		TimeUnit.SECONDS.sleep(5);// 分批唤醒
		CONDITION_2.signalAll();// 唤醒等待在条件2上的线程
		REENTRANT_LOCK.unlock();
	}

	static class Task1 implements Runnable {
		public void run() {
			try {
				REENTRANT_LOCK.lockInterruptibly();
				System.err.println("Task1获取锁定" + Thread.currentThread().getId());
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				System.err.println("Task1被中断了呢 啊哈哈哈~" + Thread.currentThread().getId());
			} finally {
				if (REENTRANT_LOCK.isHeldByCurrentThread()) {
					REENTRANT_LOCK.unlock();
				}
			}
		}
	}

	static class Task2 implements Runnable {
		public void run() {
			try {
				REENTRANT_LOCK.lockInterruptibly();
				System.err.println("Task2获取锁定" + Thread.currentThread().getId());
				CONDITION_1.await();
				System.err.println("Task2被唤醒了哦~" + Thread.currentThread().getId());
			} catch (InterruptedException e) {
				System.err.println("Task2被中断了呢 啊哈哈哈~" + Thread.currentThread().getId());
			} finally {
				if (REENTRANT_LOCK.isHeldByCurrentThread()) {
					REENTRANT_LOCK.unlock();
				}
			}
		}
	}

	static class Task3 implements Runnable {
		public void run() {
			try {
				REENTRANT_LOCK.lockInterruptibly();
				System.err.println("Task3获取锁定" + Thread.currentThread().getId());
				CONDITION_2.await();
				System.err.println("Task3被唤醒了哦~" + Thread.currentThread().getId());
			} catch (InterruptedException e) {
				System.err.println("Task3被中断了呢 啊哈哈哈~" + Thread.currentThread().getId());
			} finally {
				if (REENTRANT_LOCK.isHeldByCurrentThread()) {
					REENTRANT_LOCK.unlock();
				}
			}
		}
	}
}