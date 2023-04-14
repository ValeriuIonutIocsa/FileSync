package com.utils.concurrency;

import java.util.concurrent.Future;

import com.utils.string.StrUtils;

public abstract class AbstractConcurrencyUtils implements ConcurrencyUtils {

	private final int threadCount;

	protected AbstractConcurrencyUtils(
			final int threadCount) {

		this.threadCount = threadCount;
	}

	protected abstract void printInitMessages();

	protected abstract void futureGet(
			Future<?> future);

	protected String createThreadCountDisplayString() {

		final String threadCountDisplayString;
		if (threadCount > 0) {
			threadCountDisplayString = StrUtils.positiveIntToString(threadCount, true);
		} else {
			threadCountDisplayString = "unlimited";
		}
		return threadCountDisplayString;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	protected int getThreadCount() {
		return threadCount;
	}
}
