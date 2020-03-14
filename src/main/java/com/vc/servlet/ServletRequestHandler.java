package com.vc.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ServletRequestHandler {
	void handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
