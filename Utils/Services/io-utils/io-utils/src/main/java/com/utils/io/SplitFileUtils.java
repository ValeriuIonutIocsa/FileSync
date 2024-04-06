package com.utils.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import com.utils.log.Logger;

public final class SplitFileUtils {

	private SplitFileUtils() {
	}

	public static void splitFile(
			final String filePathString,
			final long sizeOfChunk) {

		try {
			Logger.printProgress("splitting into chunks file:");
			Logger.printLine(filePathString);

			int counter = 1;
			try (BufferedReader bufferedReader =
					ReaderUtils.openBufferedReader(filePathString)) {

				final char[] buffer = new char[1_024];
				int readCharCount = bufferedReader.read(buffer);
				while (readCharCount >= 0) {

					final String outputFilePathString =
							PathUtils.appendFileNameSuffix(filePathString, "_" + counter++);
					try (BufferedWriter bufferedWriter =
							WriterUtils.openBufferedWriter(outputFilePathString)) {

						long fileSize = 0;
						while (readCharCount >= 0) {

							if (fileSize > sizeOfChunk) {
								break;
							}

							bufferedWriter.write(buffer);
							fileSize += readCharCount;

							readCharCount = bufferedReader.read(buffer);
						}
					}
				}
			}

		} catch (final Exception exc) {
			Logger.printError("failed to split into chucks the file:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
	}
}
