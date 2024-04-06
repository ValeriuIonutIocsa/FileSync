package com.utils.inst;

import org.github.jamm.MemoryMeter;

import com.utils.log.Logger;
import com.utils.string.size.SizeUtils;

public final class InstrumentationUtils {

	private InstrumentationUtils() {
	}

	public static void printObjectMemoryUsage(
			final Object object) {

		if (object == null) {
			Logger.printWarning("used memory cannot be computed because object is null");

		} else {
			final String classSimpleName = object.getClass().getSimpleName();
			try {
				final MemoryMeter memoryMeter = MemoryMeter.builder().build();
				final long objectSize = memoryMeter.measureDeep(object);
				Logger.printWarning(classSimpleName + " used memory: " +
						SizeUtils.humanReadableByteCountBin(objectSize));

			} catch (final Exception exc) {
				Logger.printWarning("failed to compute " + classSimpleName + " used memory");
				Logger.printException(exc);
			}
		}
	}
}
