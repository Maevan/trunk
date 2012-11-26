package thread;

import java.util.concurrent.TimeUnit;

public class Interrupting3 {
    public static void main(String[] args) {
        Thread t = new Thread(new InterruptingTask());
        t.start();
        System.err.println("过五秒就踹醒那个死线程");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {}
        t.interrupt();
    }

    static class InterruptingTask implements Runnable {
        @Override
        public void run() {
            try {
                System.err.println("I'm sleep~~~");
                TimeUnit.SECONDS.sleep(60 * 60 * 24);
            } catch (InterruptedException e) {
                System.err.println("Aaaaaaa~~~");
            }
        }
    }
}
