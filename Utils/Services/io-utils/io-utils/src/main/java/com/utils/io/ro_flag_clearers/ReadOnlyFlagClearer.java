package com.utils.io.ro_flag_clearers;

import com.utils.annotations.ApiMethod;

public interface ReadOnlyFlagClearer {

	/**
	 * If file exist, clears the readonly flag of that file.
	 *
	 * @param filePathString
	 *            The path to the file.
	 * @param verboseProgress
	 *            Enables verbose progress logging.
	 * @param verboseError
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean clearReadOnlyFlagFile(
			String filePathString,
			boolean verboseProgress,
			boolean verboseError);

	/**
	 * Clears the readonly flag of a file.
	 *
	 * @param filePathString
	 *            The path to the file.
	 * @param verboseProgress
	 *            Enables verbose progress logging.
	 * @param verboseError
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean clearReadOnlyFlagFileNoChecks(
			String filePathString,
			boolean verboseProgress,
			boolean verboseError);

	/**
	 * If folder exists, clears the readonly flags of all files in that folder.
	 *
	 * @param folderPathString
	 *            The path to the folder.
	 * @param verboseProgress
	 *            Enables verbose progress logging.
	 * @param verboseError
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean clearReadOnlyFlagFolder(
			String folderPathString,
			boolean verboseProgress,
			boolean verboseError);

	/**
	 * Clears the readonly flags of all files inside a folder.
	 * 
	 * @param folderPathString
	 *            The path to the folder.
	 * @param verboseProgress
	 *            Enables verbose progress logging.
	 * @param verboseError
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean clearReadOnlyFlagFolderNoChecks(
			String folderPathString,
			boolean verboseProgress,
			boolean verboseError);
}
