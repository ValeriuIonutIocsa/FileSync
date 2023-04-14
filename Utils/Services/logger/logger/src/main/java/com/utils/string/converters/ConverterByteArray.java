package com.utils.string.converters;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class ConverterByteArray {

	private ConverterByteArray() {
	}

	public static String byteArrayToString(
			final byte[] bytes) {

		final byte[] encodedBytes = Base64.getEncoder().encode(bytes);
		return new String(encodedBytes, StandardCharsets.UTF_8);
	}

	public static byte[] parseByteArray(
			final String str) {

		final byte[] encodedBytes = str.getBytes(StandardCharsets.UTF_8);
		return Base64.getDecoder().decode(encodedBytes);
	}
}
