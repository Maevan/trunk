package example;

import java.util.LinkedList;

public class TestRunnable {
    public static void main(String[] args) {
        RunnableImpl impl = new RunnableImpl();
        impl.start();

        for (int i = 0; i < 1000; i++) {
            impl.add(i);
        }
        impl.shutdown();
        try {
            impl.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class RunnableImpl extends Thread {
        private LinkedList<Integer> queue;

        private boolean isShutDown = false;
        long count = 0;

        public RunnableImpl() {
            queue = new LinkedList<Integer>();
        }

        public void add(Integer i) {
            if (!isShutDown) {
                synchronized (queue) {
                    queue.addLast(i);
                    queue.notifyAll();
                }
            }
        }

        public void run() {
            while (true) {
                Integer i = null;
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        if (isShutDown) {
                            break;
                        } else {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {}
                        }
                    }
                    i = queue.removeFirst();
                    System.err.println(i);
                    for (int y = 0; y < i * 10000; y++)
                        count++;
                }
            }
        }

        public Long getCount() {
            return count;
        }

        public void shutdown() {
            isShutDown = true;
        }
    }
}
