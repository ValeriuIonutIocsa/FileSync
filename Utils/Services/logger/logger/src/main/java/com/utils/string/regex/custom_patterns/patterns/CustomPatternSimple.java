package com.utils.string.regex.custom_patterns.patterns;

import org.apache.commons.lang3.StringUtils;

public class CustomPatternSimple extends CustomPattern {

	public CustomPatternSimple(
			final String pattern,
			final boolean negate,
			final boolean caseSensitive) {
		super(pattern, negate, caseSensitive);
	}

	@Override
	public boolean checkMatchesSpecific(
			final String string) {

		final boolean result;
		if (caseSensitive) {
			result = StringUtils.contains(string, pattern);
		} else {
			result = StringUtils.containsIgnoreCase(string, pattern);
		}
		return result;
	}

	@Override
	void appendRegexPattern(
			final StringBuilder sbRegexPatternString) {

		sbRegexPatternString
				.append(".*")
				.append(pattern)
				.append(".*");
	}
}
