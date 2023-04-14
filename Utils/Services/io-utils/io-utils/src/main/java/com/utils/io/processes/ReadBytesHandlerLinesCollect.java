package com.utils.io.processes;

import java.util.ArrayList;
import java.util.List;

public class ReadBytesHandlerLinesCollect extends ReadBytesHandlerLines {

	private final List<String> lineList;

	public ReadBytesHandlerLinesCollect() {

		lineList = new ArrayList<>();
	}

	@Override
	protected void handleLine(
			final String line) {
		lineList.add(line);
	}

	public List<String> getLineList() {
		return lineList;
	}
}
