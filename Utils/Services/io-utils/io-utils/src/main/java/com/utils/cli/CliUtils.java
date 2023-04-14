package com.utils.cli;

import java.util.Map;

public final class CliUtils {

	private CliUtils() {
	}

	public static void fillCliArgsByNameMap(
			final String[] args,
			final Map<String, String> cliArgsByNameMap) {

		for (final String arg : args) {

			if (arg.startsWith("--")) {
				final int indexOf = arg.indexOf('=');
				if (indexOf >= 3) {

					final String argName = arg.substring(2, indexOf);
					final String argValue = arg.substring(indexOf + 1);
					cliArgsByNameMap.put(argName, argValue);
				}
			}
		}
	}

	public static boolean parseBooleanArg(
			final Map<String, String> cliArgsByNameMap,
			final String argName) {

		final String valueString = cliArgsByNameMap.get(argName);
		return Boolean.parseBoolean(valueString);
	}
}
