package com.utils.concurrency;

import com.utils.annotations.ApiMethod;

public final class ThreadUtils {

	private ThreadUtils() {
	}

	/**
	 * @param millis
	 *            amount of sleep time in milliseconds
	 * @return true if the thread was interrupted while sleeping, false otherwise
	 */
	@ApiMethod
	public static boolean trySleep(
			final long millis) {

		boolean interrupted = false;
		try {
			if (millis > 0) {
				Thread.sleep(millis);
			}
		} catch (final InterruptedException exc) {
			interrupted = true;
		}
		return interrupted;
	}
}
