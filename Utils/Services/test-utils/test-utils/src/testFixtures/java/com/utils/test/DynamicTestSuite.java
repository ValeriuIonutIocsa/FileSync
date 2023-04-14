package com.utils.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

public class DynamicTestSuite {

	public enum Mode {
		ALL, SELECTED
	}

	private final Mode mode;
	private final Runnable runnable;
	private final DynamicTestOptions<?>[] dynamicTestOptionsArray;

	public DynamicTestSuite(
			final Mode mode,
			final Runnable runnable,
			final DynamicTestOptions<?>... dynamicTestOptionsArray) {

		this.mode = mode;
		this.runnable = runnable;
		this.dynamicTestOptionsArray = dynamicTestOptionsArray;
	}

	public List<DynamicTest> createDynamicTestList() {

		if (runnable == null) {

			System.err.println("ERROR - no runnable is configured");
			Assertions.fail();
		}

		final List<DynamicTest> dynamicTestList = new ArrayList<>();
		if (mode == Mode.ALL) {

			final Stack<Integer> indexStack = new Stack<>();
			addDynamicTestsRec(0, indexStack, dynamicTestList);

		} else if (mode == Mode.SELECTED) {
			addDynamicTest(runnable::run, dynamicTestList);

		} else {
			System.err.println("ERROR - invalid mode");
			Assertions.fail();
		}
		return dynamicTestList;
	}

	private void addDynamicTestsRec(
			final int optionsIndex,
			final Stack<Integer> indexStack,
			final List<DynamicTest> dynamicTestList) {

		if (optionsIndex == dynamicTestOptionsArray.length) {

			final List<Integer> indexList = new ArrayList<>(indexStack);
			addDynamicTest(() -> {

				for (int i = 0; i < dynamicTestOptionsArray.length; i++) {

					final DynamicTestOptions<?> dynamicTestOptions = dynamicTestOptionsArray[i];
					final int index = indexList.get(i);
					dynamicTestOptions.setSelectedOptionIndex(index);
				}
				runnable.run();

			}, dynamicTestList);

		} else {
			final DynamicTestOptions<?> dynamicTestOptions = dynamicTestOptionsArray[optionsIndex];
			final List<? extends DynamicTestOption<?>> dynamicTestOptionList =
					dynamicTestOptions.getDynamicTestOptionList();
			for (final DynamicTestOption<?> dynamicTestOption : dynamicTestOptionList) {

				final int index = dynamicTestOption.getIndex();
				dynamicTestOptions.setSelectedOptionIndex(index);
				indexStack.push(index);
				addDynamicTestsRec(optionsIndex + 1, indexStack, dynamicTestList);
				indexStack.pop();
			}
		}
	}

	private void addDynamicTest(
			final Executable executable,
			final List<DynamicTest> dynamicTestList) {

		final List<String> optionsDisplayNameList = new ArrayList<>();
		for (final DynamicTestOptions<?> dynamicTestOptions : dynamicTestOptionsArray) {

			final String optionsDisplayName = dynamicTestOptions.createDisplayName();
			optionsDisplayNameList.add(optionsDisplayName);
		}
		final String displayName = String.join(" ", optionsDisplayNameList);

		dynamicTestList.add(DynamicTest.dynamicTest(displayName, executable));
	}
}
