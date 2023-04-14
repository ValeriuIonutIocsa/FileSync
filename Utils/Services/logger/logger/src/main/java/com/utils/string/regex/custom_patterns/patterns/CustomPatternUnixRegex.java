package com.utils.string.regex.custom_patterns.patterns;

import com.utils.string.regex.RegexUtils;

public class CustomPatternUnixRegex extends CustomPattern {

	public CustomPatternUnixRegex(
			final String pattern,
			final boolean negate,
			final boolean caseSensitive) {
		super(pattern, negate, caseSensitive);
	}

	@Override
	public boolean checkMatchesSpecific(
			final String string) {
		return RegexUtils.matchesPattern(string, pattern, caseSensitive);
	}

	@Override
	void appendRegexPattern(
			final StringBuilder sbRegexPatternString) {
		sbRegexPatternString.append(pattern);
	}
}
