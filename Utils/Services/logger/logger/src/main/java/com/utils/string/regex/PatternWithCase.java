package com.utils.string.regex;

import java.util.regex.Pattern;

import org.w3c.dom.Element;

import com.utils.string.StrUtils;

public class PatternWithCase {

	private final Pattern pattern;

	PatternWithCase(
			final Pattern pattern) {

		this.pattern = pattern;
	}

	public boolean checkMatches(
			final String string) {
		return string != null && pattern.matcher(string).matches();
	}

	public void writeToXml(
			final Element element,
			final String attributeName) {

		final String patternString = pattern.toString();
		element.setAttribute(attributeName, patternString);
		final boolean exclusionPatternCaseSensitive = isCaseSensitive();
		element.setAttribute(attributeName + "CaseSensitive", String.valueOf(exclusionPatternCaseSensitive));
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public Pattern getPattern() {
		return pattern;
	}

	public String getPatternString() {
		return pattern.toString();
	}

	public boolean isCaseSensitive() {
		return RegexUtils.checkCaseSensitive(pattern);
	}
}
