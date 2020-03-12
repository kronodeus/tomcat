package com.vc.bootstrap;

import java.util.Set;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.reflections.Reflections;

import com.vc.servlet.Servlet;

public class ServletLoader {
	private static final Class<Servlet> SERVLET_CLASS = Servlet.class;
	private static final String SERVLET_PACKAGE = "com.vc.servlet";
	private static ServletLoader SINGLETON;

	private final Context context;

	private ServletLoader(Context context) {
		this.context = context;
	}

	private void load() {
		System.out.println("Loading servlets");

		long start = System.currentTimeMillis();
		Set<Class<? extends Servlet>> classes = new Reflections(SERVLET_PACKAGE).getSubTypesOf(SERVLET_CLASS);
		classes.forEach(this::loadClass);
		long end = System.currentTimeMillis();

		System.out.printf("Finished loading servlets in %.2f seconds\n", (end - start) / 1000.0);
	}

	private void loadClass(Class<? extends Servlet> c) {
		try {
			Servlet servlet = c.newInstance();
			Tomcat.addServlet(context, servlet.getServletName(), servlet);
			context.addServletMappingDecoded(servlet.getUrlPattern(), servlet.getServletName());

			System.out.printf("SUCCEEDED: %s\n", servlet.getServletName());
		} catch (Exception e) {
			System.out.printf("FAILED: %s (%s)\n", c.getSimpleName(), e.toString());
		}
	}

	public static void load(Context context) {
		SINGLETON = new ServletLoader(context);
		SINGLETON.load();
	}

	public static void reload() {
		SINGLETON.load();
	}
}