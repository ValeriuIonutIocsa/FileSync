package com.personal.scripts.gen.fs.settings;

import com.utils.string.StrUtils;

public class FolderPair {

	private final String srcFolderPathString;
	private final String dstFolderPathString;

	public FolderPair(
			final String srcFolderPathString,
			final String dstFolderPathString) {

		this.srcFolderPathString = srcFolderPathString;
		this.dstFolderPathString = dstFolderPathString;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public String getSrcFolderPathString() {
		return srcFolderPathString;
	}

	public String getDstFolderPathString() {
		return dstFolderPathString;
	}
}
