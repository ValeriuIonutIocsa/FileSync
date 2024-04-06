package com.utils.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;
import com.utils.string.size.SizeUtils;

public final class FileSizeUtils {

	private FileSizeUtils() {
	}

	@ApiMethod
	public static long fileSize(
			final String filePathString) {

		long fileSize = -1;
		try {
			final Path filePath = Paths.get(filePathString);
			fileSize = Files.size(filePath);

		} catch (final Exception exc) {
			Logger.printError("failed to compute size of file:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
		return fileSize;
	}

	@ApiMethod
	public static String readableFileSize(
			final String filePathString) {

		String fileSizeString = null;
		try {
			final Path filePath = Paths.get(filePathString);
			final long fileSize = Files.size(filePath);
			fileSizeString = SizeUtils.humanReadableByteCountBin(fileSize);

		} catch (final Exception exc) {
			Logger.printError("failed to compute readable size of file:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
		return fileSizeString;
	}
}
