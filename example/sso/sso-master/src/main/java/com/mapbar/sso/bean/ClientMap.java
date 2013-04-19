package com.mapbar.sso.bean;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientMap {
	private String name;
	private Map<String, Client> clientMap = new ConcurrentHashMap<String, Client>();

	public ClientMap() {

	}

	public ClientMap(String name, String logoutURL, String jsessionId) {
		this.name = name;
		add(logoutURL, jsessionId);
	}

	public Collection<Client> getClients() {
		return clientMap.values();
	}

	public void add(String logoutURL, String jsessionId) {
		clientMap.put(logoutURL, new Client(logoutURL, jsessionId));
	}

	public String getName() {
		return name;
	}

	public static class Client {
		private String logoutURL;
		private String jsessionId;

		public Client(String loggotURL, String jsessionId) {
			this.logoutURL = loggotURL;
			this.jsessionId = jsessionId;
		}

		public String getLogoutURL() {
			return logoutURL;
		}

		public void setLogoutURL(String logoutURL) {
			this.logoutURL = logoutURL;
		}

		public String getJsessionId() {
			return jsessionId;
		}

		public void setJsessionId(String jsessionId) {
			this.jsessionId = jsessionId;
		}
	}
}
