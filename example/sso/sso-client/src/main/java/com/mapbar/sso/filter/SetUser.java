package com.mapbar.sso.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class SetUser
 */
public class SetUser implements Filter {

	/**
	 * Default constructor.
	 */
	public SetUser() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (request.getSession().getAttribute("user") == null) {
			String token = request.getParameter("token");
			if (token != null && token.trim().length() != 0) {
				URLConnection connection = new URL(tokenURL + "?token=" + token).openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder content = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					System.err.println(line);
					content.append(line);
				}
				String[] contents = content.toString().split(";");
				boolean isLogin = false;
				String user = null;
				for (String c : contents) {
					if (c.startsWith("state:")) {
						if (c.endsWith(":0")) {
							isLogin = true;
						} else {
							isLogin = false;
						}
					} else if (c.startsWith("name:")) {
						user = c.split(":")[1];
					}
				}
				if (isLogin) {
					request.getSession().setAttribute("user", user);
					request.getSession().setAttribute("token", token);
				}
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		tokenURL = config.getInitParameter("tokenURL");
	}

	private String tokenURL;
}
