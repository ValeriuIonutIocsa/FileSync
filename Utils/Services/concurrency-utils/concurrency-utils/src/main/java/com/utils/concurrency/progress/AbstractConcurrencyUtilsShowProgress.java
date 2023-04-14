package com.utils.concurrency.progress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.utils.concurrency.AbstractConcurrencyUtils;
import com.utils.log.Logger;
import com.utils.log.progress.ProgressIndicators;

abstract class AbstractConcurrencyUtilsShowProgress extends AbstractConcurrencyUtils {

	private final int showProgressInterval;

	AbstractConcurrencyUtilsShowProgress(
			final int threadCount,
			final int showProgressInterval) {

		super(threadCount);

		this.showProgressInterval = showProgressInterval;
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
			ProgressIndicators.getInstance().update(0);

			final List<Future<?>> futureList = new ArrayList<>();
			final AtomicInteger completedRunnablesCount = new AtomicInteger(0);
			final int runnableCount = runnableList.size();
			for (final Runnable runnable : runnableList) {
				submitCallable(runnable, executorService, futureList,
						completedRunnablesCount, runnableCount, showProgressInterval);
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
				ProgressIndicators.getInstance().update(0);
			}
		}
	}

	abstract void submitCallable(
			Runnable runnable,
			ExecutorService executorService,
			List<Future<?>> futureList,
			AtomicInteger completedRunnablesCount,
			int runnableCount,
			int showProgressInterval);
}
