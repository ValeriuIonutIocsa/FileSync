package com.utils.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;

public final class ListFileUtils {

	private ListFileUtils() {
	}

	@ApiMethod
	public static List<String> listFiles(
			final String directoryPathString) {

		final List<String> filePathStringList = new ArrayList<>();
		try {
			final Path directoryPath = Paths.get(directoryPathString);
			try (Stream<Path> fileListStream = Files.list(directoryPath)) {

				fileListStream.forEach(filePath -> {

					final String filePathString = filePath.toString();
					filePathStringList.add(filePathString);
				});
			}

		} catch (final Exception exc) {
			Logger.printError("failed to list files inside folder:" +
					System.lineSeparator() + directoryPathString);
			Logger.printException(exc);
		}
		return filePathStringList;
	}

	@ApiMethod
	public static List<String> listFiles(
			final String directoryPathString,
			final Predicate<Path> filterPredicate) {

		final List<String> filePathStringList;
		if (filterPredicate == null) {
			filePathStringList = listFiles(directoryPathString);

		} else {
			filePathStringList = new ArrayList<>();
			try {
				final Path directoryPath = Paths.get(directoryPathString);
				try (Stream<Path> fileListStream = Files.list(directoryPath)) {

					fileListStream.filter(filterPredicate).forEach(filePath -> {

						final String filePathString = filePath.toString();
						filePathStringList.add(filePathString);
					});
				}

			} catch (final Exception exc) {
				Logger.printError("failed to list files inside folder:" +
						System.lineSeparator() + directoryPathString);
				Logger.printException(exc);
			}
		}
		return filePathStringList;
	}

	@ApiMethod
	public static List<String> listFilesRecursively(
			final String directoryPathString) {

		final List<String> filePathStringList = new ArrayList<>();
		try {
			final Path directoryPath = Paths.get(directoryPathString);
			try (Stream<Path> fileListStream = Files.walk(directoryPath)) {
				fileListStream.forEach(filePath -> {

					final String filePathString = filePath.toString();
					filePathStringList.add(filePathString);
				});
			}

		} catch (final Exception exc) {
			Logger.printError("failed to list files recursively inside folder:" +
					System.lineSeparator() + directoryPathString);
			Logger.printException(exc);
		}

		return filePathStringList;
	}

	@ApiMethod
	public static List<String> listFilesRecursively(
			final String directoryPathString,
			final Predicate<Path> filterPredicate) {

		final List<String> filePathStringList;
		if (filterPredicate == null) {
			filePathStringList = listFilesRecursively(directoryPathString);

		} else {
			filePathStringList = new ArrayList<>();
			try {
				final Path directoryPath = Paths.get(directoryPathString);
				try (Stream<Path> fileListStream = Files.walk(directoryPath)) {
					fileListStream.filter(filterPredicate).forEach(filePath -> {

						final String filePathString = filePath.toString();
						filePathStringList.add(filePathString);
					});
				}

			} catch (final Exception exc) {
				Logger.printError("failed to list files inside folder:" +
						System.lineSeparator() + directoryPathString);
				Logger.printException(exc);
			}
		}
		return filePathStringList;
	}
}
