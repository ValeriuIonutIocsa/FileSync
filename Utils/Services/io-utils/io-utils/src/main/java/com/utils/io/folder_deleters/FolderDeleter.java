package com.utils.io.folder_deleters;

import com.utils.annotations.ApiMethod;

public interface FolderDeleter {

	/**
	 * If folder exists, deletes the folder and all of its contents.
	 *
	 * @param folderPathString
	 *            The path to the folder.
	 * @param verboseProgress
	 * @param verboseError
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean deleteFolder(
			String folderPathString,
			boolean verboseProgress,
			boolean verboseError);

	/**
	 * Deletes a folder and all of its contents.
	 *
	 * @param folderPathString
	 *            The path to the folder.
	 * @param verboseProgress
	 * @param verboseError
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean deleteFolderNoChecks(
			String folderPathString,
			boolean verboseProgress,
			boolean verboseError);

	/**
	 * If folder exists, deletes everything inside the folder but not the folder itself.
	 *
	 * @param folderPathString
	 *            The path to the folder.
	 * @param verboseProgress
	 * @param verboseError
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean cleanFolder(
			String folderPathString,
			boolean verboseProgress,
			boolean verboseError);

	/**
	 * Deletes everything inside a folder but not the folder itself.
	 *
	 * @param folderPathString
	 *            The path to the folder.
	 * @param verboseProgress
	 * @param verboseError
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean cleanFolderNoChecks(
			String folderPathString,
			boolean verboseProgress,
			boolean verboseError);
}
