package net.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class MySSLSocketFactory {

	public static final SSLSocket getSSLSocket(InetAddress address, int port, SSLContext context) throws IOException {
		SSLSocket socket = (SSLSocket) context.getSocketFactory().createSocket(address, port);

		String[] supported = socket.getSupportedCipherSuites();
		socket.setEnabledCipherSuites(supported);

		return socket;
	}

	public static final SSLContext getSSLContext(String protocol, String algorithm, String type, String keyPath, char[] pwd) throws Exception {
		SSLContext context = SSLContext.getInstance(protocol);
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);

		KeyStore ks = KeyStore.getInstance(type);
		KeyStore tks = KeyStore.getInstance(type);

		ks.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(keyPath), pwd);
		tks.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(keyPath), pwd);

		kmf.init(ks, pwd);
		tmf.init(tks);

		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		return context;
	}

	public static final SSLServerSocket getSSLServerSocket(int port, SSLContext context) throws Exception {
		SSLServerSocketFactory factory = context.getServerSocketFactory();

		return (SSLServerSocket) factory.createServerSocket(port);
	}
}
