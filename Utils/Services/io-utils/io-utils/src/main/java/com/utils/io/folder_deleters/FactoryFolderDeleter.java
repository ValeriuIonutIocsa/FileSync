package com.utils.io.folder_deleters;

import com.utils.annotations.ApiMethod;

public final class FactoryFolderDeleter {

	private static FolderDeleter instance = new FolderDeleterImpl();

	private FactoryFolderDeleter() {
	}

	@ApiMethod
	public static FolderDeleter getInstance() {
		return instance;
	}

	@ApiMethod
	public static void setInstance(
			final FolderDeleter instance) {
		FactoryFolderDeleter.instance = instance;
	}
}
