package com.vc.url;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

public class UrlParamList implements Iterable<UrlParam> {
	private static final String PARAM_DELIM = "&";

	private final Map<String, UrlParam> params = new HashMap<>();

	public void addAll(Map<String, String> params) {
		params.forEach(this::add);
	}

	public void add(String param, Object... values) throws UnsupportedEncodingException {
		add(new UrlParam(param, values));
	}

	public void add(String param, String... values) {
		add(new UrlParam(param, values));
	}

	public void add(UrlParam param) {
		params.put(param.getName(), param);
	}

	public String[] get(String name) {
		UrlParam param = params.get(name);
		return param != null ? param.getValues() : null;
	}

	public String[] get(UrlParam.Name name) {
		return get(name.toString());
	}

	public boolean isEmpty() {
		return params.isEmpty();
	}

	@Override
	public Iterator<UrlParam> iterator() {
		return params.values().iterator();
	}

	@Override
	public void forEach(Consumer<? super UrlParam> action) {
		params.values().forEach(action);
	}

	@Override
	public Spliterator<UrlParam> spliterator() {
		return params.values().spliterator();
	}

	@Override
	public String toString() {
		return String.join(PARAM_DELIM, params.values());
	}
}
