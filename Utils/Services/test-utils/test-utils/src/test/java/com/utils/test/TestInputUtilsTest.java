package com.utils.test;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class TestInputUtilsTest {

	@TestFactory
	List<DynamicTest> testParseTestInputBoolean() {

		final DynamicTestOptions<TestParseTestInputBooleanData> dynamicTestOptions =
				new DynamicTestOptions<>("", 1);

		dynamicTestOptions.getDynamicTestOptionList()
				.add(new DynamicTestOption<>(1, "true input", new TestParseTestInputBooleanData("true", true)));
		dynamicTestOptions.getDynamicTestOptionList()
				.add(new DynamicTestOption<>(2, "false input", new TestParseTestInputBooleanData("false", false)));
		dynamicTestOptions.getDynamicTestOptionList()
				.add(new DynamicTestOption<>(3, "random input", new TestParseTestInputBooleanData("abc", false)));
		dynamicTestOptions.getDynamicTestOptionList()
				.add(new DynamicTestOption<>(4, "empty input", new TestParseTestInputBooleanData("", false)));
		dynamicTestOptions.getDynamicTestOptionList()
				.add(new DynamicTestOption<>(5, "null input", new TestParseTestInputBooleanData(null, false)));

		return new DynamicTestSuite(DynamicTestSuite.Mode.ALL,
				() -> testParseTestInputBooleanCommon(dynamicTestOptions),
				dynamicTestOptions).createDynamicTestList();
	}

	private static void testParseTestInputBooleanCommon(
			final DynamicTestOptions<TestParseTestInputBooleanData> dynamicTestOptions) {

		final TestParseTestInputBooleanData testParseTestInputBooleanData = dynamicTestOptions.computeValue();
		final boolean inputBoolean = TestInputUtils.parseTestInputBoolean(
				testParseTestInputBooleanData.inputBooleanString);
		Assertions.assertEquals(testParseTestInputBooleanData.expectedInputBoolean, inputBoolean);
	}

	static class TestParseTestInputBooleanData {

		final String inputBooleanString;
		final boolean expectedInputBoolean;

		TestParseTestInputBooleanData(
				final String inputBooleanString,
				final boolean expectedInputBoolean) {

			this.inputBooleanString = inputBooleanString;
			this.expectedInputBoolean = expectedInputBoolean;
		}
	}

	@TestFactory
	List<DynamicTest> testParseTestInputNumber() {

		final DynamicTestOptions<TestParseTestInputNumberData> dynamicTestOptions =
				new DynamicTestOptions<>("", 1);

		dynamicTestOptions.getDynamicTestOptionList()
				.add(new DynamicTestOption<>(1, "positive input", new TestParseTestInputNumberData("12", 12)));
		dynamicTestOptions.getDynamicTestOptionList()
				.add(new DynamicTestOption<>(2, "negative input", new TestParseTestInputNumberData("-15", -15)));
		dynamicTestOptions.getDynamicTestOptionList()
				.add(new DynamicTestOption<>(3, "random input", new TestParseTestInputNumberData("abc", -1)));
		dynamicTestOptions.getDynamicTestOptionList()
				.add(new DynamicTestOption<>(4, "empty input", new TestParseTestInputNumberData("", -1)));
		dynamicTestOptions.getDynamicTestOptionList()
				.add(new DynamicTestOption<>(5, "null input", new TestParseTestInputNumberData(null, -1)));

		return new DynamicTestSuite(DynamicTestSuite.Mode.ALL,
				() -> testParseTestInputNumberCommon(dynamicTestOptions),
				dynamicTestOptions).createDynamicTestList();
	}

	private static void testParseTestInputNumberCommon(
			final DynamicTestOptions<TestParseTestInputNumberData> dynamicTestOptions) {

		final TestParseTestInputNumberData testParseTestInputNumberData = dynamicTestOptions.computeValue();
		final int inputNumber = TestInputUtils.parseTestInputNumber(
				testParseTestInputNumberData.inputNumberString);
		Assertions.assertEquals(testParseTestInputNumberData.expectedInputNumber, inputNumber);
	}

	static class TestParseTestInputNumberData {

		final String inputNumberString;
		final int expectedInputNumber;

		TestParseTestInputNumberData(
				final String inputNumberString,
				final int expectedInputNumber) {

			this.inputNumberString = inputNumberString;
			this.expectedInputNumber = expectedInputNumber;
		}
	}
}
