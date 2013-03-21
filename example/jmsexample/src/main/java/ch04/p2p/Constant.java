package ch04.p2p;

import java.util.Properties;

import javax.naming.Context;

public class Constant {
	public static final Properties DEFAULT_JNDI_ENV = new Properties();
	public static final String DEFAULT_FACTORY = "QueueCF";
	public static final String REQUEST_QUEUE_NAME = "QLender";
	public static final String RESPONSE_QUEUE_NAME = "QLoan";
	
	static {
		DEFAULT_JNDI_ENV.put(Context.SECURITY_PRINCIPAL, "system");
		DEFAULT_JNDI_ENV.put(Context.SECURITY_CREDENTIALS, "manager");
		DEFAULT_JNDI_ENV.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		DEFAULT_JNDI_ENV.put(Context.PROVIDER_URL, "tcp://localhost:61616");
		DEFAULT_JNDI_ENV.put("connectionFactoryNames", DEFAULT_FACTORY);
	}
}
