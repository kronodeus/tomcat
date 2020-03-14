package com.vc.servlet;

public class ServletRedirectException extends Exception {
	public ServletRedirectException(Class<? extends Servlet> fromServlet, Class<? extends Servlet> toServlet) {
		super(String.format("Servlet redirection from %s to %s is not allowed", fromServlet.getSimpleName(), toServlet.getSimpleName()));
	}
}
