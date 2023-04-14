package com.utils.concurrency.no_progress.data;

public class CustomCallableRegular extends AbstractCustomCallable {

	public CustomCallableRegular(
			final Runnable runnable) {
		super(runnable);
	}

	@Override
	protected void work() {

		final Runnable runnable = getRunnable();
		runnable.run();
	}
}
