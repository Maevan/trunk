package com.example.spark.streaming;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import scala.Tuple2;

public class JustPrint {
	// ./spark-submit --master spark://s6.hadoop.com:7077 --class
	// com.example.spark.streaming.JustPrint --name JustPrint --jars
	// ../elib/spark-streaming-example-0.0.1-SNAPSHOT-jar-with-dependencies.jar
	// ../elib/spark-streaming-example-0.0.1-SNAPSHOT-jar-with-dependencies.jar
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		JavaStreamingContext context = new JavaStreamingContext(new JavaSparkContext(), new Duration(1000));
		JavaPairReceiverInputDStream<String, String> stream = KafkaUtils.createStream(context,
				"s1.hadoop.com,s2.hadoop.com,s3.hadoop.com/kafka", "spark-just-print", getTopicPartitions());
		JavaDStream<Integer> mstream = stream.map(new Function<Tuple2<String, String>, Integer>() {
			@Override
			public Integer call(Tuple2<String, String> pair) throws Exception {
				return 1;
			}
		});

		JavaDStream<Integer> rstream = mstream.reduceByWindow(new Function2<Integer, Integer, Integer>() {
			@Override
			public Integer call(Integer c1, Integer c2) throws Exception {
				return c1 + c2;
			}
		}, new Duration(1000 * 6), new Duration(1000 * 1));

		rstream.foreach(new Function<JavaRDD<Integer>, Void>() {
			@Override
			public Void call(JavaRDD<Integer> rdd) throws Exception {
				int count = 0;
				for (Integer i : rdd.collect()) {
					count += i;
				}
				System.err.println(new Date() + ":" + count);
				return null;
			}
		});
		context.start();
	}

	public static Map<String, Integer> getTopicPartitions() {
		Map<String, Integer> topicPartitions = new HashMap<>();
		topicPartitions.put("access_log", 0);
		topicPartitions.put("access_log", 1);
		
		return topicPartitions;
	}
}
