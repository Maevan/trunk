package ch04.p2p;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import util.MQueueConnectionFactory;

public class QBorrowser {
	public QBorrowser(String requestQueueName, String responseQueueName) {
		try {
			queueConnect = MQueueConnectionFactory.getQueueConnection();
			queueSession = (QueueSession) queueConnect.createSession(false, Session.AUTO_ACKNOWLEDGE);
			requestQueue = queueSession.createQueue(requestQueueName);
			responseQueue = queueSession.createQueue(responseQueueName);

			queueConnect.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private void sendLoanRequest(double salary, double loanAmt) {
		try {
			MapMessage msg = queueSession.createMapMessage();
			msg.setDouble("Salary", salary);
			msg.setDouble("LoanAmount", loanAmt);
			msg.setJMSReplyTo(responseQueue);

			QueueSender queueSender = queueSession.createSender(requestQueue);
			queueSender.send(msg, DeliveryMode.NON_PERSISTENT, 5, 20000);
			String filter = "JMSCorrelationID = '" + msg.getJMSMessageID() + "'";
			QueueReceiver queueReceiver = queueSession.createReceiver(responseQueue, filter);
			TextMessage message = (TextMessage) queueReceiver.receive(30000);
			if (message == null) {
				System.out.println("QLender not responding");
			} else {
				System.out.println("Loan request was " + message.getText());
			}
		} catch (JMSException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private void exit() {
		try {
			queueConnect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(-1);
	}

	public static void main(String[] args) {
		QBorrowser borrowser = new QBorrowser(Constant.REQUEST_QUEUE_NAME, Constant.RESPONSE_QUEUE_NAME);
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("QBorrower Appliction Started");
		System.out.println("Press Enter to quit application");
		System.out.println("Enter:Salary,Loan_Amount");
		System.out.println("\ne.g 50000,120000");
		try {

			while (true) {
				System.err.println(">");
				String loanRequest = stdin.readLine();
				if (loanRequest == null || loanRequest.trim().length() == 0) {
					borrowser.exit();
					break;
				}
				StringTokenizer st = new StringTokenizer(loanRequest, ",");
				double salary = Double.parseDouble(st.nextToken().trim());
				double loanAmt = Double.parseDouble(st.nextToken().trim());

				borrowser.sendLoanRequest(salary, loanAmt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private QueueSession queueSession = null;
	private Queue requestQueue = null;
	private Queue responseQueue = null;
	private QueueConnection queueConnect;

}
