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
			final boolean verbose) {

		final boolean success = false;
		try {
			final Path filePath = Paths.get(filePathString);
			Files.createFile(filePath);

		} catch (final Exception exc) {
			if (verbose) {
				Logger.printError("failed to create file:" + System.lineSeparator() + filePathString);
			}
			Logger.printException(exc);
		}
		return success;
	}
}
