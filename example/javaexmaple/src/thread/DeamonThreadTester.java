package thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DeamonThreadTester {
    public static void main(String[] args) {
        Thread t = new Thread() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(86400);
                } catch (InterruptedException e) {}// 睡一天
            }

            public void destroy() {
                System.err.println("我被销毁了" + new Date());
            }
        };

        t.setDaemon(true);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {}
        System.err.println("结束");
    }
}
