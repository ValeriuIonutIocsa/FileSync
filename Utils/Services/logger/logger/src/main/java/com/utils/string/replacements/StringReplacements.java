package com.utils.string.replacements;

public interface StringReplacements {

	void addReplacement(
			String searchString,
			String replacementString);

	String performReplacements(
			String str);
}
