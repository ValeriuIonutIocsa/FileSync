package com.utils.io.file_creators;

import com.utils.annotations.ApiMethod;

public interface FileCreator {

	@ApiMethod
	boolean createFile(
			String filePathString,
			boolean verbose);
}
