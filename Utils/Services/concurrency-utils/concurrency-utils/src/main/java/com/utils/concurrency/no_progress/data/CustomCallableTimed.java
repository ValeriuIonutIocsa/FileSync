package com.utils.concurrency.no_progress.data;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;

public class CustomCallableTimed extends AbstractCustomCallable {

	private final int timeout;
	private Future<?> future;

	public CustomCallableTimed(
			final Runnable runnable,
			final int timeout) {

		super(runnable);

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
