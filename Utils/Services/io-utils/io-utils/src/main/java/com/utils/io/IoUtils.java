package com.utils.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.time.Instant;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

public final class IoUtils {

	private IoUtils() {
	}

	/**
	 * @param pathString
	 *            the input path
	 * @return false if the path is null, blank or if the file does not exist, true otherwise
	 */
	@ApiMethod
	public static boolean fileExists(
			final String pathString) {
		return StringUtils.isNotBlank(pathString) && new File(pathString).exists();
	}

	/**
	 * @param pathString
	 *            the input path
	 * @return false if the path is null, blank or if the file is not a directory, true otherwise
	 */
	@ApiMethod
	public static boolean directoryExists(
			final String pathString) {
		return StringUtils.isNotBlank(pathString) && new File(pathString).isDirectory();
	}

	@ApiMethod
	public static long computeFileLastModifiedTime(
			final String filePathString) {

		long lastModifiedTime = -1;
		try {
			final Path filePath = Paths.get(filePathString);
			lastModifiedTime = Files.getLastModifiedTime(filePath).toMillis();

		} catch (final Exception exc) {
			Logger.printError("failed to compute last modified time of file:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
		return lastModifiedTime;
	}

	@ApiMethod
	public static void configureFileLastModifiedTime(
			final String filePathString,
			final Instant lastModifiedTimeInstant) {

		try {
			final Path filePath = Paths.get(filePathString);
			Files.setLastModifiedTime(filePath, FileTime.from(lastModifiedTimeInstant));

		} catch (final Exception exc) {
			Logger.printError("failed to configure last modified time of file:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
	}

	@ApiMethod
	public static byte[] fileMd5HashCode(
			final String filePathString) {

		byte[] md5HashCode = null;
		try {
			final byte[] fileBytes = ReaderUtils.fileToByteArray(filePathString);
			final MessageDigest messageDigestMd5 = MessageDigest.getInstance("MD5");
			md5HashCode = messageDigestMd5.digest(fileBytes);

		} catch (final Exception exc) {
			Logger.printError("failed to compute MD5 hash code of file:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
		return md5HashCode;
	}

	@ApiMethod
	public static int computeLineCount(
			final String filePathString) {

		int lineCount = 0;
		try (BufferedReader bufferedReader = ReaderUtils.openBufferedReader(filePathString)) {

			while (bufferedReader.readLine() != null) {
				lineCount++;
			}

		} catch (final Exception exc) {
			Logger.printError("failed to compute line count of file:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
		return lineCount;
	}

	@ApiMethod
	public static String createTmpFile(
			final String filePathString,
			final String tmpFilePathString) {

		String resultTmpFilePathString = null;
		try {
			final File tmpFile;
			if (tmpFilePathString != null) {
				tmpFile = new File(tmpFilePathString);
			} else {
				tmpFile = File.createTempFile(StrUtils.createDateTimeString(), ".tmp");
			}

			tmpFile.deleteOnExit();

			try (InputStream inputStream = StreamUtils.openInputStream(filePathString);
					OutputStream outputStream = Files.newOutputStream(tmpFile.toPath())) {
				IOUtils.copy(inputStream, outputStream);
			}
			resultTmpFilePathString = tmpFile.getPath();

		} catch (final Exception exc) {
			Logger.printError("failed to create temporary file");
			Logger.printException(exc);
		}
		return resultTmpFilePathString;
	}

	@ApiMethod
	public static String createTmpFile(
			final InputStream inputStream,
			final String tmpFilePathString) {

		String resultTmpFilePathString = null;
		try {
			final File tmpFile;
			if (tmpFilePathString != null) {
				tmpFile = new File(tmpFilePathString);
			} else {
				tmpFile = File.createTempFile(StrUtils.createDateTimeString(), ".tmp");
			}

			tmpFile.deleteOnExit();

			try (OutputStream outputStream = Files.newOutputStream(tmpFile.toPath())) {
				IOUtils.copy(inputStream, outputStream);
			}
			resultTmpFilePathString = tmpFile.getPath();

		} catch (final Exception exc) {
			Logger.printError("failed to create temporary file");
			Logger.printException(exc);
		}
		return resultTmpFilePathString;
	}

	@ApiMethod
	public static boolean openFileWithDefaultApp(
			final String filePathString) {

		boolean success = false;
		try {
			final ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command("cmd", "/c", "start", filePathString);
			processBuilder.inheritIO();
			final Process process = processBuilder.start();
			final int exitCode = process.waitFor();
			success = exitCode == 0;

		} catch (final Exception exc) {
			Logger.printError("failed to open file with default app:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
		return success;
	}
}
