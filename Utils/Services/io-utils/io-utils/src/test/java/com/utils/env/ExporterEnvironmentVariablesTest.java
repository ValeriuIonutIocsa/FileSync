package com.utils.env;

import org.junit.jupiter.api.Test;

import com.utils.io.PathUtils;
import com.utils.string.StrUtils;

class ExporterEnvironmentVariablesTest {

	@Test
	void testWork() {

		final String outputPathString =
				PathUtils.computePath(PathUtils.createRootPath(), "env_" + StrUtils.createDateTimeString() + ".txt");

		final ExporterEnvironmentVariables exporterEnvironmentVariables =
				FactoryExporterEnvironmentVariables.newInstance(outputPathString);
		exporterEnvironmentVariables.work();
	}
}
