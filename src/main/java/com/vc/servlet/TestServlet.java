package com.vc.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestServlet extends Servlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		System.out.printf("RECEIVED! %s\n", req.getParameterMap());
	}

	@Override
	public String getUrlPattern() {
		return "/test";
	}
}