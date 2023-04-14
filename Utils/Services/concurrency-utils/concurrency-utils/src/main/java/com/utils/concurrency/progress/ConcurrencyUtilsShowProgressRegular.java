package com.utils.concurrency.progress;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import com.utils.concurrency.no_progress.data.CustomCallable;
import com.utils.concurrency.progress.data.CustomCallableShowProgressRegular;
import com.utils.log.Logger;

public class ConcurrencyUtilsShowProgressRegular extends AbstractConcurrencyUtilsShowProgress {

	public ConcurrencyUtilsShowProgressRegular(
			final int threadCount,
			final int showProgressInterval) {
		super(threadCount, showProgressInterval);
	}

	@Override
	protected void printInitMessages() {

		final String threadCountDisplayString = createThreadCountDisplayString();
		Logger.printLine("(number of threads: " + threadCountDisplayString + ")");
	}

	@Override
	void submitCallable(
			final Runnable runnable,
			final ExecutorService executorService,
			final List<Future<?>> futureList,
			final AtomicInteger completedRunnablesCount,
			final int runnableCount,
			final int showProgressInterval) {

		final CustomCallable customCallable = new CustomCallableShowProgressRegular(
				runnable, completedRunnablesCount, runnableCount, showProgressInterval);
		final Future<Void> future = executorService.submit(customCallable);
		futureList.add(future);
	}

	@Override
	protected void futureGet(
			final Future<?> future) {

		try {
			future.get();
		} catch (final Exception ignored) {
		}
	}
}
