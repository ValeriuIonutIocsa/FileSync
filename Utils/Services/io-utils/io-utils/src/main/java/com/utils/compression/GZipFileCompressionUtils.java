package com.utils.compression;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

import com.utils.io.StreamUtils;

public final class GZipFileCompressionUtils {

	private GZipFileCompressionUtils() {
	}

	public static void compressFile(
			final String inputFilePathString,
			final String outputFilePathString) throws Exception {

		try (InputStream inputStream = StreamUtils.openInputStream(inputFilePathString);
				OutputStream outputStream = new GZIPOutputStream(StreamUtils.openOutputStream(outputFilePathString))) {

			IOUtils.copy(inputStream, outputStream);
		}
	}

	public static void decompressFile(
			final String inputFilePathString,
			final String outputFilePathString) throws Exception {

		try (InputStream inputStream = new GZIPInputStream(StreamUtils.openInputStream(inputFilePathString));
				OutputStream outputStream = StreamUtils.openOutputStream(outputFilePathString)) {

			IOUtils.copy(inputStream, outputStream);
		}
	}
}
