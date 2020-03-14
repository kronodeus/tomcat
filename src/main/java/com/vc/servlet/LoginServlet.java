package com.vc.servlet;

import com.vc.session.Session;
import com.vc.transaction.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends Servlet {
	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.getWriter().printf("Logging you in with new session: %s", Session.getNew(request).getId());
		ServletRedirector.reverseRedirect(request, response);
	}

	@Override
	protected Transaction.Type getTransactionType(Method method) {
		return Transaction.Type.LOGIN;
	}

	@Override
	protected boolean requiresActiveSession() {
		return false;
	}

	@Override
	public boolean allowsRedirectFrom(Class<? extends Servlet> servletClass) {
		return true;
	}

	@Override
	public String getUrlPattern() {
		return "/login";
	}
}
