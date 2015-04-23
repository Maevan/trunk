package com.example.metrics;

import java.util.concurrent.TimeUnit;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.reporting.ConsoleReporter;

public class HistogramExample {
	private static Histogram histo = Metrics.newHistogram(HistogramExample.class, "histo-sizes");

	public static void main(String[] args) throws Exception {
		ConsoleReporter.enable(1, TimeUnit.SECONDS);

		for (int i = 0;; i++) {
			histo.update(i++);
			Thread.sleep(1000);
		}
	}
}
