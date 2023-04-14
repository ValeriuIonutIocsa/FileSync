package com.utils.concurrency.no_progress;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.utils.concurrency.no_progress.data.CustomCallable;
import com.utils.concurrency.no_progress.data.CustomCallableRegular;
import com.utils.log.Logger;

public class ConcurrencyUtilsSimpleRegular extends AbstractConcurrencyUtilsSimple {

	public ConcurrencyUtilsSimpleRegular(
			final int threadCount) {
		super(threadCount);
	}

	@Override
	protected void printInitMessages() {

		final String threadCountDisplayString = createThreadCountDisplayString();
		Logger.printLine("(number of threads: " + threadCountDisplayString + ")");
	}

	@Override
	protected void submitCallable(
			final Runnable runnable,
			final ExecutorService executorService,
			final List<Future<?>> futureList) {

		final CustomCallable customCallable = new CustomCallableRegular(runnable);
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
