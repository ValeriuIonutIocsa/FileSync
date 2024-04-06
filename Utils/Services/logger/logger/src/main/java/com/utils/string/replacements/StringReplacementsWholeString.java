package com.utils.string.replacements;

import java.util.LinkedHashMap;
import java.util.Map;

import com.utils.string.StrUtils;

public class StringReplacementsWholeString implements StringReplacements {

	private final boolean trimmedString;

	private final Map<String, String> replacementsMap;

	public StringReplacementsWholeString(
			final boolean trimmedString) {

		this.trimmedString = trimmedString;

		replacementsMap = new LinkedHashMap<>();
	}

	@Override
	public void addReplacement(
			final String searchString,
			final String replacementString) {

		replacementsMap.put(searchString, replacementString);
	}

	@Override
	public String performReplacements(
			final String str) {

		String resultStr = str;
		if (trimmedString) {

			final String whitespacePrefix = computeWhiteSpacePrefix(str);
			final String whitespaceSuffix = computeWhitespaceSuffix(str);
			final String trimmedStr = str.substring(
					whitespacePrefix.length(), str.length() - whitespaceSuffix.length());
			final String replacementStr = replacementsMap.get(trimmedStr);
			if (replacementStr != null) {
				resultStr = whitespacePrefix + replacementStr + whitespaceSuffix;
			}

		} else {
			final String replacementStr = replacementsMap.get(str);
			if (replacementStr != null) {
				resultStr = replacementStr;
			}
		}
		return resultStr;
	}

	private static String computeWhiteSpacePrefix(
			final String str) {

		final StringBuilder sbWhitespacePrefix = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {

			final char ch = str.charAt(i);
			if (Character.isWhitespace(ch)) {
				sbWhitespacePrefix.append(ch);
			} else {
				break;
			}
		}
		return sbWhitespacePrefix.toString();
	}

	private static String computeWhitespaceSuffix(
			final String str) {

		final StringBuilder sbWhitespaceSuffix = new StringBuilder();
		for (int i = str.length() - 1; i >= 0; i--) {

			final char ch = str.charAt(i);
			if (Character.isWhitespace(ch)) {
				sbWhitespaceSuffix.append(ch);
			} else {
				break;
			}
		}
		return sbWhitespaceSuffix.toString();
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
