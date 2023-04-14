package com.utils.io.ro_flag_clearers;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import com.utils.annotations.ApiMethod;
import com.utils.io.IoUtils;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

class ReadOnlyFlagClearerImpl implements ReadOnlyFlagClearer {

	ReadOnlyFlagClearerImpl() {
	}

	@Override
	public boolean clearReadOnlyFlagFile(
			final String filePathString,
			final boolean verbose) {

		final boolean success;
		if (IoUtils.fileExists(filePathString)) {
			success = clearReadOnlyFlagFileNoChecks(filePathString, verbose);
		} else {
			success = true;
		}
		return success;
	}

	@Override
	@ApiMethod
	public boolean clearReadOnlyFlagFileNoChecks(
			final String filePathString,
			final boolean verbose) {

		boolean success = false;
		try {
			final Path filePath = Paths.get(filePathString);
			Files.setAttribute(filePath, "dos:readonly", false);
			success = true;

		} catch (final Exception exc) {
			if (verbose) {
				Logger.printError("failed to clear readonly flag for path:" +
						System.lineSeparator() + filePathString);
			}
			Logger.printException(exc);
		}
		return success;
	}

	@Override
	public boolean clearReadOnlyFlagFolder(
			final String folderPathString,
			final boolean verbose) {

		final boolean success;
		if (IoUtils.directoryExists(folderPathString)) {
			success = clearReadOnlyFlagFolderNoChecks(folderPathString, verbose);
		} else {
			success = true;
		}
		return success;
	}

	@Override
	public boolean clearReadOnlyFlagFolderNoChecks(
			final String folderPathString,
			final boolean verbose) {

		boolean success = false;
		try {
			final Path folderPath = Paths.get(folderPathString);
			Files.walkFileTree(folderPath, new SimpleFileVisitor<>() {

				@Override
				public FileVisitResult visitFile(
						final Path filePath,
						final BasicFileAttributes attr) {

					final String filePathString = filePath.toString();
					clearReadOnlyFlagFileNoChecks(filePathString, true);
					return FileVisitResult.CONTINUE;
				}
			});
			success = true;

		} catch (final Exception exc) {
			if (verbose) {
				Logger.printError("failed to clear the readonly flags of folder:" +
						System.lineSeparator() + folderPathString);
			}
			Logger.printException(exc);
		}
		return success;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
