package util;

import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;


public class MQueueConnectionFactory {

	public static QueueConnection getQueueConnection() throws Exception {
		if (qcf == null) {
			Constant.DEFAULT_JNDI_ENV.put("connectionFactoryNames", Constant.DEFAULT_QUEUE_FACTORY);
			Context ctx = new InitialContext(Constant.DEFAULT_JNDI_ENV);
			qcf = (QueueConnectionFactory) ctx.lookup(Constant.DEFAULT_QUEUE_FACTORY);
		}
		return ((QueueConnectionFactory) qcf).createQueueConnection();
	}

	private static QueueConnectionFactory qcf = null;
}
