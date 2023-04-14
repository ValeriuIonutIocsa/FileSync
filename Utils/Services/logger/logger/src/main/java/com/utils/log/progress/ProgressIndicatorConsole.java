package com.utils.log.progress;

import com.utils.log.Logger;
import com.utils.string.StrUtils;

public final class ProgressIndicatorConsole extends AbstractProgressIndicator {

	public static final ProgressIndicatorConsole INSTANCE = new ProgressIndicatorConsole();

	private ProgressIndicatorConsole() {
	}

	@Override
	public void update(
			final double value) {
		Logger.printStatus("done " + StrUtils.doubleToPercentageString(value, 2));
	}
}
