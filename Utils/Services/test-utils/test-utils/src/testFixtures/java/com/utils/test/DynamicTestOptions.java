package com.utils.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

public class DynamicTestOptions<
		ObjectT> {

	private final String name;
	private int selectedOptionIndex;

	private final List<DynamicTestOption<ObjectT>> dynamicTestOptionList;

	public DynamicTestOptions(
			final String name,
			final int selectedOptionIndex) {

		this.name = name;
		this.selectedOptionIndex = selectedOptionIndex;

		dynamicTestOptionList = new ArrayList<>();
	}

	public String createDisplayName() {

		String displayName = name + " " + selectedOptionIndex;
		final DynamicTestOption<ObjectT> selectedDynamicTestOption = computeSelectedDynamicTestOption();
		final String description = selectedDynamicTestOption.getDescription();
		if (description != null && !description.isBlank()) {
			displayName += " " + description;
		}
		return displayName;
	}

	public ObjectT computeValue() {

		final DynamicTestOption<ObjectT> selectedDynamicTestOption = computeSelectedDynamicTestOption();
		return selectedDynamicTestOption.getValue();
	}

	private DynamicTestOption<ObjectT> computeSelectedDynamicTestOption() {

		DynamicTestOption<ObjectT> selectedDynamicTestOption = null;
		for (final DynamicTestOption<ObjectT> dynamicTestOption : dynamicTestOptionList) {

			final int index = dynamicTestOption.getIndex();
			if (index == selectedOptionIndex) {
				selectedDynamicTestOption = dynamicTestOption;
			}
		}
		if (selectedDynamicTestOption == null) {

			System.err.println("ERROR - failed to compute selected " + name + " option");
			Assertions.fail();
		}
		return selectedDynamicTestOption;
	}

	void setSelectedOptionIndex(
			final int selectedOptionIndex) {
		this.selectedOptionIndex = selectedOptionIndex;
	}

	int getSelectedOptionIndex() {
		return selectedOptionIndex;
	}

	public List<DynamicTestOption<ObjectT>> getDynamicTestOptionList() {
		return dynamicTestOptionList;
	}
}
