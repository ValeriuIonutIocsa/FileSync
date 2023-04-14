package com.utils.concurrency.progress.data;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomCallableShowProgressTimed extends AbstractCustomCallableShowProgress {

	private final int timeout;
	private Future<?> future;

	public CustomCallableShowProgressTimed(
			final Runnable runnable,
			final AtomicInteger completedRunnablesCount,
			final int runnableCount,
			final int showProgressInterval,
			final int timeout) {

		super(runnable, completedRunnablesCount, runnableCount, showProgressInterval);

		this.timeout = timeout;
	}

	@Override
	protected void work() {

		final Timer cancelTimer = new Timer();
		cancelTimer.schedule(new TimerTask() {

			@Override
			public void run() {

				if (future != null) {
					future.cancel(true);
				}
			}
		}, timeout);

		final Runnable runnable = getRunnable();
		runnable.run();
		cancelTimer.cancel();
	}

	public void setFuture(
			final Future<?> future) {
		this.future = future;
	}
}
