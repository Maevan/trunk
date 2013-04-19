package com.mapbar.sso.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class Validator
 */
public class Validator implements Filter {

	/**
	 * Default constructor.
	 */
	public Validator() {
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

		if (!valid(request)) {
			String clientURL = URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
			String clientJSessionId = request.getSession().getId();
			String clientLoggotURL = URLEncoder.encode("http://" + request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath() + this.clientLoggotURL, "UTF-8");
			response.sendRedirect(ssoURL + "?clientURL=" + clientURL + "&clientJSessionId=" + clientJSessionId + "&clientLoggotURL=" + clientLoggotURL);
			return;
		}
		chain.doFilter(request, response);
	}

	private boolean valid(HttpServletRequest request) throws IOException {
		if (request.getSession().getAttribute("user") != null) {
			return true;
		}
		String token = request.getParameter("token");
		boolean isLogin = false;
		
		if (token == null || token.trim().length() == 0) {
			return isLogin;
		}

		String content = getUserByToken(token);

		if (content == null) {
			return false;
		}

		String[] contents = getUserByToken(token).toString().split(";");
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
		return isLogin;
	}

	public String getUserByToken(String token) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new URL(tokenURL + "?token=" + token).openConnection().getInputStream()));
			StringBuilder content = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				content.append(line);
			}
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		this.tokenURL = config.getInitParameter("tokenURL");
		this.ssoURL = config.getInitParameter("ssoURL");
		this.clientLoggotURL = config.getInitParameter("clientLoggotURL");
	}

	private String tokenURL;

	private String ssoURL;

	private String clientLoggotURL;
}
