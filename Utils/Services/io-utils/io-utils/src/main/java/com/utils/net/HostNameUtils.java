package com.utils.net;

import java.io.InputStream;
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
			final InputStream inputStream = process.getInputStream();
			final ReadBytesHandlerByteArray readBytesHandlerByteArray = new ReadBytesHandlerByteArray();
			new InputStreamReaderThread("host name finder", inputStream,
					Charset.defaultCharset(), readBytesHandlerByteArray).start();
			process.waitFor();
			hostName = readBytesHandlerByteArray.getString(Charset.defaultCharset());

		} catch (final Exception ignored) {
		}
		return hostName;
	}
}
