package com.mapbar.sso.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = (String) request.getSession().getAttribute("token");
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("token");
		response.sendRedirect(loggotURL + "?clientURL=" + URLEncoder.encode("http://" + request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath() + "/index.jsp", "UTF-8")
				+ "&token=" + token);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		loggotURL = config.getInitParameter("loggotURL");
	}

	private String loggotURL;
}
