package com.utils.concurrency.no_progress.data;

public abstract class AbstractCustomCallable implements CustomCallable {

	private final Runnable runnable;

	protected AbstractCustomCallable(
			final Runnable runnable) {

		this.runnable = runnable;
	}

	@Override
	public Void call() {

		work();

		return null;
	}

	protected abstract void work();

	protected Runnable getRunnable() {
		return runnable;
	}
}
