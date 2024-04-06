package com.utils.test;

public final class TestInputUtils {

	private TestInputUtils() {
	}

	public static boolean parseTestInputBoolean(
			final String booleanString) {

		return Boolean.parseBoolean(booleanString);
	}

	public static int parseTestInputNumber(
			final String intString) {

		int value = -1;
		try {
			value = Integer.parseInt(intString);

		} catch (final Exception ignored) {
		}
		return value;
	}
}
