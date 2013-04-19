package com.mapbar.sso.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mapbar.sso.bean.ClientMap;
import com.mapbar.sso.bean.ClientMap.Client;

/**
 * Servlet implementation class Loggot
 */
public class Loggot extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Loggot() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		String clientURL = request.getParameter("clientURL");
		clientURL = URLDecoder.decode(clientURL, "UTF-8");
		ClientMap clientMap = Token.TOKEN_MAP.remove(token);
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length != 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		if (clientMap != null && clientMap.getClients() != null && !clientMap.getClients().isEmpty()) {
			loggot(clientMap.getClients());
		}
		response.sendRedirect(clientURL);
	}

	private void loggot(Collection<Client> clients) {
		for (Client client : clients) {
			BufferedReader reader = null;
			try {
				System.err.println(client.getLogoutURL() + ";jsessionid=" + client.getJsessionId());
				reader = new BufferedReader(new InputStreamReader(new URL(client.getLogoutURL() + ";jsessionid=" + client.getJsessionId()).openConnection().getInputStream()));
				while (reader.readLine() != null)
					;

			} catch (IOException e) {
				e.printStackTrace();
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
