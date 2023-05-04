package com.utils.io.processes;

import com.utils.string.StrUtils;

public abstract class AbstractReadBytesHandlerLines implements ReadBytesHandler {

	private final StringBuilder stringBuilder;

	protected AbstractReadBytesHandlerLines() {

		stringBuilder = new StringBuilder();
	}

	@Override
	public void handleReadByte(
			final int intByte) {

		final char ch = (char) intByte;
		if (ch == '\n') {
			final String line = stringBuilder.toString();
			handleLine(line);
			stringBuilder.setLength(0);

		} else if (ch != '\r') {
			stringBuilder.append(ch);
		}
	}

	protected abstract void handleLine(
			String line);

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
