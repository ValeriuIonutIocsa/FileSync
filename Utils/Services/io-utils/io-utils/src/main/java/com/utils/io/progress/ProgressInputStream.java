package com.utils.io.progress;

import java.io.IOException;
import java.io.InputStream;

import com.utils.io.progress.listeners.ProgressListener;

public class ProgressInputStream extends InputStream {

	private final InputStream inputStream;
	private final long totalLength;
	private final ProgressListener progressListener;

	private long totalReadByteCount;

	public ProgressInputStream(
			final InputStream inputStream,
			final long totalLength,
			final ProgressListener progressListener) {

		this.inputStream = inputStream;
		this.totalLength = totalLength;
		this.progressListener = progressListener;
	}

	@Override
	public int read(
			final byte[] b) throws IOException {

		final int readCount = inputStream.read(b);
		evaluatePercent(readCount);
		return readCount;
	}

	@Override
	public int read(
			final byte[] b,
			final int off,
			final int len) throws IOException {

		final int readCount = inputStream.read(b, off, len);
		evaluatePercent(readCount);
		return readCount;
	}

	@Override
	public long skip(
			final long n) throws IOException {

		final long skip = inputStream.skip(n);
		evaluatePercent(skip);
		return skip;
	}

	@Override
	public int read() throws IOException {

		final int read = inputStream.read();
		if (read != -1) {
			evaluatePercent(1);
		}
		return read;
	}

	private void evaluatePercent(
			final long readCount) {

		if (readCount != -1) {
			totalReadByteCount += readCount;
		}
		progressListener.transferred(totalReadByteCount, totalLength);
	}
}
