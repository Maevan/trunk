package com.example.metrics;

import java.util.concurrent.TimeUnit;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Counter;
import com.yammer.metrics.reporting.ConsoleReporter;

public class CounterExample {
	private static final Counter counter = Metrics.newCounter(CounterExample.class, "current-threads-counter");

	public static void main(String[] args) throws Exception {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					open();
				}
			}
		}.start();
		TimeUnit.SECONDS.sleep(10);
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					close();
				}
			}
		}.start();
		ConsoleReporter.enable(2, TimeUnit.SECONDS);
	}

	public static void open() {
		counter.inc();
	}

	public static void close() {
		counter.dec();
	}
}
