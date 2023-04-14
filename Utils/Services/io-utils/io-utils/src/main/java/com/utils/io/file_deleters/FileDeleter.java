package com.utils.io.file_deleters;

import com.utils.annotations.ApiMethod;

public interface FileDeleter {

	@ApiMethod
	boolean deleteFile(
			String filePathString,
			boolean verboseProgress,
			boolean verboseError);

	@ApiMethod
	boolean deleteFileNoChecks(
			String filePathString,
			boolean verboseProgress,
			boolean verboseError);
}
