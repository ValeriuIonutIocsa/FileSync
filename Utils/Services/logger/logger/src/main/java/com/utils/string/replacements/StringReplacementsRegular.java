package com.utils.string.replacements;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.utils.string.StrUtils;

public class StringReplacementsRegular implements StringReplacements {

	private final List<String> searchList;
	private final List<String> replacementList;

	public StringReplacementsRegular() {

		searchList = new ArrayList<>();
		replacementList = new ArrayList<>();
	}

	@Override
	public void addReplacement(
			final String searchString,
			final String replacementString) {

		searchList.add(searchString);
		replacementList.add(replacementString);
	}

	@Override
	public String performReplacements(
			final String strParam) {

		String str = strParam;
		final int replacementCount = Math.min(searchList.size(), replacementList.size());
		for (int i = 0; i < replacementCount; i++) {

			final String searchString = searchList.get(i);
			final String replacementString = replacementList.get(i);
			str = StringUtils.replace(str, searchString, replacementString);
		}
		return str;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
