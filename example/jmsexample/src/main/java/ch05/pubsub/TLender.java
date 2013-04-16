package ch05.pubsub;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.BytesMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import util.Constant;
import util.MTopicConnectionFactory;

public class TLender {
	private TopicConnection connection = null;
	private TopicSession session = null;
	private Topic topic = null;

	public TLender(String topicName) {
		try {
			connection = MTopicConnectionFactory.getTopicConnection();
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = session.createTopic(topicName);
			connection.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void publishRate(double newRate) {
		try {
			BytesMessage message = session.createBytesMessage();
			message.writeDouble(newRate);
			TopicPublisher publisher = session.createPublisher(topic);
			publisher.publish(message);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void exit() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public static void main(String[] args) throws Exception {
		TLender lender = new TLender(Constant.RATE_TOPIC_NAME);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("TLender Application Started");
		System.out.println("Please enter to quit application");
		System.out.println("Enter: Rate");
		System.out.println("\ne.g. 6.8");
		while (true) {
			System.out.println(">");
			String rate = reader.readLine();
			if (rate == null || rate.trim().length() == 0) {
				lender.exit();
			}
			double newRate = Double.valueOf(rate);
			lender.publishRate(newRate);
		}
	}

}
