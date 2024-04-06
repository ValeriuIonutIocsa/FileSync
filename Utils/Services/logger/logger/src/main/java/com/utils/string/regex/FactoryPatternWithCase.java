package com.utils.string.regex;

import java.util.regex.Pattern;

import org.w3c.dom.Element;

import com.utils.annotations.ApiMethod;

public final class FactoryPatternWithCase {

	private FactoryPatternWithCase() {
	}

	@ApiMethod
	public static PatternWithCase newInstance(
			final Pattern pattern) {

		PatternWithCase patternWithCase = null;
		if (pattern != null) {

			final String patternString = pattern.toString();
			final boolean caseSensitive = RegexUtils.checkCaseSensitive(pattern);
			patternWithCase = new PatternWithCase(patternString, caseSensitive, pattern);
		}
		return patternWithCase;
	}

	@ApiMethod
	public static PatternWithCase newInstance(
			final String patternString,
			final boolean caseSensitive) {

		PatternWithCase patternWithCase = null;
		final Pattern pattern = RegexUtils.tryCompile(patternString, caseSensitive);
		if (pattern != null) {
			patternWithCase = new PatternWithCase(patternString, caseSensitive, pattern);
		}
		return patternWithCase;
	}

	@ApiMethod
	public static PatternWithCase newInstance(
			final Element element,
			final String attributeName) {

		PatternWithCase patternWithCase = null;

		final String patternCaseSensitiveString =
				element.getAttribute(attributeName + "CaseSensitive");
		final boolean caseSensitive = Boolean.parseBoolean(patternCaseSensitiveString);

		final String patternString = element.getAttribute(attributeName);
		final Pattern pattern = RegexUtils.tryCompile(patternString, caseSensitive);
		if (pattern != null) {
			patternWithCase = new PatternWithCase(patternString, caseSensitive, pattern);
		}

		return patternWithCase;
	}

	@ApiMethod
	public static PatternWithCase newInstanceBlank() {

		final String patternString = "";
		final boolean caseSensitive = true;
		final Pattern pattern = Pattern.compile(patternString);
		return new PatternWithCase(patternString, caseSensitive, pattern);
	}
}
