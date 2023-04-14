package com.utils.io.file_creators;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;

class FileCreatorImpl implements FileCreator {

	FileCreatorImpl() {
	}

	@ApiMethod
	@Override
	public boolean createFile(
			final String filePathString,
			final boolean verboseProgress,
			final boolean verboseError) {

		boolean success = false;
		try {
			if (verboseProgress) {

				Logger.printProgress("creating file:");
				Logger.printLine(filePathString);
			}

			final Path filePath = Paths.get(filePathString);
			Files.createFile(filePath);

			success = true;

		} catch (final Exception exc) {
			Logger.printException(exc);
		}

		if (!success) {
			if (verboseError) {
				Logger.printError("failed to create file:" +
						System.lineSeparator() + filePathString);
			}
		}

		return success;
	}
}
