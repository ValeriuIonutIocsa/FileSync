package com.utils.io.folder_creators;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.utils.annotations.ApiMethod;
import com.utils.io.IoUtils;
import com.utils.io.PathUtils;
import com.utils.io.file_deleters.FactoryFileDeleter;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

class FolderCreatorImpl implements FolderCreator {

	FolderCreatorImpl() {
	}

	@ApiMethod
	@Override
	public boolean createParentDirectories(
			final String filePathString,
			final boolean verbose) {

		final boolean success;
		if (filePathString != null) {

			final String parentFolderPathString = PathUtils.computeParentPath(filePathString);
			if (parentFolderPathString != null) {
				success = createDirectories(parentFolderPathString, verbose);
			} else {
				success = true;
			}

		} else {
			success = true;
		}
		return success;
	}

	@ApiMethod
	@Override
	public boolean createParentDirectoriesNoCheck(
			final String filePathString,
			final boolean verbose) {

		final boolean success;
		if (filePathString != null) {

			final String parentFolderPathString = PathUtils.computeParentPath(filePathString);
			if (parentFolderPathString != null) {
				success = createDirectoriesNoCheck(parentFolderPathString, verbose);
			} else {
				success = true;
			}

		} else {
			success = true;
		}
		return success;
	}

	@ApiMethod
	@Override
	public boolean createDirectories(
			final String directoryPathString,
			final boolean verbose) {

		boolean success = false;
		if (directoryPathString == null || IoUtils.directoryExists(directoryPathString)) {
			success = true;

		} else {
			if (IoUtils.fileExists(directoryPathString)) {

				final boolean deleteFileSuccess =
						FactoryFileDeleter.getInstance().deleteFile(directoryPathString, verbose);
				if (deleteFileSuccess) {
					success = createDirectoriesNoCheck(directoryPathString, verbose);
				}

			} else {
				success = createDirectoriesNoCheck(directoryPathString, verbose);
			}

		}
		return success;
	}

	@ApiMethod
	@Override
	public boolean createDirectoriesNoCheck(
			final String directoryPathString,
			final boolean verbose) {

		boolean success = false;
		try {
			final Path directoryPath = Paths.get(directoryPathString);
			Files.createDirectories(directoryPath);
			success = true;

		} catch (final Exception exc) {
			if (verbose) {
				Logger.printError("failed to create directory:" +
						System.lineSeparator() + directoryPathString);
			}
			Logger.printException(exc);
		}
		return success;
	}

	@ApiMethod
	@Override
	public boolean createDirectory(
			final String directoryPathString,
			final boolean verbose) {

		boolean success = false;
		if (directoryPathString == null || IoUtils.directoryExists(directoryPathString)) {
			success = true;

		} else {
			if (IoUtils.fileExists(directoryPathString)) {

				final boolean deleteFileSuccess =
						FactoryFileDeleter.getInstance().deleteFile(directoryPathString, verbose);
				if (deleteFileSuccess) {
					success = createDirectoryNoChecks(directoryPathString, verbose);
				}

			} else {
				success = createDirectoryNoChecks(directoryPathString, verbose);
			}

		}
		return success;
	}

	@ApiMethod
	@Override
	public boolean createDirectoryNoChecks(
			final String directoryPathString,
			final boolean verbose) {

		boolean success = false;
		try {
			final Path directoryPath = Paths.get(directoryPathString);
			Files.createDirectory(directoryPath);
			success = true;

		} catch (final Exception exc) {
			if (verbose) {
				Logger.printError("failed to create directory:" +
						System.lineSeparator() + directoryPathString);
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
