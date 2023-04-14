package com.utils.io.folder_copiers;

import com.utils.annotations.ApiMethod;

public final class FactoryFolderCopier {

	private static FolderCopier instance = new FolderCopierImpl();

	private FactoryFolderCopier() {
	}

	@ApiMethod
	public static FolderCopier getInstance() {
		return instance;
	}

	@ApiMethod
	public static void setInstance(
			final FolderCopier instance) {
		FactoryFolderCopier.instance = instance;
	}
}
