package com.utils.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;

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
			fileSizeString = humanReadableByteCountBin(fileSize);

		} catch (final Exception exc) {
			Logger.printError("failed to compute readable size of file:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
		return fileSizeString;
	}

	public static String humanReadableByteCountBin(
			final long bytes) {

		final String result;
		final long absB;
		if (bytes == Long.MIN_VALUE) {
			absB = Long.MAX_VALUE;
		} else {
			absB = Math.abs(bytes);
		}
		if (absB < 1024) {
			result = bytes + " B";

		} else {
			long value = absB;
			final CharacterIterator ci = new StringCharacterIterator("KMGTPE");
			for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {

				value >>= 10;
				ci.next();
			}
			value *= Long.signum(bytes);
			result = String.format("%.1f %ciB", value / 1024.0, ci.current());
		}
		return result;
	}
}
