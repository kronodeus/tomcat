package com.vc.url;

import java.util.stream.IntStream;

public class UrlParam implements CharSequence {
	public enum Name {
		SOURCE_URL("source_url");

		final String name;

		Name(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private static final String ASSIGNMENT_OPERATOR = "=";

	private final String name;
	private final String[] values;

	public UrlParam(String name, Object... values) {
		this.name = name;
		this.values = getStringValues(values);
	}

	public UrlParam(String name, String... values) {
		this.name = name;
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public String[] getValues() {
		return values;
	}

	@Override
	public int length() {
		return toString().length();
	}

	@Override
	public char charAt(int index) {
		return toString().charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return toString().subSequence(start, end);
	}

	@Override
	public IntStream chars() {
		return toString().chars();
	}

	@Override
	public IntStream codePoints() {
		return toString().codePoints();
	}

	@Override
	public String toString() {
		return String.format("%s%s%s", name, ASSIGNMENT_OPERATOR, String.join(",", values));
	}

	private static String[] getStringValues(Object... values) {
		String[] stringValues = new String[values.length];
		for (int i = 0; i < values.length; i++)
			stringValues[i] = values[i] != null ? values[i].toString() : null;

		return stringValues;
	}
}
