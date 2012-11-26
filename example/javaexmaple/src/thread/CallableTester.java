package thread;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Callable使用样例
 * 
 * @author Lv9
 * 
 */
public class CallableTester {
	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		List<Future<Integer>> list = new LinkedList<Future<Integer>>();

		for (int i = 0; i < 5; i++) {
			Future<Integer> future = executor.submit(new CallableImpl());

			list.add(future);
		}

		executor.shutdown();

		while (!list.isEmpty()) {
			for (Iterator<Future<Integer>> iterator = list.iterator(); iterator.hasNext();) {
				Future<Integer> future = iterator.next();
				if (future.isDone()) {
					// 调用get方法会阻塞 在此之前建议使用isDone方法确认线程是否执行完毕
					// 也可以调用cancel来取消该任务的执行
					Integer result = future.get();

					if (result != null) {
						System.err.println(result);
						iterator.remove();
					}
				}
			}
		}
	}
}

class CallableImpl implements Callable<Integer> {
	static int i = 0;
	static Random random = new Random();

	@Override
	public Integer call() throws Exception {
		Thread.sleep(random.nextInt(10) * 1000);
		return i++;
	}
}