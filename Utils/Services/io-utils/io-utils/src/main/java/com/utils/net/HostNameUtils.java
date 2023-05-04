package com.utils.net;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.utils.annotations.ApiMethod;
import com.utils.io.processes.InputStreamReaderThread;
import com.utils.io.processes.ReadBytesHandlerByteArray;

public final class HostNameUtils {

	private HostNameUtils() {
	}

	@ApiMethod
	public static String findHostName() {

		String hostName = "";
		try {
			final List<String> commandList = new ArrayList<>();
			commandList.add("cmd");
			commandList.add("/c");
			commandList.add("hostname");
			final Process process = new ProcessBuilder(commandList).start();

			final ReadBytesHandlerByteArray readBytesHandlerByteArray = new ReadBytesHandlerByteArray();
			final InputStreamReaderThread inputStreamReaderThread = new InputStreamReaderThread(
					"host name finder", process.getInputStream(),
					Charset.defaultCharset(), readBytesHandlerByteArray);
			inputStreamReaderThread.start();

			process.waitFor();
			inputStreamReaderThread.join();

			hostName = readBytesHandlerByteArray.getString(Charset.defaultCharset());

		} catch (final Exception ignored) {
		}
		return hostName;
	}
}
