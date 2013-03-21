package ch02.chat;

import java.util.Properties;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Chat implements javax.jms.MessageListener {
	private TopicSession pubSession;
	private TopicPublisher publisher;
	private TopicConnection connection;
	private String username;

	public Chat(String topicFactory, String topicName, String username) throws Exception {
		InitialContext ctx = new InitialContext(DEFAULT_JNDI_ENV);

		// 查找一个JMS连接工厂并创建连接
		TopicConnectionFactory conFactory = (TopicConnectionFactory) ctx.lookup(topicFactory);
		TopicConnection connection = conFactory.createTopicConnection();
		// 创建两个JMS会话
		TopicSession pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		// 查找一个JMS主题
		Topic chatTopic = (Topic) ctx.lookup(topicName);

		// 创建一个JMS的发布者和订阅者。createSubscriber的参数null是个消息选择器和noLocal标记的一个真值
		// 它表明这个发布者生产的消息不应该被自己消费
		TopicPublisher publisher = pubSession.createPublisher(chatTopic);
		TopicSubscriber subscriber = subSession.createSubscriber(chatTopic, null, true);

		// 设置一个JMS消息侦听器
		subscriber.setMessageListener(this);

		this.connection = connection;
		this.publisher = publisher;
		this.username = username;
		this.pubSession = pubSession;
		// 启动JMS连接 允许传送消息
		connection.start();
	}

	public void onMessage(Message message) {
		TextMessage tmessage = (TextMessage) message;
		try {
			System.err.println(tmessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	protected void writeMessage(String text) throws JMSException {
		TextMessage message = pubSession.createTextMessage();
		message.setText(username + ": " + text);
		publisher.publish(message);
	}

	public void close() throws JMSException {
		connection.close();
	}

	public static void main(String[] args) {
		try {
			Chat chat = new Chat(DEFAULT_FACTORY, DEFAULT_TOPIC_NAME, DEFAULT_USERNAME);
			Scanner scanner = new Scanner(System.in);

			while (true) {
				String s = scanner.nextLine();
				if (s.equalsIgnoreCase("exit")) {
					chat.close();
					System.exit(0);
				} else {
					chat.writeMessage(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final Properties DEFAULT_JNDI_ENV = new Properties();
	private static final String DEFAULT_FACTORY = "TopicCF";
	private static final String DEFAULT_TOPIC_NAME = "topic1";
	private static final String DEFAULT_USERNAME = "Lv9";
	static {
		DEFAULT_JNDI_ENV.put(Context.SECURITY_PRINCIPAL, "system");
		DEFAULT_JNDI_ENV.put(Context.SECURITY_CREDENTIALS, "manager");
		DEFAULT_JNDI_ENV.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		DEFAULT_JNDI_ENV.put(Context.PROVIDER_URL, "tcp://localhost:61616");
		DEFAULT_JNDI_ENV.put("connectionFactoryNames", "TopicCF");
		DEFAULT_JNDI_ENV.put("topic.topic1", "jms.topic1");
	}
}
