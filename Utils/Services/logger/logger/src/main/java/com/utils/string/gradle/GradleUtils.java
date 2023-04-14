package com.utils.string.gradle;

import com.utils.annotations.ApiMethod;

public final class GradleUtils {

	private GradleUtils() {
	}

	@ApiMethod
	public static boolean isGradle() {

		boolean gradleTest = false;
		final StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
		for (final StackTraceElement stackTraceElement : stackTraceElementArray) {

			final String clsName = stackTraceElement.getClassName();
			if (clsName.startsWith("org.gradle.")) {

				gradleTest = true;
				break;
			}
		}
		return gradleTest;
	}
}
