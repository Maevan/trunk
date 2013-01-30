package ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

public class LDAPExample {
	public static void main(String[] args) throws NamingException {
		InitialLdapContext context = new InitialLdapContext(getEnv("zhaojp", "111qqq,,,"), null);
		NamingEnumeration<SearchResult> results = context.search("mapbar.com", "objectClass=User", getControls());
		while (results.hasMore()) {
			SearchResult result = results.nextElement();
			System.err.println(result.getName());
		}
	}

	public static SearchControls getControls() {
		SearchControls controls = new SearchControls();
		controls.setReturningAttributes(new String[] { "url", "name" });

		return controls;
	}

	public static Hashtable<String, Object> getEnv(String name, String pwd) {
		Hashtable<String, Object> env = new Hashtable<String, Object>();

		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, name + "@mapbar.com");
		env.put(Context.SECURITY_CREDENTIALS, pwd);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://" + HOST_NAME);
		return env;
	}

	private static final String HOST_NAME = "mapbar.com";
}
