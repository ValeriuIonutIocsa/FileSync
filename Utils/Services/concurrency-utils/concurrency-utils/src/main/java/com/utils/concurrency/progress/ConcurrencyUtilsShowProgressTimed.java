package com.utils.concurrency.progress;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import com.utils.concurrency.progress.data.CustomCallableShowProgressTimed;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

public class ConcurrencyUtilsShowProgressTimed extends AbstractConcurrencyUtilsShowProgress {

	private final int timeout;

	/**
	 * @param threadCount
	 *            the maximum number of threads that will run in parallel
	 * @param timeout
	 *            the duration in ms after which a task will be aborted
	 */
	public ConcurrencyUtilsShowProgressTimed(
			final int threadCount,
			final int showProgressInterval,
			final int timeout) {

		super(threadCount, showProgressInterval);

		this.timeout = timeout;
	}

	@Override
    protected void printInitMessages() {

		final String threadCountDisplayString = createThreadCountDisplayString();
		Logger.printLine("(number of threads: " + threadCountDisplayString + ")");

		Logger.printLine("(timeout: " + StrUtils.timeMsToString(timeout) + ")");
	}

	@Override
	void submitCallable(
			final Runnable runnable,
			final ExecutorService executorService,
			final List<Future<?>> futureList,
			final AtomicInteger completedRunnablesCount,
			final int runnableCount,
			final int showProgressInterval) {

		final CustomCallableShowProgressTimed customCallableTimed = new CustomCallableShowProgressTimed(
				runnable, completedRunnablesCount, runnableCount, showProgressInterval, timeout);
		final Future<Void> future = executorService.submit(customCallableTimed);
		customCallableTimed.setFuture(future);
		futureList.add(future);
	}

	@Override
	protected void futureGet(
			final Future<?> future) {

		try {
			future.get();
		} catch (final ExecutionException | InterruptedException | CancellationException ignored) {
		}
	}
}
