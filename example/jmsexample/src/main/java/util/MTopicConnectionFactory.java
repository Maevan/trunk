package util;

import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;


public class MTopicConnectionFactory {

	public static TopicConnection getTopicConnection() throws Exception {
		if (tcf == null) {
			Constant.DEFAULT_JNDI_ENV.put("connectionFactoryNames", Constant.DEFAULT_TOPIC_FACTORY);
			Context ctx = new InitialContext(Constant.DEFAULT_JNDI_ENV);
			tcf = (TopicConnectionFactory) ctx.lookup(Constant.DEFAULT_TOPIC_FACTORY);
		}
		return ((TopicConnectionFactory) tcf).createTopicConnection();
	}

	private static TopicConnectionFactory tcf = null;
}
