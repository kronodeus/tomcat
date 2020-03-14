package com.vc.servlet;

import java.io.IOException;
import java.util.Optional;

import com.vc.bootstrap.ServletRegistry;
import com.vc.log.Log;
import com.vc.url.Url;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServletRedirector {
	public static void redirect(HttpServletRequest request, HttpServletResponse response, Class<? extends Servlet> fromServlet, Class<? extends Servlet> toServlet) throws Exception {
		if (!ServletRegistry.servletAllowsRedirectFrom(fromServlet, toServlet))
			throw new ServletRedirectException(fromServlet, toServlet);

		Url toUrl = new Url.Builder(ServletRegistry.getServletUrl(toServlet))
				.fromSourceUrl(Url.parse(request))
				.build();

		doRedirect(response, toUrl.toString());
	}

	public static void reverseRedirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Url url = Url.parse(request);
		Optional<String> sourceUrl = url.getSourceUrl();

		if (!sourceUrl.isPresent())
			throw new Exception(String.format("Cannot perform reverse redirect, no source URL found: %s", url));

		doRedirect(response, sourceUrl.get());
	}

	private static void doRedirect(HttpServletResponse response, String url) throws IOException {
		Log.info("Redirecting to: %s", url);
		response.sendRedirect(url);
	}
}
