package com.vc.bootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.reflections.Reflections;

import com.vc.log.Log;
import com.vc.servlet.Servlet;

public class ServletRegistry {
	private static final Class<Servlet> SERVLET_CLASS = Servlet.class;
	private static final String SERVLET_PACKAGE = "com.vc.servlet";
	private static ServletRegistry SINGLETON;

	private final Context context;
	private final Map<Class<? extends Servlet>, Servlet> loadedServlets;

	private ServletRegistry(Context context) {
		this.context = context;
		this.loadedServlets = new HashMap<>();
	}

	private void load() {
		int alreadyLoaded = loadedServlets.size();
		Log.info("Loading available servlets (%d already loaded)", alreadyLoaded);

		long start = System.currentTimeMillis();
		Set<Class<? extends Servlet>> classes = new Reflections(SERVLET_PACKAGE).getSubTypesOf(SERVLET_CLASS);
		classes.forEach(this::loadClass);
		long end = System.currentTimeMillis();

		Log.info("Loaded %d servlets in %.2f seconds", loadedServlets.size() - alreadyLoaded, (end - start) / 1000.0);
	}

	private void loadClass(Class<? extends Servlet> c) {
		if (loadedServlets.containsKey(c)) {
			Log.info("Servlet class already loaded: %s", c.getSimpleName());
			return;
		}

		try {
			Servlet servlet = c.newInstance();
			Tomcat.addServlet(context, servlet.getServletName(), servlet);
			context.addServletMappingDecoded(servlet.getUrlPattern(), servlet.getServletName());
			loadedServlets.put(c, servlet);

			Log.info("SUCCEEDED: %s (%s)", servlet.getServletName(), servlet.getUrlPattern());
		} catch (Exception e) {
			Log.warn("FAILED: %s (%s)", c.getSimpleName(), e.toString());
		}
	}

	public static synchronized void load(Context context) {
		SINGLETON = new ServletRegistry(context);
		SINGLETON.load();
	}

	public static synchronized void reload() {
		SINGLETON.load();
	}

	public static synchronized String getServletUrl(Class<? extends Servlet> servletClass) throws Exception {
		return getServlet(servletClass).getUrlPattern();
	}

	public static synchronized boolean servletAllowsRedirectFrom(Class<? extends Servlet> fromServlet, Class<? extends Servlet> toServlet) throws Exception {
		return getServlet(toServlet).allowsRedirectFrom(fromServlet);
	}

	private static synchronized Servlet getServlet(Class<? extends Servlet> servletClass) throws Exception {
		if (!SINGLETON.loadedServlets.containsKey(servletClass))
			throw new ServletNotLoadedException(servletClass);

		return SINGLETON.loadedServlets.get(servletClass);
	}
}