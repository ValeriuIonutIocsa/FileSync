package com.utils.io.processes;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.utils.log.Logger;

public class InputStreamReaderThread extends Thread {

	private final InputStream inputStream;
	private final Charset charset;
	private final ReadBytesHandler readBytesHandler;

	public InputStreamReaderThread(
			final String name,
			final InputStream inputStream,
			final Charset charset,
			final ReadBytesHandler readBytesHandler) {

		super(name);

		this.inputStream = inputStream;
		this.charset = charset;
		this.readBytesHandler = readBytesHandler;
	}

	@Override
	public void run() {

		try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset)) {

			int intByte;
			while ((intByte = inputStreamReader.read()) != -1) {
				readBytesHandler.handleReadByte(intByte);
			}

		} catch (final Exception exc) {
			Logger.printError("error occurred while reading from the input stream");
			Logger.printException(exc);
		}
	}
}
