package com.vc.servlet;

import com.vc.session.Session;
import com.vc.transaction.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class Servlet extends HttpServlet {
	public enum Method {
		GET,
		POST
	}

	@Override
	protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		handleRequest(request, response, this::get, Method.GET);
	}

	@Override
	protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		handleRequest(request, response, this::post, Method.POST);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response, ServletRequestHandler handler, Method method) throws ServletException {
		try {
			if (requiresActiveSession() && !Session.get(request).isActive()) {
				ServletRedirector.redirect(request, response, getClass(), LoginServlet.class);
				return;
			}

			Transaction.run(
					Session.get(request),
					getTransactionType(method),
					request.getRequestURI(),
					() -> handler.handle(request, response)
			);
		} catch (Exception e) {
			response.reset();
			throw new ServletException(e);
		}
	}

	protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		throw new UnsupportedRequestException(getClass(), Method.POST);
	}

	protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
		throw new UnsupportedRequestException(getClass(), Method.POST);
	}

	protected Transaction.Type getTransactionType(Method method) {
		if (method == Method.POST)
			return Transaction.Type.SERVLET_POST;

		return Transaction.Type.SERVLET_GET;
	}

	protected boolean requiresActiveSession() {
		return true;
	}

	public boolean allowsRedirectFrom(Class<? extends Servlet> servletClass) {
		return false;
	}

	@Override
	public String getServletName() {
		return this.getClass().getSimpleName();
	}

	public abstract String getUrlPattern();

	private static class UnsupportedRequestException extends ServletException {
		public UnsupportedRequestException(Class<? extends Servlet> servletClass, Method method) {
			super(String.format("%s does not support %s requests", servletClass.getSimpleName(), method));
		}
	}
}
