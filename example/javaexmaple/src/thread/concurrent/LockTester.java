package thread.concurrent;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTester {
	static Lock l = new ReentrantLock();
	static Condition getcondition = l.newCondition();// 不同线程用不同的condition阻塞
	static Condition putcondition = l.newCondition();
	static LinkedList<Integer> intlist = new LinkedList<Integer>();

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			executor.submit(new PutTask());
			executor.submit(new GetTask());
		}
	}

	static class PutTask implements Runnable {
		static AtomicInteger i = new AtomicInteger();

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(1);
					l.lock();
					if (intlist.size() > 10) {
						System.err.println("PutTask: int list size is " + intlist.size());
						getcondition.signalAll();
						putcondition.await();
						System.err.println("PutTask " + Thread.currentThread().getId() + " is notify!");
					}
					intlist.add(i.getAndAdd(1));
				} catch (InterruptedException e) {} finally {
					l.unlock();
				}
			}
		}
	}

	static class GetTask implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(5);
					l.lock();
					if (intlist.size() == 0) {
						System.err.println("GetTask: int list size is " + intlist.size());
						putcondition.signalAll();
						getcondition.await();
						System.err.println("GetTask " + Thread.currentThread().getId() + " is notify!");
					}
					System.err.println("GetTask " + Thread.currentThread().getId() + " remove " + intlist.removeFirst());
				} catch (InterruptedException e) {} finally {
					l.unlock();
				}
			}
		}
	}
}
