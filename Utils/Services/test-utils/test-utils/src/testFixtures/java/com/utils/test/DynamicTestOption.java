package com.utils.test;

public class DynamicTestOption<
		ObjectT> {

	private final int index;
	private final String description;
	private final ObjectT value;

	public DynamicTestOption(
			final int index,
			final String description,
			final ObjectT value) {

		this.index = index;
		this.description = description;
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public String getDescription() {
		return description;
	}

	public ObjectT getValue() {
		return value;
	}
}
