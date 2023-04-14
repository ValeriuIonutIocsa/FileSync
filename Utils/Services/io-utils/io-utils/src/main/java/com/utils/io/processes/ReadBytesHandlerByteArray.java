package com.utils.io.processes;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import com.utils.string.StrUtils;

public class ReadBytesHandlerByteArray implements ReadBytesHandler {

	private final ByteArrayOutputStream byteArrayOutputStream;

	public ReadBytesHandlerByteArray() {

		byteArrayOutputStream = new ByteArrayOutputStream();
	}

	@Override
	public void handleReadByte(
			final int intByte) {
		byteArrayOutputStream.write(intByte);
	}

	public String getString(
			final Charset charset) {

		final byte[] bytes = getBytes();
		return new String(bytes, charset);
	}

	public byte[] getBytes() {
		return byteArrayOutputStream.toByteArray();
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
