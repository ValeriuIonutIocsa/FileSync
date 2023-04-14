package com.utils.string.env;

import java.util.HashMap;
import java.util.Map;

public class EnvProviderTest implements EnvProvider {

	private static final Map<String, String> ENV = new HashMap<>();

	public static void setEnv(
			final String name,
			final String value) {

		ENV.put(name, value);
	}

	@Override
	public String getEnv(
			final String name) {

		return ENV.get(name);
	}
}
