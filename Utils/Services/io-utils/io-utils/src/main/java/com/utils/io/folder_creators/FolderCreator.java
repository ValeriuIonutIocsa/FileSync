package com.utils.io.folder_creators;

import com.utils.annotations.ApiMethod;

public interface FolderCreator {

	@ApiMethod
	boolean createParentDirectories(
			String filePathString,
			boolean verboseProgress,
			boolean verboseError);

	@ApiMethod
	boolean createParentDirectoriesNoCheck(
			String filePathString,
			boolean verboseProgress,
			boolean verboseError);

	@ApiMethod
	boolean createDirectories(
			String directoryPathString,
			boolean verboseProgress,
			boolean verboseError);

	@ApiMethod
	boolean createDirectoriesNoCheck(
			String directoryPathString,
			boolean verboseProgress,
			boolean verboseError);

	@ApiMethod
	boolean createDirectory(
			String directoryPathString,
			boolean verboseProgress,
			boolean verboseError);

	@ApiMethod
	boolean createDirectoryNoChecks(
			String directoryPathString,
			boolean verboseProgress,
			boolean verboseError);
}
