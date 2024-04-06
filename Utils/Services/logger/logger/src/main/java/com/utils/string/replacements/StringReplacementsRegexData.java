package com.utils.string.replacements;

import java.util.regex.Pattern;

import com.utils.string.StrUtils;

class StringReplacementsRegexData {

	private final Pattern searchPattern;
	private final String replacementString;

	StringReplacementsRegexData(
			final Pattern searchPattern,
			final String replacementString) {

		this.searchPattern = searchPattern;
		this.replacementString = replacementString;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	Pattern getSearchPattern() {
		return searchPattern;
	}

	String getReplacementString() {
		return replacementString;
	}
}
