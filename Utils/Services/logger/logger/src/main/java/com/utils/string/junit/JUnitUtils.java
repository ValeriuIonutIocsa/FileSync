package com.utils.string.junit;

import com.utils.annotations.ApiMethod;

public final class JUnitUtils {

	private JUnitUtils() {
	}

	@ApiMethod
	public static boolean isJUnitTest() {

		boolean jUnitTest = false;
		final StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
		for (final StackTraceElement stackTraceElement : stackTraceElementArray) {

			final String clsName = stackTraceElement.getClassName();
			if (clsName.startsWith("org.junit.")) {

				jUnitTest = true;
				break;
			}
		}
		return jUnitTest;
	}
}
