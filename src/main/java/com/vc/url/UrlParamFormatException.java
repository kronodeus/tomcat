package com.vc.url;

public class UrlParamFormatException extends Exception {
	public UrlParamFormatException(String param) {
		super(String.format("Unable to parse URL param: %s", param));
	}
}