package com.vc.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RootServlet extends Servlet {
	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("html/home.html").forward(request, response);
	}

	@Override
	public String getUrlPattern() {
		return "";
	}
}