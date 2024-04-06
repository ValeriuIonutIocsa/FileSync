package com.utils.log.progress;

public final class ProgressIndicatorNoOp implements ProgressIndicator {

	public static final ProgressIndicatorNoOp INSTANCE = new ProgressIndicatorNoOp();

	private ProgressIndicatorNoOp() {
		super();
	}

	@Override
	public void update(
			final double value) {
	}
}
