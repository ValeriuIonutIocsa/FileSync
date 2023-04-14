package com.utils.io.file_deleters;

import com.utils.annotations.ApiMethod;

public interface FileDeleter {

	@ApiMethod
	boolean deleteFile(
			String filePathString,
			boolean verbose);

	@ApiMethod
	boolean deleteFileNoChecks(
			String filePathString,
			boolean verbose);
}
