package com.mapbar.sso.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mapbar.sso.bean.ClientMap;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		String clientURL = request.getParameter("clientURL");
		String clientJSessionId = request.getParameter("clientJSessionId");
		String clientLoggotURL = request.getParameter("clientLoggotURL");
		if (cookies != null && cookies.length != 0)
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					String token = cookie.getValue();
					if (!Token.TOKEN_MAP.containsKey(token)) {
						break;
					}
					Token.TOKEN_MAP.get(token).add(clientLoggotURL, clientJSessionId);
					clientURL = URLDecoder.decode(clientURL, "utf-8");
					response.sendRedirect(clientURL + (clientURL.indexOf("?") == -1 ? "?token=" : "&token=") + cookie.getValue());
					return;
				}
			}
		request.setAttribute("clientURL", clientURL);
		request.setAttribute("clientJSessionId", clientJSessionId);
		request.setAttribute("clientLoggotURL", clientLoggotURL);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clientURL = request.getParameter("clientURL");
		String login = request.getParameter("login");
		String jsessionId = request.getParameter("clientJSessionId");
		String clientLoggotURL = request.getParameter("clientLoggotURL");
		String token = UUID.randomUUID().toString();

		Token.TOKEN_MAP.put(token, new ClientMap(login, clientLoggotURL, jsessionId));
		request.setAttribute("token", token);
		request.setAttribute("clientURL", clientURL);

		clientURL = URLDecoder.decode(clientURL, "utf-8");
		clientURL += (clientURL.indexOf("?") == -1 ? "?token=" : "&token=") + token;

		response.addCookie(new Cookie("token", token));

		response.sendRedirect(clientURL);
	}
}
