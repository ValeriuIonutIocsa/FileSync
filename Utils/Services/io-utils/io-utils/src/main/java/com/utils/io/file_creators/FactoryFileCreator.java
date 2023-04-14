package com.utils.io.file_creators;

import com.utils.annotations.ApiMethod;

public final class FactoryFileCreator {

	private static FileCreator instance = new FileCreatorImpl();

	private FactoryFileCreator() {
	}

	@ApiMethod
	public static FileCreator getInstance() {
		return instance;
	}

	@ApiMethod
	public static void setInstance(
			final FileCreator instance) {
		FactoryFileCreator.instance = instance;
	}
}
