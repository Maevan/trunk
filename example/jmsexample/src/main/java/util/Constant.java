package util;

import java.util.Properties;

import javax.naming.Context;

public class Constant {
	public static final Properties DEFAULT_JNDI_ENV = new Properties();
	public static final String DEFAULT_QUEUE_FACTORY = "QueueCF";
	public static final String REQUEST_QUEUE_NAME = "QLender";
	public static final String RESPONSE_QUEUE_NAME = "QLoan";
	public static final String DEFAULT_TOPIC_FACTORY = "TopicCF";
	public static final String RATE_TOPIC_NAME = "RateTopic";
	static {
		DEFAULT_JNDI_ENV.put(Context.SECURITY_PRINCIPAL, "system");
		DEFAULT_JNDI_ENV.put(Context.SECURITY_CREDENTIALS, "manager");
		DEFAULT_JNDI_ENV.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		DEFAULT_JNDI_ENV.put(Context.PROVIDER_URL, "tcp://localhost:61616");
		DEFAULT_JNDI_ENV.put("connectionFactoryNames", DEFAULT_QUEUE_FACTORY);
	}
}
