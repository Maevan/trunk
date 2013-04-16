package ch04.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import util.Constant;
import util.MQueueConnectionFactory;

public class QLender implements MessageListener {
	public QLender(String requestQueueName) {
		try {
			queueConnection = MQueueConnectionFactory.getQueueConnection();
			queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			requestQueue = queueSession.createQueue(requestQueueName);
			queueConnection.start();

			QueueReceiver queueReceiver = queueSession.createReceiver(requestQueue);
			queueReceiver.setMessageListener(this);
			System.out.println("Waiting for loan requests...");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			MapMessage msg = (MapMessage) message;
			double salary = msg.getDouble("Salary");
			double loanAmt = msg.getDouble("LoanAmount");
			boolean accepted = (salary / loanAmt) > (loanAmt < 200000 ? 0.25 : 0.33);
			System.out.println("% = " + (salary / loanAmt) + ",loan is " + (accepted ? "Accepted!" : "Declined"));
			TextMessage textMessage = queueSession.createTextMessage((accepted ? "Accepted!" : "Declined"));
			textMessage.setJMSCorrelationID(message.getJMSMessageID());

			QueueSender queueSender = queueSession.createSender((Queue) message.getJMSReplyTo());
			queueSender.send(textMessage);
			System.out.println("\nWaiting for loan requests...");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private void exit() {
		try {
			queueConnection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.exit(-1);
	}

	public static void main(String[] args) {
		QLender lender = new QLender(Constant.REQUEST_QUEUE_NAME);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("QLender application started");
		System.out.println("Press enter to quit application");
		try {
			reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lender.exit();
	}

	private QueueConnection queueConnection = null;
	private QueueSession queueSession = null;
	private Queue requestQueue = null;
}
