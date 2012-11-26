package thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.PrintUtils.*;

/**
 * CountDownLatch的样例
 * 
 * @see CountDownLatch的计数操作只能触发一次 如果想重置的话可以用CyclicBarrier
 * @author Lv9
 */
public class StudyCountDownLatch {
	static final int SIZE = 100;

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(SIZE);

		for (int i = 0; i < 10; i++) {
			exec.execute(new WaitingTask(latch));
		}

		for (int i = 0; i < SIZE; i++) {
			exec.execute(new TaskPortion(latch));
		}

		println("Launched all tasks");

		exec.shutdown();

	}
}

class TaskPortion implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private static Random rand = new Random(47);
	private final CountDownLatch latch;

	TaskPortion(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			doWork();
			latch.countDown();// 通过调用countDown来减少计数值
		} catch (InterruptedException e) {
			println(this, "interrupted");
		}
	}

	public void doWork() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
		println(this, "completed");
	}

	public String toString() {
		return String.format("%1$-3d ", id);
	}
}

class WaitingTask implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final CountDownLatch latch;

	WaitingTask(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			// 调用await()阻塞 知道latch中的计数值为0 则开始运行 类似join 只不过并不依赖具体线程的执行结果
			// 而是根据计数器的剩余数来决定是否运行完毕
			latch.await();
			println("Latch barrier passed for", this);
		} catch (InterruptedException e) {
			println(this, " interrupted");
		}
	}

	@Override
	public String toString() {
		return String.format("WaitingTask %1$-3d ", id);
	}
}