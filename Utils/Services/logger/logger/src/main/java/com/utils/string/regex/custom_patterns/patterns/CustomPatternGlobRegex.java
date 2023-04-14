package com.utils.string.regex.custom_patterns.patterns;

import com.utils.string.regex.RegexUtils;

public class CustomPatternGlobRegex extends CustomPatternUnixRegex {

	public CustomPatternGlobRegex(
			final String pattern,
			final boolean negate,
			final boolean caseSensitive) {
		super(pattern, negate, caseSensitive);
	}

	@Override
	public boolean checkMatchesSpecific(
			final String string) {

		final StringBuilder sbRegexPatternString = new StringBuilder();
		appendRegexPattern(sbRegexPatternString);
		final String regexPatternString = sbRegexPatternString.toString();
		return RegexUtils.matchesPattern(string, regexPatternString, caseSensitive);
	}

	@Override
	void appendRegexPattern(
			final StringBuilder sbRegexPatternString) {

		boolean escaping = false;
		int insideCurlyBrackets = 0;
		for (final char currentChar : pattern.toCharArray()) {

			if (currentChar == '*') {
				if (escaping) {
					sbRegexPatternString.append("\\*");
				} else {
					sbRegexPatternString.append(".*");
				}
				escaping = false;

			} else if (currentChar == '?') {
				if (escaping) {
					sbRegexPatternString.append("\\?");
				} else {
					sbRegexPatternString.append('.');
				}
				escaping = false;

			} else if (currentChar == '.' || currentChar == '(' || currentChar == ')' ||
					currentChar == '+' || currentChar == '|' || currentChar == '^' || currentChar == '$' ||
					currentChar == '@' || currentChar == '%') {
				sbRegexPatternString.append('\\');
				sbRegexPatternString.append(currentChar);
				escaping = false;

			} else if (currentChar == '\\') {
				if (escaping) {
					sbRegexPatternString.append("\\\\");
					escaping = false;
				} else {
					escaping = true;
				}

			} else if (currentChar == '{') {
				if (escaping) {
					sbRegexPatternString.append("\\{");
				} else {
					sbRegexPatternString.append('(');
					insideCurlyBrackets++;
				}
				escaping = false;

			} else if (currentChar == '}') {
				if (insideCurlyBrackets > 0 && !escaping) {
					sbRegexPatternString.append(')');
					insideCurlyBrackets--;
				} else if (escaping) {
					sbRegexPatternString.append("\\}");
				} else {
					sbRegexPatternString.append('}');
				}
				escaping = false;

			} else if (currentChar == ',') {
				if (insideCurlyBrackets > 0 && !escaping) {
					sbRegexPatternString.append('|');
				} else if (escaping) {
					sbRegexPatternString.append("\\,");
				} else {
					sbRegexPatternString.append(',');
				}

			} else {
				escaping = false;
				sbRegexPatternString.append(currentChar);
			}
		}
	}
}
