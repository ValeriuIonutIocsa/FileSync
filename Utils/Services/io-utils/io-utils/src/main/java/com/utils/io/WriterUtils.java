package com.utils.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.io.ro_flag_clearers.FactoryReadOnlyFlagClearer;
import com.utils.log.Logger;

public final class WriterUtils {

	private WriterUtils() {
	}

	public static BufferedWriter openBufferedWriter(
			final String filePathString) throws IOException {

		return openBufferedWriter(filePathString, StandardCharsets.UTF_8);
	}

	public static BufferedWriter openBufferedWriter(
			final String filePathString,
			final Charset charset) throws IOException {

		final Path filePath = Paths.get(filePathString);
		return Files.newBufferedWriter(filePath, charset);
	}

	public static boolean byteArrayToFile(
			final byte[] byteArray,
			final String filePathString) {

		boolean success = false;
		try {
			final Path filePath = Paths.get(filePathString);
			Files.write(filePath, byteArray);
			success = true;

		} catch (final Exception exc) {
			Logger.printError("failed to write byte array to file:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
		return success;
	}

	public static void stringToFile(
			final String string,
			final Charset charset,
			final String filePathString) throws Exception {

		FactoryFolderCreator.getInstance().createParentDirectories(filePathString, true);
		FactoryReadOnlyFlagClearer.getInstance().clearReadOnlyFlagFile(filePathString, true);
		try (PrintStream printStream = StreamUtils.openPrintStream(filePathString, false, charset)) {
			printStream.print(string);
		}
	}
}
