package net;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class DialogAuthenticator extends Authenticator {
	private static final DialogAuthenticator instance = new DialogAuthenticator();

	private DialogAuthenticator() {
		super();
	}

	public static DialogAuthenticator getInstance() {
		return instance;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		String site = this.getRequestingSite().getHostAddress();
		if (site.trim().indexOf("127.0.0.1") != -1) {
			return new PasswordAuthentication("admin", "admin".toCharArray());
		} else {
			return null;
		}
	}
}
