package util;

import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;

import ch04.p2p.Constant;

public class MQueueConnectionFactory {

	public static QueueConnection getQueueConnection() throws Exception {
		if (qcf == null) {
			Context ctx = new InitialContext(Constant.DEFAULT_JNDI_ENV);
			qcf = (QueueConnectionFactory) ctx.lookup(Constant.DEFAULT_FACTORY);
		}
		return ((QueueConnectionFactory) qcf).createQueueConnection();
	}

	private static QueueConnectionFactory qcf = null;
}
