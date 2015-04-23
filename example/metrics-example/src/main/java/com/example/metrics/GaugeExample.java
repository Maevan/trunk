package com.example.metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.reporting.ConsoleReporter;

public class GaugeExample {
	public static void main(String[] args) throws Exception {
		new Thread(new ExampleQueueAdder()).start();
		ConsoleReporter.enable(2, TimeUnit.SECONDS);
		TimeUnit.SECONDS.sleep(60);
	}

	public static class ExampleQueueAdder implements Runnable {
		public ExampleQueueAdder() {
			this.elements = new ArrayList<>();
			Metrics.newGauge(ExampleQueueAdder.class, "queue-gauge-jobs", new Gauge<Integer>() {
				@Override
				public Integer value() {
					return elements.size();
				}
			});
		}

		public void run() {
			while (true) {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
				}
				elements.add(String.valueOf(System.currentTimeMillis()));
			}
		}

		private List<String> elements;
	}
}
