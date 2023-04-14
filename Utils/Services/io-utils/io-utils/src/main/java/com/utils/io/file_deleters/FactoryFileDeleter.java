package com.utils.io.file_deleters;

import com.utils.annotations.ApiMethod;

public final class FactoryFileDeleter {

	private static FileDeleter instance = new FileDeleterImpl();

	private FactoryFileDeleter() {
	}

	@ApiMethod
	public static FileDeleter getInstance() {
		return instance;
	}

	@ApiMethod
	public static void setInstance(
			final FileDeleter instance) {
		FactoryFileDeleter.instance = instance;
	}
}
