package com.utils.string.replacements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.utils.string.StrUtils;

public class StringReplacementsRegex implements StringReplacements {

	private final List<Pattern> searchList;
	private final List<String> replacementList;

	public StringReplacementsRegex() {

		searchList = new ArrayList<>();
		replacementList = new ArrayList<>();
	}

	@Override
	public void addReplacement(
			final String searchString,
			final String replacementString) {

		try {
			final Pattern compile = Pattern.compile(searchString);
			searchList.add(compile);
			replacementList.add(replacementString);
		} catch (final Exception ignored) {
		}
	}

	@Override
	public String performReplacements(
			final String strParam) {

		String str = strParam;
		final int replacementCount = Math.min(searchList.size(), replacementList.size());
		for (int i = 0; i < replacementCount; i++) {

			final Pattern searchPattern = searchList.get(i);
			final String replacementString = replacementList.get(i);
			str = searchPattern.matcher(str).replaceAll(replacementString);
		}
		return str;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
