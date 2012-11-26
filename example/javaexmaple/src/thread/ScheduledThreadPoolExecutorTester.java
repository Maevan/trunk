package thread;

import java.util.Scanner;
import java.util.concurrent.ScheduledFuture;

public class ScheduledThreadPoolExecutorTester {


    public static void main(String[] args) {
        String a;
        Scanner input = new Scanner(System.in);
        for (a = "n"; a.equals("y");) {
            System.out.print("停止吗？y/n");
            a = input.next();

        }
    }

    static class TestTask implements Runnable {
        private int count = 0;
        private ScheduledFuture<?> future;

        public void run() {
            if (count++ == 5) {
                System.err.println("ByeBye");
                future.cancel(false);
            } else {
                System.err.println(Thread.currentThread().getId() + ":" + count + ";isDeamon:" + Thread.currentThread().isDaemon());
            }
        }

        public void setFuture(ScheduledFuture<?> future) {
            this.future = future;
        }
    }
}
