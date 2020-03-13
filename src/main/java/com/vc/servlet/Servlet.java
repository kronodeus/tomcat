package com.vc.servlet;

import java.io.IOException;

import com.vc.transaction.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class Servlet extends HttpServlet {
	@Override
	protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
			Transaction.run(getTransactionType(), () -> get(request, response));
		} catch (Exception e) {
			response.reset();
			throw new ServletException(e);
		}
	}

	protected abstract void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	@Override
	public String getServletName() {
		return this.getClass().getSimpleName();
	}

	public Transaction.Type getTransactionType() {
		return Transaction.Type.SERVLET;
	}

	public abstract String getUrlPattern();
}
