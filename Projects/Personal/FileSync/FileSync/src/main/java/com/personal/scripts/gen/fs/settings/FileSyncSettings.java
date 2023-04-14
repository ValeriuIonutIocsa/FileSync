package com.personal.scripts.gen.fs.settings;

import java.util.List;

public class FileSyncSettings {

	private final List<FolderPair> folderPairList;

	public FileSyncSettings(
			final List<FolderPair> folderPairList) {

		this.folderPairList = folderPairList;
	}

	public List<FolderPair> getFolderPairList() {
		return folderPairList;
	}
}
