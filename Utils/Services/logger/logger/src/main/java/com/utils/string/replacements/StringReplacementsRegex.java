package com.utils.string.replacements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.utils.string.StrUtils;

public class StringReplacementsRegex implements StringReplacements {

	private final List<StringReplacementsRegexData> stringReplacementsRegexDataList;

	public StringReplacementsRegex() {

		stringReplacementsRegexDataList = new ArrayList<>();
	}

	@Override
	public void addReplacement(
			final String searchString,
			final String replacementString) {

		try {
			final Pattern searchPattern = Pattern.compile(searchString);
			final StringReplacementsRegexData stringReplacementsRegexData =
					new StringReplacementsRegexData(searchPattern, replacementString);
			stringReplacementsRegexDataList.add(stringReplacementsRegexData);

		} catch (final Exception ignored) {
		}
	}

	@Override
	public String performReplacements(
			final String str) {

		String resultStr = str;
		for (final StringReplacementsRegexData stringReplacementsRegexData : stringReplacementsRegexDataList) {

			final Pattern searchPattern = stringReplacementsRegexData.getSearchPattern();
			final String replacementString = stringReplacementsRegexData.getReplacementString();
			resultStr = searchPattern.matcher(resultStr).replaceAll(replacementString);
		}
		return resultStr;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
