package com.utils.string.regex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegexUtilsTest {

	@Test
	void testMatchesPattern() {

		final String string;
		final String patternString;
		final boolean caseSensitive;
		final boolean expectedMatches;
		final int input = Integer.parseInt("11");
		if (input == 1) {

			string = "SOMEthing";
			patternString = "SO(?i)me(?-i)thing";
			caseSensitive = true;
			expectedMatches = true;

		} else if (input == 2) {

			string = "SOMEthing";
			patternString = "something";
			caseSensitive = false;
			expectedMatches = true;

		} else if (input == 3) {

			string = "SOMEthing";
			patternString = "something";
			caseSensitive = true;
			expectedMatches = false;

		} else if (input == 11) {

            string = "Os_task_task";
            patternString = "^(?!Os_).*";
            caseSensitive = true;
            expectedMatches = false;

        } else if (input == 12) {

            string = "abc_bcd";
            patternString = "^(?!Os_).*";
            caseSensitive = true;
            expectedMatches = true;

        } else {
			throw new RuntimeException();
		}

		final boolean matches = RegexUtils.matchesPattern(string, patternString, caseSensitive);
		Assertions.assertEquals(expectedMatches, matches);
	}
}
