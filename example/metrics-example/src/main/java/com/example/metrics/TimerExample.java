package com.example.metrics;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;
import com.yammer.metrics.reporting.ConsoleReporter;

public class TimerExample {
	private static Timer timer = Metrics.newTimer(TimerExample.class, "responses", TimeUnit.MILLISECONDS,
			TimeUnit.SECONDS);

	public static void main(String[] args) throws Exception {
		ConsoleReporter.enable(2, TimeUnit.SECONDS);
		Random random = new Random();
		while (true) {
			TimerContext context = timer.time();
			Thread.sleep(random.nextInt(1000));
			context.stop();
		}
	}
}
