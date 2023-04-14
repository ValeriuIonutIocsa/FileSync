package com.utils.string;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.utils.string.characters.SpecialCharacterUtils;

class StrUtilsTest {

	@TestFactory
	List<DynamicTest> testUnsignedIntToPaddedBinaryString() {

		final List<DynamicTest> dynamicTestList = new ArrayList<>();
		final List<Integer> testCaseList = Arrays.asList(0, 1, 2, 3, 4);
		if (testCaseList.contains(1)) {
			dynamicTestList.add(DynamicTest.dynamicTest("1", () -> testUnsignedIntToPaddedBinaryStringCommon(
					0x20, 8, "00100000")));
		}
		if (testCaseList.contains(2)) {
			dynamicTestList.add(DynamicTest.dynamicTest("2", () -> testUnsignedIntToPaddedBinaryStringCommon(
					0xa0, 8, "10100000")));
		}
		if (testCaseList.contains(3)) {
			dynamicTestList.add(DynamicTest.dynamicTest("3", () -> testUnsignedIntToPaddedBinaryStringCommon(
					0b1100110011001100, 32, "00000000000000001100110011001100")));
		}
		if (testCaseList.contains(4)) {
			dynamicTestList.add(DynamicTest.dynamicTest("4", () -> testUnsignedIntToPaddedBinaryStringCommon(
					0b11001100110011001100110011001100, 32, "11001100110011001100110011001100")));
		}
		return dynamicTestList;
	}

	private static void testUnsignedIntToPaddedBinaryStringCommon(
			final int n,
			final int size,
			final String expectedStr) {

		final String str = StrUtils.unsignedIntToPaddedBinaryString(n, size);
		Assertions.assertEquals(expectedStr, str);
	}

	@TestFactory
	List<DynamicTest> testDoubleToString() {

		final List<DynamicTest> dynamicTestList = new ArrayList<>();
		final List<Integer> testCaseList = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
		if (testCaseList.contains(1)) {
			dynamicTestList.add(DynamicTest.dynamicTest("1 (no decimal digits)",
					() -> testDoubleToStringCommon(20.0, 0, 5, "20")));
		}
		if (testCaseList.contains(2)) {
			dynamicTestList.add(DynamicTest.dynamicTest("2 (one decimal digit zero)",
					() -> testDoubleToStringCommon(20.0, 1, 5, "20.0")));
		}
		if (testCaseList.contains(3)) {
			dynamicTestList.add(DynamicTest.dynamicTest("3 (one decimal digit not zero)",
					() -> testDoubleToStringCommon(12.1, 0, 5, "12.1")));
		}
		if (testCaseList.contains(4)) {
			dynamicTestList.add(DynamicTest.dynamicTest("4 (three decimal digits)",
					() -> testDoubleToStringCommon(1.324, 0, 5, "1.324")));
		}
		if (testCaseList.contains(5)) {
			dynamicTestList.add(DynamicTest.dynamicTest("5 (seven decimal digits)",
					() -> testDoubleToStringCommon(127.753_216_500, 0, 10, "127.7532165")));
		}
		if (testCaseList.contains(6)) {
			dynamicTestList.add(DynamicTest.dynamicTest("6 (zero decimal digits)",
					() -> testDoubleToStringCommon(251.537, 0, 0, "252")));
		}
		return dynamicTestList;
	}

	private static void testDoubleToStringCommon(
			final double d,
			final int mandatoryDigitCount,
			final int optionalDigitCount,
			final String expectedDoubleString) {

		final String doubleString = StrUtils.doubleToString(d, mandatoryDigitCount, optionalDigitCount, false);
		Assertions.assertEquals(expectedDoubleString, doubleString);
	}

