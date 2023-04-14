package com.utils.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import com.utils.concurrency.no_progress.ConcurrencyUtilsSimpleRegular;
import com.utils.concurrency.no_progress.ConcurrencyUtilsSimpleTimed;
import com.utils.concurrency.progress.ConcurrencyUtilsShowProgressRegular;
import com.utils.concurrency.progress.ConcurrencyUtilsShowProgressTimed;
import com.utils.log.Logger;
import com.utils.log.progress.ProgressIndicatorConsole;
import com.utils.log.progress.ProgressIndicators;
import com.utils.string.StrUtils;

class ConcurrencyUtilsTest {

	@Test
	void testExecuteMultiThreadedTask() {

		final ConcurrencyUtils concurrencyUtils;
		final int input = Integer.parseInt("1");
		if (input == 1) {
			concurrencyUtils = new ConcurrencyUtilsSimpleRegular(16);
		} else if (input == 2) {
			concurrencyUtils = new ConcurrencyUtilsSimpleTimed(16, 500);

		} else if (input == 11) {
			ProgressIndicators.setInstance(ProgressIndicatorConsole.INSTANCE);
			concurrencyUtils = new ConcurrencyUtilsShowProgressRegular(16, 1);
		} else if (input == 12) {
			ProgressIndicators.setInstance(ProgressIndicatorConsole.INSTANCE);
			concurrencyUtils = new ConcurrencyUtilsShowProgressTimed(16, 1, 500);

		} else {
			throw new RuntimeException();
		}

		final List<Runnable> runnableList = new ArrayList<>();
		final Random random = new Random();
		for (int i = 0; i < 10; i++) {

			final int index = i;
			runnableList.add(() -> {

				final long startTime = System.currentTimeMillis();
				try {
					final int runTime = (int) (random.nextDouble() * 1000);
					Logger.printLine("run time for task " + index + ": " + StrUtils.timeMsToString(runTime));

					Thread.sleep(runTime);

					final long executionTime = System.currentTimeMillis() - startTime;
					Logger.printLine("task " + index + " done in " + StrUtils.timeMsToString(executionTime));

				} catch (final InterruptedException e) {
					final long executionTime = System.currentTimeMillis() - startTime;
					Logger.printLine("task " + index + " aborted after " + StrUtils.timeMsToString(executionTime));
				}
			});
		}

		Logger.printProgress("running concurrency utils test");
		concurrencyUtils.executeMultiThreadedTask(runnableList);
	}
}
