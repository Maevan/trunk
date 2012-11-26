package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal使用样例
 * 
 * @author dell
 */
public class ThreadLocalTester {
    static final ThreadLocal<Integer> local = new ThreadLocal<Integer>();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executor.submit(new Task());
        }
        executor.shutdown();
    }

    private static class Task implements Runnable {
        @Override
        public void run() {
            Integer i = 0;
            while (i < 10) {
                local.set(++i);
                i = local.get();
                System.err.println("Thread " + Thread.currentThread().getId() + " i:" + i);
            }
        }
    }
}
