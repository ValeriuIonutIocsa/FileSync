package com.utils.log.progress;

public interface ProgressIndicator {

	default void update(
			final int count,
			final int total) {

		final double value = (double) count / total;
		update(value);
	}

	void update(
			double value);
}
