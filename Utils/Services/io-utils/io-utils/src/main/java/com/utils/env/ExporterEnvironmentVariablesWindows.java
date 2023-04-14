package com.utils.env;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.utils.annotations.ApiMethod;
import com.utils.io.StreamUtils;
import com.utils.io.processes.InputStreamReaderThread;
import com.utils.io.processes.ReadBytesHandlerOutputStream;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

public class ExporterEnvironmentVariablesWindows implements ExporterEnvironmentVariables {

	private final String outputPathString;

	ExporterEnvironmentVariablesWindows(
			final String outputPathString) {

		this.outputPathString = outputPathString;
	}

	@ApiMethod
	@Override
	public void work() {

		try (OutputStream outputStream = StreamUtils.openBufferedOutputStream(outputPathString)) {

			final Process process = new ProcessBuilder()
					.command("cmd", "/c", "set")
					.redirectErrorStream(true)
					.start();
			final InputStream inputStream = process.getInputStream();
			final InputStreamReaderThread inputStreamReaderThread = new InputStreamReaderThread(
					"env", inputStream, StandardCharsets.UTF_8, new ReadBytesHandlerOutputStream(outputStream));
			inputStreamReaderThread.start();
			process.waitFor();
			inputStreamReaderThread.join();

		} catch (final Exception exc) {
			Logger.printException(exc);
		}
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
