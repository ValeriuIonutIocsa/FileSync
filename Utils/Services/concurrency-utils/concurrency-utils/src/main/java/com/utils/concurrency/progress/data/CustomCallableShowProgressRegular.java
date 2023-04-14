package com.utils.concurrency.progress.data;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomCallableShowProgressRegular extends AbstractCustomCallableShowProgress {

	public CustomCallableShowProgressRegular(
			final Runnable runnable,
			final AtomicInteger completedRunnablesCount,
			final int runnableCount,
			final int showProgressInterval) {
		super(runnable, completedRunnablesCount, runnableCount, showProgressInterval);
	}

	@Override
	protected void work() {

		final Runnable runnable = getRunnable();
		runnable.run();
	}
}
