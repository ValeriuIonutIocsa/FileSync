package com.utils.string.regex.custom_patterns.patterns;

import com.utils.string.StrUtils;

public abstract class CustomPattern {

	final String pattern;
	final boolean negate;
	final boolean caseSensitive;

	CustomPattern(
			final String pattern,
			final boolean negate,
			final boolean caseSensitive) {

		this.pattern = pattern;
		this.negate = negate;
		this.caseSensitive = caseSensitive;
	}

	public boolean checkMatches(
			final String string) {

		final boolean result;
		final boolean tmpResult = checkMatchesSpecific(string);
		if (negate) {
			result = !tmpResult;
		} else {
			result = tmpResult;
		}
		return result;
	}

	abstract boolean checkMatchesSpecific(
			String string);

	public void appendPatternString(
			final StringBuilder sbPatternString) {

		if (!negate) {
			sbPatternString.append("(?=");
		} else {
			sbPatternString.append("(?!");
		}
		if (caseSensitive) {
			sbPatternString.append("(?i)");
		}
		appendRegexPattern(sbPatternString);
		if (caseSensitive) {
			sbPatternString.append("(?-i)");
		}
		sbPatternString.append(')');
	}

	abstract void appendRegexPattern(
			StringBuilder sbRegexPatternString);

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
