package com.utils.io.processes;

import java.util.ArrayList;
import java.util.List;

import com.utils.log.Logger;

public class ReadBytesHandlerLinesCollectAndPrint extends ReadBytesHandlerLines {

	private final List<String> lineList;

	public ReadBytesHandlerLinesCollectAndPrint() {

		lineList = new ArrayList<>();
	}

	@Override
	protected void handleLine(
			final String line) {

		Logger.printLine(line);
		lineList.add(line);
	}

	public List<String> getLineList() {
		return lineList;
	}
}
