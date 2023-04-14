package com.utils.io;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;

public final class ResourceFileUtils {

	private ResourceFileUtils() {
	}

	@ApiMethod
	public static String resourceFileToString(
			final String resourceFilePathString) {

		String str = "";
		try {
			final InputStream inputStream = resourceFileToInputStream(resourceFilePathString);
			str = ReaderUtils.inputStreamToString(inputStream);

		} catch (final Exception exc) {
			Logger.printError("failed to get resource file content for:" +
					System.lineSeparator() + resourceFilePathString);
			Logger.printException(exc);
		}
		return str;
	}

	@ApiMethod
	public static byte[] resourceFileToByteArray(
			final String resourceFilePathString) {

		byte[] bytes = null;
		try {
			final InputStream inputStream = resourceFileToInputStream(resourceFilePathString);
			bytes = IOUtils.toByteArray(inputStream);

		} catch (final Exception exc) {
			Logger.printError("failed to get resource file content for:" +
					System.lineSeparator() + resourceFilePathString);
			Logger.printException(exc);
		}
		return bytes;
	}

	@ApiMethod
	public static InputStream resourceFileToInputStream(
			final String resourceFilePathString) {

		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(resourceFilePathString);
	}

	@ApiMethod
	public static URL resourceFileToUrl(
			final String resourceFilePathString) {

		return Thread.currentThread().getContextClassLoader()
				.getResource(resourceFilePathString);
	}
}