	@TestFactory
	List<DynamicTest> testDoubleToFloatString() {

		final List<DynamicTest> dynamicTestList = new ArrayList<>();
		final List<Integer> testCaseList = Arrays.asList(0, 1, 2, 3, 4, 5);
		if (testCaseList.contains(1)) {
			dynamicTestList.add(DynamicTest.dynamicTest("1 (no decimal digits)",
					() -> testDoubleToFloatStringCommon(20.0, 0, 5, "20f")));
		}
		if (testCaseList.contains(2)) {
			dynamicTestList.add(DynamicTest.dynamicTest("2 (one decimal digit zero)",
					() -> testDoubleToFloatStringCommon(20.0, 1, 5, "20.0f")));
		}
		if (testCaseList.contains(3)) {
			dynamicTestList.add(DynamicTest.dynamicTest("3 (one decimal digit not zero)",
					() -> testDoubleToFloatStringCommon(12.1, 0, 5, "12.1f")));
		}
		if (testCaseList.contains(4)) {
			dynamicTestList.add(DynamicTest.dynamicTest("4 (three decimal digits)",
					() -> testDoubleToFloatStringCommon(1.324, 0, 5, "1.324f")));
		}
		if (testCaseList.contains(5)) {
			dynamicTestList.add(DynamicTest.dynamicTest("5 (seven decimal digits)",
					() -> testDoubleToFloatStringCommon(127.753_216_500, 0, 10, "127.7532165f")));
		}
		return dynamicTestList;
	}

	private static void testDoubleToFloatStringCommon(
			final double d,
			final int mandatoryDigitCount,
			final int optionalDigitCount,
			final String expectedFloatString) {

		final String floatString = StrUtils.doubleToFloatString(d, mandatoryDigitCount, optionalDigitCount, false);
		Assertions.assertEquals(expectedFloatString, floatString);
	}

	@TestFactory
	List<DynamicTest> testDurationToString() {

		final List<DynamicTest> dynamicTestList = new ArrayList<>();
		final List<Integer> testCaseList = Arrays.asList(0, 1, 2);
		if (testCaseList.contains(1)) {
			dynamicTestList.add(DynamicTest.dynamicTest("1 (short time)",
					() -> testDurationToStringCommon(754_321, "12m 34.321s")));
		}
		if (testCaseList.contains(2)) {
			dynamicTestList.add(DynamicTest.dynamicTest("2 (long time)",
					() -> testDurationToStringCommon(12_754_321, "3h 32m 34.321s")));
		}
		return dynamicTestList;
	}

	private static void testDurationToStringCommon(
			final int millis,
			final String expectedString) {

		final Duration duration = Duration.ofMillis(millis);
		final String durationString = StrUtils.durationToString(duration);
		Assertions.assertEquals(expectedString, durationString);
	}

	@TestFactory
	List<DynamicTest> testNanoTimeToString() {

		final List<DynamicTest> dynamicTestList = new ArrayList<>();
		final List<Integer> testCaseList = Arrays.asList(0, 1, 2, 3);
		if (testCaseList.contains(1)) {
			dynamicTestList.add(DynamicTest.dynamicTest("1 (short time)",
					() -> testNanoTimeToStringCommon(75_321,
							"75" + SpecialCharacterUtils.MU + "s 321ns")));
		}
		if (testCaseList.contains(2)) {
			dynamicTestList.add(DynamicTest.dynamicTest("2 (long time)",
					() -> testNanoTimeToStringCommon(1_234_754_321,
							"1s 234ms 754" + SpecialCharacterUtils.MU + "s 321ns")));
		}
		if (testCaseList.contains(3)) {
			dynamicTestList.add(DynamicTest.dynamicTest("3 (negative time)",
					() -> testNanoTimeToStringCommon(-123, "")));
		}
		return dynamicTestList;
	}

	private static void testNanoTimeToStringCommon(
			final long nanoTime,
			final String expectedString) {

		final String nanoTimeString = StrUtils.nanoTimeToString(nanoTime);
		Assertions.assertEquals(expectedString, nanoTimeString);
	}
}
