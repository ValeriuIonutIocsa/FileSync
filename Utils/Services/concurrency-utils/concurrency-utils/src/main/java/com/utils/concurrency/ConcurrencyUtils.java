package com.utils.concurrency;

import java.util.List;

public interface ConcurrencyUtils {

	void executeMultiThreadedTask(
			List<Runnable> runnableList);
}
