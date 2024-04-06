package com.utils.io;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.utils.log.Logger;

class ListFileUtilsTest {

	@Test
	void testVisitFiles() {

		final String rootDirPathString =
				PathUtils.computePath(PathUtils.createRootPath(), "IVI_MISC", "Docs");

		final List<String> dirPathStringList = new ArrayList<>();
		final List<String> filePathStringList = new ArrayList<>();
		ListFileUtils.visitFiles(rootDirPathString,
				dirPath -> {

					final String dirPathString = dirPath.toString();
					dirPathStringList.add(dirPathString);

				}, filePath -> {

					final String filePathString = filePath.toString();
					filePathStringList.add(filePathString);
				});

		Logger.printLine("root dir path:");
		Logger.printLine(rootDirPathString);
		Logger.printLine("dir paths:");
		for (final String dirPathString : dirPathStringList) {
			Logger.printLine(dirPathString);
		}
		Logger.printLine("file paths:");
		for (final String filePathString : filePathStringList) {
			Logger.printLine(filePathString);
		}
	}

	@Test
	void testVisitFilesRecursively() {

		final String rootDirPathString =
				PathUtils.computePath(PathUtils.createRootPath(), "IVI_MISC", "Docs");

		final List<String> dirPathStringList = new ArrayList<>();
		final List<String> filePathStringList = new ArrayList<>();
		ListFileUtils.visitFilesRecursively(rootDirPathString,
				dirPath -> {

					final String dirPathString = dirPath.toString();
					dirPathStringList.add(dirPathString);

				}, filePath -> {

					final String filePathString = filePath.toString();
					filePathStringList.add(filePathString);
				});

		Logger.printLine("root dir path:");
		Logger.printLine(rootDirPathString);
		Logger.printLine("dir paths:");
		for (final String dirPathString : dirPathStringList) {
			Logger.printLine(dirPathString);
		}
		Logger.printLine("file paths:");
		for (final String filePathString : filePathStringList) {
			Logger.printLine(filePathString);
		}
	}
}
