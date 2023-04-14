package com.utils.log.progress;

public final class ProgressIndicators {

	private static ProgressIndicator instance = ProgressIndicatorNoOp.INSTANCE;

	private ProgressIndicators() {
	}

	public static void setInstance(
			final ProgressIndicator instance) {
		ProgressIndicators.instance = instance;
	}

	public static ProgressIndicator getInstance() {
		return instance;
	}
}
