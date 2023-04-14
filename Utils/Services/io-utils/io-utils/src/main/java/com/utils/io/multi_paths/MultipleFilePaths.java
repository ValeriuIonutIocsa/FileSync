package com.utils.io.multi_paths;

import java.util.ArrayList;
import java.util.List;

import com.utils.io.IoUtils;
import com.utils.string.StrUtils;

public class MultipleFilePaths {

	private final List<String> pathStringList;

	public MultipleFilePaths() {

		pathStringList = new ArrayList<>();
	}

	public void addPath(
			final String pathString) {
		pathStringList.add(pathString);
	}

	public String computeFirstPathThatExists() {

		String firstPathStringThatExists = null;
		for (final String pathString : pathStringList) {

			if (IoUtils.fileExists(pathString)) {
				firstPathStringThatExists = pathString;
				break;
			}
		}
		return firstPathStringThatExists;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
