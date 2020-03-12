package com.vc.servlet;

import java.io.IOException;

import com.vc.transaction.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RootServlet extends Servlet {
	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().write(String.format("Welcome! Active transaction: %s", Transaction.get()));
	}

	@Override
	public String getUrlPattern() {
		return "";
	}
}