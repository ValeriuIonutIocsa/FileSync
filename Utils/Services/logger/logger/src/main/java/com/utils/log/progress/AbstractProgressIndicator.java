package com.utils.log.progress;

public abstract class AbstractProgressIndicator implements ProgressIndicator {

	@Override
	public final void update(
			final int count,
			final int total) {

		final double value = (double) count / total;
		update(value);
	}
}
