package ch04.p2p;

import java.util.Enumeration;

import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.TextMessage;

import util.Constant;
import util.MQueueConnectionFactory;

public class LoanRequestQueueBrowser {
	public static void main(String[] args) throws Exception {
		QueueConnection conn = MQueueConnectionFactory.getQueueConnection();
		conn.start();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		QueueBrowser browser = session.createBrowser(session.createQueue(Constant.REQUEST_QUEUE_NAME));
		Enumeration<Message> enumeration = browser.getEnumeration();
		while (enumeration.hasMoreElements()) {
			Message message = enumeration.nextElement();
			System.err.println(((TextMessage) message).getText());
		}
		browser.close();
		conn.close();
		System.exit(0);
	}
}
