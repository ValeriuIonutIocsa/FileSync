package com.utils.io.file_movers;

import com.utils.annotations.ApiMethod;

public interface FileMover {

	@ApiMethod
	boolean moveFile(
			String srcFilePathString,
			String dstFilePathString,
			boolean verboseProgress,
			boolean verboseError);
}
