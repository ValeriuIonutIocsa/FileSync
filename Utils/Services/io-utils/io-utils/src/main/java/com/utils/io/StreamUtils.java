package com.utils.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.utils.annotations.ApiMethod;

public final class StreamUtils {

	private StreamUtils() {
	}

	@ApiMethod
	public static InputStream openBufferedInputStream(
			final String filePathString,
			final OpenOption... openOptions) throws Exception {

		final InputStream inputStream = openInputStream(filePathString, openOptions);
		return new BufferedInputStream(inputStream);
	}

	@ApiMethod
	public static InputStream openInputStream(
			final String filePathString,
			final OpenOption... openOptions) throws Exception {

		final Path filePath = Paths.get(filePathString);
		return Files.newInputStream(filePath, openOptions);
	}

	@ApiMethod
	public static PrintStream openPrintStream(
			final String filePathString) throws IOException {

		return openPrintStream(filePathString, false, StandardCharsets.UTF_8);
	}

	@ApiMethod
	public static PrintStream openPrintStream(
			final String filePathString,
			final boolean autoFlush,
			final Charset charset,
			final OpenOption... openOptions) throws IOException {

		final OutputStream outputStream = openBufferedOutputStream(filePathString, openOptions);
		return new PrintStream(outputStream, autoFlush, charset);
	}

	@ApiMethod
	public static OutputStream openBufferedOutputStream(
			final String filePathString,
			final OpenOption... openOptions) throws IOException {

		final OutputStream outputStream = openOutputStream(filePathString, openOptions);
		return new BufferedOutputStream(outputStream);
	}

	@ApiMethod
	public static OutputStream openOutputStream(
			final String filePathString,
			final OpenOption... openOptions) throws IOException {

		final Path filePath = Paths.get(filePathString);
		return Files.newOutputStream(filePath, openOptions);
	}
}
