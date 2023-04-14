package com.utils.concurrency.no_progress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.utils.concurrency.AbstractConcurrencyUtils;
import com.utils.log.Logger;

public abstract class AbstractConcurrencyUtilsSimple extends AbstractConcurrencyUtils {

	protected AbstractConcurrencyUtilsSimple(
			final int threadCount) {

		super(threadCount);
	}

	@Override
	public void executeMultiThreadedTask(
			final List<Runnable> runnableList) {

		if (!runnableList.isEmpty()) {

			printInitMessages();

			final ExecutorService executorService;
			final int threadCount = getThreadCount();
			if (threadCount <= 0) {
				executorService = Executors.newCachedThreadPool();
			} else {
				executorService = Executors.newFixedThreadPool(threadCount);
			}

			final List<Future<?>> futureList = new ArrayList<>();
			for (final Runnable runnable : runnableList) {
				submitCallable(runnable, executorService, futureList);
			}

			for (final Future<?> future : futureList) {
				futureGet(future);
			}

			executorService.shutdown();

			boolean awaitTerminationSuccess = false;
			try {
				awaitTerminationSuccess = executorService.awaitTermination(10, TimeUnit.SECONDS);

			} catch (final Exception exc) {
				Logger.printException(exc);

			} finally {
				if (!awaitTerminationSuccess) {
					Logger.printError("failed to await termination of multi-threaded tasks");
				}
			}
		}
	}

	protected abstract void submitCallable(
			Runnable runnable,
			ExecutorService executorService,
			List<Future<?>> futureList);
}
