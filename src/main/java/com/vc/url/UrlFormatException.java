package com.vc.url;

public class UrlFormatException extends Exception {
	public UrlFormatException(String url) {
		super(getMessage(url));
	}

	public UrlFormatException(String url, Exception cause) {
		super(getMessage(url), cause);
	}

	private static String getMessage(String url) {
		return String.format("Unable to parse URL: %s", url);
	}
}
