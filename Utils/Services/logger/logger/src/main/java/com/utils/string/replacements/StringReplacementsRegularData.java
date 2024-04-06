package com.utils.string.replacements;

import com.utils.string.StrUtils;

class StringReplacementsRegularData {

	private final String searchString;
	private final String replacementString;

	StringReplacementsRegularData(
			final String searchString,
			final String replacementString) {

		this.searchString = searchString;
		this.replacementString = replacementString;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	String getSearchString() {
		return searchString;
	}

	String getReplacementString() {
		return replacementString;
	}
}
