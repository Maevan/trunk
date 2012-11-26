package thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitTester {
    private static Date date = new Date();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread() {
                public void run() {
                    testWait();
                }
            }.start();
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (date) {
            date.notifyAll();
        }
    }

    public static void testWait() {
        synchronized (date) {
            try {
                System.err.println("开始等待:" + Thread.currentThread().getId());
                date.wait();
                System.err.println("被唤醒:" + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
