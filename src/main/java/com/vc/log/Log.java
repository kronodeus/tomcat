package com.vc.log;

import java.io.PrintStream;

public class Log {
	private static final String PREFIX_INFO = "[INFO] ";
	private static final String PREFIX_WARN = "[WARN] ";
	private static final String PREFIX_ERROR = "[ERROR] ";

	private Log() {
	}

	public static void info(String message, Object... args) {
		info(String.format(message, args));
	}

	public static void info(String message) {
		log(System.out, PREFIX_INFO, message);
	}

	public static void warn(String message, Object... args) {
		warn(String.format(message, args));
	}

	public static void warn(String message) {
		log(System.out, PREFIX_WARN, message);
	}

	public static void error(String message, Object... args) {
		error(String.format(message, args));
	}

	public static void error(String message) {
		log(System.err, PREFIX_ERROR, message);
	}

	private static void log(PrintStream outputStream, String prefix, String message) {
		outputStream.println(prefix + Thread.currentThread().getName() + " - " + message);
	}
}
