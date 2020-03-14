package com.vc.servlet;

import java.io.IOException;

import com.vc.transaction.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DefaultServlet extends Servlet {
	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	@Override
	protected Transaction.Type getTransactionType(Method method) {
		return Transaction.Type.DEFAULT;
	}

	@Override
	public String getUrlPattern() {
		return "/";
	}
}