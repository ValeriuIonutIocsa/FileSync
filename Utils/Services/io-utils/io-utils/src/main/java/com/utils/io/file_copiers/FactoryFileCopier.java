package com.utils.io.file_copiers;

import com.utils.annotations.ApiMethod;

public final class FactoryFileCopier {

	private static FileCopier instance = new FileCopierImpl();

	private FactoryFileCopier() {
	}

	@ApiMethod
	public static FileCopier getInstance() {
		return instance;
	}

	@ApiMethod
	public static void setInstance(
			final FileCopier instance) {
		FactoryFileCopier.instance = instance;
	}
}
