package com.utils.string.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class GzipCompressionUtils {

	private GzipCompressionUtils() {
	}

	public static byte[] compress(
			final byte[] uncompressedData) {

		byte[] compressedData = null;
		if (uncompressedData != null) {

			try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(uncompressedData.length);
					GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {

				gzipOutputStream.write(uncompressedData);
				gzipOutputStream.close();
				compressedData = byteArrayOutputStream.toByteArray();

			} catch (final Exception ignored) {
			}
		}
		return compressedData;
	}

	public static byte[] decompress(
			final byte[] compressedData) {

		byte[] decompressedData = null;
		if (compressedData != null) {

			try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressedData));
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

				final byte[] buffer = new byte[1024];
				int len;
				while ((len = gzipInputStream.read(buffer)) != -1) {
					byteArrayOutputStream.write(buffer, 0, len);
				}
				decompressedData = byteArrayOutputStream.toByteArray();

			} catch (final Exception ignored) {
			}
		}
		return decompressedData;
	}
}
