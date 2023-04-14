package com.utils.string.regex;

import org.w3c.dom.Element;

public final class FactoryPatterns {

	private FactoryPatterns() {
	}

	public static Patterns newInstance(
			final Element patternsElement) {

		Patterns patterns = null;
		final PatternWithCase inclusionPattern =
				FactoryPatternWithCase.newInstance(patternsElement, "InclusionPattern");
		if (inclusionPattern != null) {

			final PatternWithCase exclusionPattern =
					FactoryPatternWithCase.newInstance(patternsElement, "ExclusionPattern");
			patterns = new Patterns(exclusionPattern, inclusionPattern);
		}
		return patterns;
	}

	public static Patterns newInstance(
			final String inclusionPatternString,
			final String exclusionPatternString) {

		Patterns patterns = null;
		final PatternWithCase inclusionPattern =
				FactoryPatternWithCase.newInstance(inclusionPatternString, true);
		if (inclusionPattern != null) {

			final PatternWithCase exclusionPattern =
					FactoryPatternWithCase.newInstance(exclusionPatternString, true);
			patterns = new Patterns(exclusionPattern, inclusionPattern);
		}
		return patterns;
	}

	public static Patterns newInstance(
			final String inclusionPatternString) {

		Patterns patterns = null;
		final PatternWithCase inclusionPattern =
				FactoryPatternWithCase.newInstance(inclusionPatternString, false);
		if (inclusionPattern != null) {
			patterns = new Patterns(null, inclusionPattern);
		}
		return patterns;
	}
}
