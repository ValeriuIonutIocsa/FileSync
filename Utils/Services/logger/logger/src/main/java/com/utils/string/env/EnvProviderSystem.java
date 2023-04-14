package com.utils.string.env;

public class EnvProviderSystem implements EnvProvider {

	@Override
	public String getEnv(
			final String name) {
		return System.getenv(name);
	}
}
