package com.utils.string.regex.custom_patterns.patterns;

public interface CustomPattern {

	boolean checkMatches(
			String string);

	void appendPatternString(
			StringBuilder sbPatternString);
}
