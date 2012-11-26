package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.PrintUtils.println;

public class HorseRace {
	static final int FINISH_LINE = 75;
	private List<Horse> horses = new ArrayList<Horse>();
	private ExecutorService executor = Executors.newCachedThreadPool();
	private CyclicBarrier barrier;

	public HorseRace(int horsecount, final int pause) {
		barrier = new CyclicBarrier(horsecount, new Runnable() {

			@Override
			public void run() {
				StringBuilder s = new StringBuilder();
				for (int i = 0; i < FINISH_LINE; i++) {
					s.append("=");
				}
				println(s);
				for (Horse horse : horses) {
					println(horse.tracks());
				}
				for (Horse horse : horses) {
					if (horse.getStrides() >= FINISH_LINE) {
						println(horse, "won!");
						executor.shutdownNow();
						return;
					}
				}
				try {
					TimeUnit.MILLISECONDS.sleep(pause);
				} catch (InterruptedException e) {
					println("barrier-action sleep interrupted");
				}
			}
		});

		for (int i = 0; i < horsecount; i++) {
			Horse horse = new Horse(barrier);
			horses.add(horse);
			executor.execute(horse);
		}
	}

	public static void main(String[] args) {
		int horsecount = 7;
		int pause = 200;

		new HorseRace(horsecount, pause);
	}
}

class Horse implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final CyclicBarrier barrier;
	private int strides = 0;
	private static Random rand = new Random(47);

	public Horse(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	public synchronized int getStrides() {
		return strides;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					strides += rand.nextInt(3);
				}
				barrier.await();//当所有参与者调用wait的时候 HorseRace持有的线程对象将会开始运行 
			}
		} catch (InterruptedException e) {} catch (BrokenBarrierException e) {}
	}

	@Override
	public String toString() {
		return "Horse " + id + " ";
	}

	public String tracks() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < getStrides(); i++) {
			s.append("*");
		}
		s.append(id);

		return s.toString();
	}
}