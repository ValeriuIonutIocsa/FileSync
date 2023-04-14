package com.utils.io;

import com.utils.string.StrUtils;

public class FileCompareData {

	private final String filePathString;
	private final String otherFilePathString;
	private final boolean contentEquals;

	FileCompareData(
            final String filePathString,
            final String otherFilePathString,
            final boolean contentEquals) {

		this.filePathString = filePathString;
		this.otherFilePathString = otherFilePathString;
		this.contentEquals = contentEquals;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public String getFilePathString() {
		return filePathString;
	}

	public String getOtherFilePathString() {
		return otherFilePathString;
	}

	public boolean isContentEquals() {
		return contentEquals;
	}
}
