package com.utils.io.folder_creators;

import com.utils.annotations.ApiMethod;

public interface FolderCreator {

	@ApiMethod
	boolean createParentDirectories(
			String filePathString,
			boolean verbose);

    @ApiMethod
    boolean createParentDirectoriesNoCheck(
            String filePathString,
            boolean verbose);

	@ApiMethod
	boolean createDirectories(
			String directoryPathString,
			boolean verbose);

	@ApiMethod
	boolean createDirectoriesNoCheck(
			String directoryPathString,
			boolean verbose);

	@ApiMethod
	boolean createDirectory(
			String directoryPathString,
			boolean verbose);

	@ApiMethod
	boolean createDirectoryNoChecks(
			String directoryPathString,
			boolean verbose);
}
