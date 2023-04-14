package com.utils.io.folder_copiers;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.utils.annotations.ApiMethod;
import com.utils.io.folder_deleters.FactoryFolderDeleter;
import com.utils.log.Logger;

class FolderCopierImpl implements FolderCopier {

	FolderCopierImpl() {
	}

	@ApiMethod
	@Override
	public void copyFolder(
			final String srcFolderPathString,
			final String dstFolderPathString,
			final boolean verbose) {

		try {
			Logger.printProgress("copying folder:");
			Logger.printLine(srcFolderPathString);
			Logger.printLine("to:");
			Logger.printLine(dstFolderPathString);

			final boolean deleteFolderSuccess = FactoryFolderDeleter.getInstance()
					.deleteFolder(dstFolderPathString, true);
			if (deleteFolderSuccess) {

				final File srcFolder = new File(srcFolderPathString);
				final File dstFolder = new File(dstFolderPathString);
				FileUtils.copyDirectory(srcFolder, dstFolder);
			}

		} catch (final Exception exc) {
			if (verbose) {
				Logger.printError("failed to copy folder " +
						System.lineSeparator() + srcFolderPathString +
						System.lineSeparator() + "to:" +
						System.lineSeparator() + dstFolderPathString);
			}
			Logger.printException(exc);
		}
	}
}
