package com.mapbar.sso.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mapbar.sso.bean.ClientMap;

/**
 * Servlet implementation class Token
 */
public class Token extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Token() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		PrintWriter out = new PrintWriter(response.getWriter());
		if (token != null && token.trim().length() != 0 && TOKEN_MAP.containsKey(token)) {
			ClientMap cm = TOKEN_MAP.get(token);
			out.write("state:0;name:" + cm.getName());
		} else {
			out.write("state:1");
		}
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected static final ConcurrentMap<String, ClientMap> TOKEN_MAP = new ConcurrentHashMap<String, ClientMap>();
}
