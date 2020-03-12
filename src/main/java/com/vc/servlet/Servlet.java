package com.vc.servlet;

import jakarta.servlet.http.HttpServlet;

public abstract class Servlet extends HttpServlet {
	@Override
	public String getServletName() {
		return this.getClass().getSimpleName();
	}

	public abstract String getUrlPattern();
}
