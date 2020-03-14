package com.vc.url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

public class Url {
	private static final String UTF8 = "UTF-8";

	private final String root;
	private final UrlParamList params;

	private Url(String root, UrlParamList params) {
		this.root = root.startsWith("/") ? root : "/" + root;
		this.params = params;
	}

	public String getRoot() {
		return root;
	}

	public Optional<String> getSourceUrl() {
		return Optional.ofNullable(Optional
				.ofNullable(params.get(UrlParam.Name.SOURCE_URL))
				.orElse(new String[]{null})[0]
		);
	}

	public String[] getParam(String name) {
		return params.get(name);
	}

	public String[] getParam(UrlParam.Name name) {
		return params.get(name);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(root);
		if (!params.isEmpty())

			sb.append("?").append(params);

		return sb.toString();
	}

	public static Url parse(HttpServletRequest request) {
		Builder urlBuilder = new Builder(request.getRequestURI());
		Map<String, String[]> params = request.getParameterMap();

		for (String param : params.keySet())
			urlBuilder.withParam(param, params.get(param));

		return urlBuilder.build();
	}

	public static String encode(String s) throws UnsupportedEncodingException {
		return URLEncoder.encode(s, UTF8);
	}

	public static String decode(String s) throws UnsupportedEncodingException {
		return URLDecoder.decode(s, UTF8);
	}

	public static class Builder {
		private final String root;
		private final UrlParamList params;

		public Builder() {
			this("/");
		}

		public Builder(String root) {
			this.root = Objects.requireNonNull(root);
			this.params = new UrlParamList();
		}

		public Builder fromSourceUrl(Url source) throws UnsupportedEncodingException {
			params.add(UrlParam.Name.SOURCE_URL.toString(), Url.encode(source.toString()));
			return this;
		}

		public Builder withParams(UrlParamList newParams) {
			newParams.forEach(params::add);
			return this;
		}

		public Builder withParam(String param, String... values) {
			params.add(param, values);
			return this;
		}

		public Url build() {
			return new Url(root, params);
		}
	}
}
