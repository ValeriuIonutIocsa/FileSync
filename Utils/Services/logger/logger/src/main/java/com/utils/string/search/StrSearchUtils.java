package com.utils.string.search;

public final class StrSearchUtils {

	private StrSearchUtils() {
	}

	public static int countOccurrencesOfSubstringInString(
			final String string,
			final String substring) {

		int count = 0;
		final int substringLength = substring.length();
		int index = 0;
		while ((index = string.indexOf(substring, index)) != -1) {

			index += substringLength;
			count++;
		}
		return count;
	}
}
