package com.utils.env;

import com.utils.annotations.ApiMethod;

public final class FactoryExporterEnvironmentVariables {

	private FactoryExporterEnvironmentVariables() {
	}

	@ApiMethod
	public static ExporterEnvironmentVariables newInstance(
			final String outputPathString) {

		return new ExporterEnvironmentVariablesWindows(outputPathString);
	}
}
