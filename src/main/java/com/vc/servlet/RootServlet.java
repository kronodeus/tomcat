package com.vc.servlet;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RootServlet extends Servlet {
	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//response.getWriter().write(String.format("Welcome! Active transaction: %s", Transaction.get()));
		response.sendRedirect("home.html");
	}

	@Override
	public String getUrlPattern() {
		return "";
	}
}