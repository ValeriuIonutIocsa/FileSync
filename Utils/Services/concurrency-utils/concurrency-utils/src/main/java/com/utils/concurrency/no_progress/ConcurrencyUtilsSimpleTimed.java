package com.utils.concurrency.no_progress;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.utils.concurrency.no_progress.data.CustomCallableTimed;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

public class ConcurrencyUtilsSimpleTimed extends AbstractConcurrencyUtilsSimple {

	private final int timeout;

	/**
	 * @param threadCount
	 *            the maximum number of threads that will run in parallel
	 * @param timeout
	 *            the duration in ms after which a task will be aborted
	 */
	public ConcurrencyUtilsSimpleTimed(
			final int threadCount,
			final int timeout) {

		super(threadCount);

		this.timeout = timeout;
	}

	@Override
	protected void printInitMessages() {

		final String threadCountDisplayString = createThreadCountDisplayString();
		Logger.printLine("(number of threads: " + threadCountDisplayString + ")");

		Logger.printLine("(timeout: " + StrUtils.timeMsToString(timeout) + ")");
	}

	@Override
	protected void submitCallable(
			final Runnable runnable,
			final ExecutorService executorService,
			final List<Future<?>> futureList) {

		final CustomCallableTimed customCallableTimed =
				new CustomCallableTimed(runnable, timeout);
		final Future<Void> future = executorService.submit(customCallableTimed);
		customCallableTimed.setFuture(future);
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
