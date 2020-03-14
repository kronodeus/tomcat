package com.vc.bootstrap;

import com.vc.servlet.Servlet;

public class ServletNotLoadedException extends Exception {
	public ServletNotLoadedException(Class<? extends Servlet> servletClass) {
		super(String.format("Servlet class not loaded: %s", servletClass.getSimpleName()));
	}
}
