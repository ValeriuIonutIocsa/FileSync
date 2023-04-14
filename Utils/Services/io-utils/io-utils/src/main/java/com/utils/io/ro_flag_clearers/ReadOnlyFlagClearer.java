package com.utils.io.ro_flag_clearers;

import com.utils.annotations.ApiMethod;

public interface ReadOnlyFlagClearer {

	/**
	 * If file exist, clears the readonly flag of that file.
	 *
	 * @param filePathString
	 *            The path to the file.
	 * @param verbose
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean clearReadOnlyFlagFile(
			String filePathString,
			boolean verbose);

	/**
	 * Clears the readonly flag of a file.
	 *
	 * @param filePathString
	 *            The path to the file.
	 * @param verbose
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean clearReadOnlyFlagFileNoChecks(
			String filePathString,
			boolean verbose);

	/**
	 * If folder exists, clears the readonly flags of all files in that folder.
	 *
	 * @param folderPathString
	 *            The path to the folder.
	 * @param verbose
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean clearReadOnlyFlagFolder(
			String folderPathString,
			boolean verbose);

	/**
	 * Clears the readonly flags of all files inside a folder.
	 *
	 * @param folderPathString
	 *            The path to the folder.
	 * @param verbose
	 *            If true, messages will be logged.
	 */
	@ApiMethod
	boolean clearReadOnlyFlagFolderNoChecks(
			String folderPathString,
			boolean verbose);
}
