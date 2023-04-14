package com.utils.log.progress;

public interface ProgressIndicator {

	void update(
			int count,
			int total);

	void update(
			double value);
}
