package jdbc;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestConnectionPool {

	public static void main(String[] args) throws Exception {
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 0; i < 500; i++) {
			service.submit(new TestConnectPoolThread(connectionPool));
		}
	}
}

class TestConnectPoolThread extends Thread {
	public Random random = new Random();
	ConnectionPool connectionPool = null;

	public TestConnectPoolThread(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public void run() {
		boolean isError = false;
		while (!isError) {
			try {
				Thread.sleep(Math.abs(random.nextInt() % 200));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			try {
				connectionPool
						.executeQuery("SELECT * FROM example_table e LIMIT 0,1000");
			} catch (Exception e) {
				e.printStackTrace();
				isError = true;
			}
		}
	}
}