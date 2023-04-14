package com.utils.string.regex;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;

public final class RegexUtils {

	public static final Pattern NEW_LINE_PATTERN = Pattern.compile("\\R");
	public static final Pattern WHITE_SPACE_PATTERN = Pattern.compile("\\s+");
	public static final Pattern NOT_WHITE_SPACE_PATTERN = Pattern.compile("\\S+");
	public static final Pattern WORD_PATTERN = Pattern.compile("\\w+");
	public static final Pattern NOT_WORD_PATTERN = Pattern.compile("\\W+");

	private RegexUtils() {
	}

	@ApiMethod
	public static boolean matchesPattern(
			final String string,
			final String patternString,
			final boolean caseSensitive) {

		final Pattern pattern = tryCompile(patternString, caseSensitive);
		return matchesPattern(string, pattern);
	}

	@ApiMethod
	public static Pattern tryCompile(
			final String patternString,
			final boolean caseSensitive) {

		Pattern pattern = null;
		try {
			if (patternString != null) {

				if (caseSensitive) {
					pattern = Pattern.compile(patternString);
				} else {
					pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
				}
			}

		} catch (final Exception ignored) {
			if (StringUtils.isNotBlank(patternString)) {
				Logger.printWarning("failed to compile REGEX pattern \"" + patternString + "\"");
			}
		}
		return pattern;
	}

	@ApiMethod
	public static boolean matchesPattern(
			final String string,
			final Pattern pattern) {
		return string != null && pattern != null && pattern.matcher(string).matches();
	}

	@ApiMethod
	public static boolean matchesPattern(
			final String string,
			final PatternWithCase patternWithCase) {
		return patternWithCase != null && patternWithCase.checkMatches(string);
	}

	@ApiMethod
	public static boolean checkCaseSensitive(
			final Pattern pattern) {

		boolean caseSensitive = false;
		if (pattern != null) {
			final int flags = pattern.flags();
			caseSensitive = (flags & Pattern.CASE_INSENSITIVE) == 0;
		}
		return caseSensitive;
	}
}
