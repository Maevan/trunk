package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTester {
    private static final Lock L = new ReentrantLock();
    private static final Condition C1 = L.newCondition();
    private static final Condition C2 = L.newCondition();

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    L.lock();
                    try {
                        System.err.println("T1 get lock...");
                        C1.await();
                        System.err.println("T1 Wake up");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        L.unlock();
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    L.lock();
                    try {
                        System.err.println("T2 get lock...");
                        C2.await();
                        System.err.println("T2 Wake up");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        L.unlock();
                    }
                }
            }
        }.start();
        while (true) {
            L.lock();
            System.err.println("Signaler get lock");
            try {
                TimeUnit.SECONDS.sleep(2);
                System.err.println("Singal T1");
                C1.signalAll();
                System.err.println("Singal T2");
                TimeUnit.SECONDS.sleep(2);
                C2.signalAll();
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                L.unlock();
            }
        }
    }
}
