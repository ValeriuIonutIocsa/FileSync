package com.utils.string.regex.custom_patterns;

import java.util.ArrayList;
import java.util.List;

import com.utils.string.StrUtils;
import com.utils.string.regex.custom_patterns.patterns.CustomPattern;

public class CustomPatterns {

	private final List<List<CustomPattern>> andPatternListList;

	public CustomPatterns(
			final List<List<CustomPattern>> andPatternListList) {

		this.andPatternListList = andPatternListList;
	}

	public boolean checkEmptyPatterns() {

		int count = 0;
		for (final List<CustomPattern> andPatterns : andPatternListList) {
			count += andPatterns.size();
		}
		return count == 0;
	}

	public boolean checkMatchesPatterns(
			final String string) {

		boolean matchesPatterns = false;
		for (final List<CustomPattern> andPatternList : andPatternListList) {

			if (checkMatchesAndPatterns(string, andPatternList)) {

				matchesPatterns = true;
				break;
			}
		}
		return matchesPatterns;
	}

	private static boolean checkMatchesAndPatterns(
			final String string,
			final List<CustomPattern> andPatternList) {

		boolean matchesAndPatterns = true;
		for (final CustomPattern andPattern : andPatternList) {

			final boolean matchesAndPattern = andPattern.checkMatches(string);
			if (!matchesAndPattern) {

				matchesAndPatterns = false;
				break;
			}
		}
		return matchesAndPatterns;
	}

	public String createPatternString() {

		final StringBuilder sbPatternString = new StringBuilder();
		sbPatternString.append('^');
		final List<String> andPatternStringList = new ArrayList<>();
		for (final List<CustomPattern> andPatternList : andPatternListList) {

			final StringBuilder sbAndPatternString = new StringBuilder();
			for (final CustomPattern customPattern : andPatternList) {
				customPattern.appendPatternString(sbAndPatternString);
			}
			if (sbAndPatternString.length() > 0) {

				sbAndPatternString.append(".*");
				final String andPatternString = sbAndPatternString.toString();
				andPatternStringList.add(andPatternString);
			}
		}
		if (andPatternStringList.size() > 1) {
			sbPatternString.append('(');
		}
		for (int i = 0; i < andPatternStringList.size(); i++) {

			final String andPatternString = andPatternStringList.get(i);
			sbPatternString.append(andPatternString);
			if (i < andPatternStringList.size() - 1) {
				sbPatternString.append('|');
			}
		}
		if (andPatternStringList.size() > 1) {
			sbPatternString.append(')');
		}
		sbPatternString.append('$');
		return sbPatternString.toString();
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
