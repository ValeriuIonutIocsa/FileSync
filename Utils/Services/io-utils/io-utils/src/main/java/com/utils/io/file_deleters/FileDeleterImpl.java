package com.utils.io.file_deleters;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.utils.io.IoUtils;
import com.utils.io.ro_flag_clearers.FactoryReadOnlyFlagClearer;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

class FileDeleterImpl implements FileDeleter {

	FileDeleterImpl() {
	}

	@Override
	public boolean deleteFile(
			final String filePathString,
			final boolean verbose) {

		final boolean success;
		if (IoUtils.fileExists(filePathString)) {
			success = deleteFileNoChecks(filePathString, verbose);
		} else {
			success = true;
		}
		return success;
	}

	@Override
	public boolean deleteFileNoChecks(
			final String filePathString,
			final boolean verbose) {

		boolean success = false;
		try {
			FactoryReadOnlyFlagClearer.getInstance().clearReadOnlyFlagFile(filePathString, true);

			final Path filePath = Paths.get(filePathString);
			Files.delete(filePath);

			success = true;

		} catch (final Exception exc) {
			if (verbose) {
				Logger.printError("failed to delete file:" + System.lineSeparator() + filePathString);
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
