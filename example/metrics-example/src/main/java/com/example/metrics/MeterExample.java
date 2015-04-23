package com.example.metrics;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Meter;
import com.yammer.metrics.reporting.ConsoleReporter;

public class MeterExample {
	private static Meter meter = Metrics.newMeter(MeterExample.class, "callbacks", "callbacks", TimeUnit.SECONDS);
	private static Random random = new Random();

	public static void doSomething() {
		int tmp = random.nextInt(10);
		for (int i = 0; i < tmp; i++) {
			meter.mark();
		}
	}

	public static void main(String[] args) throws Exception {
		ConsoleReporter.enable(2, TimeUnit.SECONDS);
		while (true) {
			doSomething();
			TimeUnit.SECONDS.sleep(1);
		}
	}
}
