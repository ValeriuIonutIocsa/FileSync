package com.utils.io.folder_creators;

import com.utils.annotations.ApiMethod;

public final class FactoryFolderCreator {

	private static FolderCreator instance = new FolderCreatorImpl();

	private FactoryFolderCreator() {
	}

	@ApiMethod
	public static FolderCreator getInstance() {
		return instance;
	}

	@ApiMethod
	public static void setInstance(
			final FolderCreator instance) {
		FactoryFolderCreator.instance = instance;
	}
}
