package com.utils.io.folder_deleters;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import com.utils.annotations.ApiMethod;
import com.utils.io.IoUtils;
import com.utils.io.ro_flag_clearers.FactoryReadOnlyFlagClearer;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

class FolderDeleterImpl implements FolderDeleter {

	FolderDeleterImpl() {
	}

	@Override
	@ApiMethod
	public boolean deleteFolder(
			final String folderPathString,
			final boolean verbose) {

		final boolean success;
		if (IoUtils.directoryExists(folderPathString)) {
			success = deleteFolderNoChecks(folderPathString, verbose);
		} else {
			success = true;
		}
		return success;
	}

	@Override
	@ApiMethod
	public boolean deleteFolderNoChecks(
			final String folderPathString,
			final boolean verbose) {

		boolean success = false;
		try {
			final Path folderPath = Paths.get(folderPathString);
			Files.walkFileTree(folderPath, new SimpleFileVisitor<>() {

				@Override
				public FileVisitResult visitFile(
						final Path filePath,
						final BasicFileAttributes attrs) throws IOException {

					FactoryReadOnlyFlagClearer.getInstance()
							.clearReadOnlyFlagFileNoChecks(filePath.toString(), true);
					Files.delete(filePath);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(
						final Path dir,
						final IOException exc) throws IOException {

					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}

			});
			success = true;

		} catch (final Exception exc) {
			if (verbose) {
				Logger.printError("failed to delete folder:" + System.lineSeparator() + folderPathString);
			}
			Logger.printException(exc);
		}
		return success;
	}

	@Override
	@ApiMethod
	public boolean cleanFolder(
			final String folderPathString,
			final boolean verbose) {

		final boolean success;
		if (IoUtils.directoryExists(folderPathString)) {
			success = cleanFolderNoChecks(folderPathString, verbose);
		} else {
			success = true;
		}
		return success;
	}

	@Override
	@ApiMethod
	public boolean cleanFolderNoChecks(
			final String folderPathString,
			final boolean verbose) {

		boolean success = false;
		try {
			final Path folderPath = Paths.get(folderPathString);
			Files.walkFileTree(folderPath, new SimpleFileVisitor<>() {

				@Override
				public FileVisitResult visitFile(
						final Path filePath,
						final BasicFileAttributes attrs) throws IOException {

					FactoryReadOnlyFlagClearer.getInstance()
							.clearReadOnlyFlagFileNoChecks(filePath.toString(), true);
					Files.delete(filePath);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(
						final Path directoryPath,
						final IOException exc) throws IOException {

					if (!folderPath.equals(directoryPath)) {
						Files.delete(directoryPath);
					}
					return FileVisitResult.CONTINUE;
				}
			});
			success = true;

		} catch (final Exception exc) {
			if (verbose) {
				Logger.printError("failed to clean folder:" + System.lineSeparator() + folderPathString);
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
