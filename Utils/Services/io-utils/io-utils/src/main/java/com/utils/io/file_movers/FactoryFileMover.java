package com.utils.io.file_movers;

import com.utils.annotations.ApiMethod;

public final class FactoryFileMover {

	private static FileMover instance = new FileMoverImpl();

	private FactoryFileMover() {
	}

	@ApiMethod
	public static FileMover getInstance() {
		return instance;
	}

	@ApiMethod
	public static void setInstance(
			final FileMover instance) {
		FactoryFileMover.instance = instance;
	}
}
