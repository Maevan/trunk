package net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

public class ProxyTester {
	static final List<Proxy> PROXIES = new LinkedList<Proxy>();
	static {
		try {
			PROXIES.add(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(InetAddress.getLocalHost(), 80)));
			PROXIES.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getLocalHost(), 80)));
		} catch (UnknownHostException e) {}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		ProxySelector.setDefault(new ProxySelector() {
			@Override
			public List<Proxy> select(URI uri) {
				return PROXIES;
			}

			@Override
			public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
				
			}
		});

//		 java.net.Socket client = new java.net.Socket("www.baidu.com", 80);
	}
}
