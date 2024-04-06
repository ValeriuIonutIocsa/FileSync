package com.utils.io.processes;

import com.utils.log.Logger;

public class ReadBytesHandlerLinesPrint extends AbstractReadBytesHandlerLines {

	@Override
	protected void handleLine(
			final String line) {

		Logger.printLine(line);
	}
}
