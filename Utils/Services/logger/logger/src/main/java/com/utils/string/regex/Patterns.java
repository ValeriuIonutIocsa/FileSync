package com.utils.string.regex;

import java.io.Serializable;

import org.w3c.dom.Element;

import com.utils.string.StrUtils;

public class Patterns implements Serializable {

	private static final long serialVersionUID = -3158472204665180250L;

	private PatternWithCase exclusionPattern;
	private PatternWithCase inclusionPattern;

	public Patterns(
			final PatternWithCase exclusionPattern,
			final PatternWithCase inclusionPattern) {

		this.exclusionPattern = exclusionPattern;
		this.inclusionPattern = inclusionPattern;
	}

	public boolean checkMatches(
			final String str) {

		boolean matches = false;
		final boolean excluded = RegexUtils.matchesPattern(str, exclusionPattern);
		if (!excluded) {
			matches = RegexUtils.matchesPattern(str, inclusionPattern);
		}
		return matches;
	}

	public void writeToXml(
			final Element element) {

		if (exclusionPattern != null) {
			exclusionPattern.writeToXml(element, "ExclusionPattern");
		}
		if (inclusionPattern != null) {
			inclusionPattern.writeToXml(element, "InclusionPattern");
		}
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public String createDisplayString() {

		final StringBuilder sbDisplayString = new StringBuilder();
		sbDisplayString.append(inclusionPattern.getPatternString());
		if (exclusionPattern != null) {

			sbDisplayString.append(", exclusion: ");
			sbDisplayString.append(exclusionPattern.getPatternString());
		}
		return sbDisplayString.toString();
	}

	public void setExclusionPattern(
			final PatternWithCase exclusionPattern) {
		this.exclusionPattern = exclusionPattern;
	}

	public PatternWithCase getExclusionPattern() {
		return exclusionPattern;
	}

	public void setInclusionPattern(
			final PatternWithCase inclusionPattern) {
		this.inclusionPattern = inclusionPattern;
	}

	public PatternWithCase getInclusionPattern() {
		return inclusionPattern;
	}
}
