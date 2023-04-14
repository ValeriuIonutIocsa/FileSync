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
			final boolean verboseProgress,
			final boolean verboseError) {

		final boolean success;
		if (IoUtils.fileExists(filePathString)) {
			success = deleteFileNoChecks(filePathString, verboseProgress, verboseError);
		} else {
			success = true;
		}
		return success;
	}

	@Override
	public boolean deleteFileNoChecks(
			final String filePathString,
			final boolean verboseProgress,
			final boolean verboseError) {

		boolean success = false;
		try {
			if (verboseProgress) {

				Logger.printProgress("deleting file:");
				Logger.printLine(filePathString);
			}

			final boolean keepGoing = FactoryReadOnlyFlagClearer.getInstance()
					.clearReadOnlyFlagFile(filePathString, false, verboseError);
			if (keepGoing) {

				final Path filePath = Paths.get(filePathString);
				Files.delete(filePath);

				success = true;
			}

		} catch (final Exception exc) {
			Logger.printException(exc);
		}

		if (!success) {
			if (verboseError) {
				Logger.printError("failed to delete file:" +
						System.lineSeparator() + filePathString);
			}
		}

		return success;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
