package com.utils.string.regex.custom_patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.utils.string.regex.custom_patterns.patterns.CustomPattern;
import com.utils.string.regex.custom_patterns.patterns.CustomPatternUnixRegex;

class CustomPatternsTest {

	@TestFactory
	List<DynamicTest> testCheckMatchesPatterns() {

		final List<DynamicTest> dynamicTestList = new ArrayList<>();
		final List<Integer> testCaseList = Arrays.asList(0, 1, 2);
		if (testCaseList.contains(1)) {
			dynamicTestList.add(DynamicTest.dynamicTest("1", () -> {

				final List<List<CustomPattern>> andPatternListList = new ArrayList<>();
				final List<CustomPattern> andPatternList = new ArrayList<>();
				andPatternList.add(new CustomPatternUnixRegex(".*@vitesco.com", false, true));
				andPatternList.add(new CustomPatternUnixRegex(".*email.*", true, true));
				andPatternListList.add(andPatternList);
				testCheckMatchesPatternsCommon(andPatternListList, "abcd@vitesco.com", true);
			}));
		}
		if (testCaseList.contains(2)) {
			dynamicTestList.add(DynamicTest.dynamicTest("2", () -> {

				final List<List<CustomPattern>> andPatternListList = new ArrayList<>();
				final List<CustomPattern> andPatternList = new ArrayList<>();
				andPatternList.add(new CustomPatternUnixRegex(".*@vitesco.com", false, true));
				andPatternList.add(new CustomPatternUnixRegex(".*email.*", true, true));
				andPatternListList.add(andPatternList);
				testCheckMatchesPatternsCommon(andPatternListList, "abcdemailbcd@vitesco.com", false);
			}));
		}
		return dynamicTestList;
	}

	private static void testCheckMatchesPatternsCommon(
			final List<List<CustomPattern>> andPatternListList,
			final String string,
			final boolean expectedMatchesPatterns) {

		final CustomPatterns customPatterns = new CustomPatterns(andPatternListList);
		final boolean matchesPatterns = customPatterns.checkMatchesPatterns(string);
		Assertions.assertEquals(expectedMatchesPatterns, matchesPatterns);
	}
}
