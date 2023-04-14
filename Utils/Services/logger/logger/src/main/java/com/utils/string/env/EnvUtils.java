package com.utils.string.env;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.utils.string.junit.JUnitUtils;

public final class EnvUtils {

	private static final Pattern ENVIRONMENT_VALUE_PATTERN = Pattern.compile("%[^%]+?%");
	private static final EnvProvider ENV_PROVIDER = createEnvProvider();

	private EnvUtils() {
	}

	private static EnvProvider createEnvProvider() {

		final EnvProvider envProvider;
		final boolean jUnitTest = JUnitUtils.isJUnitTest();
		if (jUnitTest) {
			envProvider = new EnvProviderTest();
		} else {
			envProvider = new EnvProviderSystem();
		}
		return envProvider;
	}

	public static String replaceEnvironmentVariables(
			final String strParam) {

		final StringBuilder stringBuilder = new StringBuilder();

		String str = strParam;
		final int length = str.length();
		char lastCh = 0;
		for (int i = 0; i < length; i++) {

			final char ch = str.charAt(i);
			if (lastCh == '%' && ch == '%') {
				lastCh = 0;
				continue;
			}

			stringBuilder.append(ch);
			lastCh = ch;
		}
		str = stringBuilder.toString();

		stringBuilder.setLength(0);
		int remainingStart = 0;
		final Matcher matcher = ENVIRONMENT_VALUE_PATTERN.matcher(str);
		while (matcher.find()) {

			final int start = matcher.start();
			final int end = matcher.end();
			final String environmentVariable = str.substring(start + 1, end - 1);
			final String environmentVariableValue = getEnv(environmentVariable);
			if (environmentVariableValue != null) {
				stringBuilder.append(str, remainingStart, start).append(environmentVariableValue);
				remainingStart = end;
			}
		}
		stringBuilder.append(str.substring(remainingStart));
		return stringBuilder.toString();
	}

	public static String getEnv(
			final String name) {
		return ENV_PROVIDER.getEnv(name);
	}
}
