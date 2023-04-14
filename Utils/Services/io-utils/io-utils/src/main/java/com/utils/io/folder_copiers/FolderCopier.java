package com.utils.io.folder_copiers;

import com.utils.annotations.ApiMethod;

public interface FolderCopier {

	@ApiMethod
	boolean copyFolder(
			String srcFolderPathString,
			String dstFolderPathString,
			boolean verboseProgress,
			boolean verboseError);
}
