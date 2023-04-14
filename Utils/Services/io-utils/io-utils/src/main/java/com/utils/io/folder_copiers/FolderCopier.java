package com.utils.io.folder_copiers;

import com.utils.annotations.ApiMethod;

public interface FolderCopier {

	@ApiMethod
	void copyFolder(
			String srcFolderPathString,
			String dstFolderPathString,
			boolean verbose);
}
