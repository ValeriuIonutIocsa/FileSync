package com.utils.io.progress.listeners;

import com.utils.log.Logger;
import com.utils.string.StrUtils;

public class ProgressListenerConsole implements ProgressListener {

	private int percentageInt;

	public ProgressListenerConsole() {

		percentageInt = -1;
	}

	@Override
	public void transferred(
			final long totalRead,
			final long totalLength) {

		final double ratio = (double) totalRead / totalLength;
		final double percentage = 100.0 * ratio;
		final int tmpPercentageInt = (int) Math.floor(percentage);
		if (tmpPercentageInt > percentageInt) {

			percentageInt = tmpPercentageInt;
			Logger.printLine("completed " + StrUtils.doubleToPercentageString(ratio, 2));
		}
	}
}
