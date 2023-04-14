package com.utils.io.file_movers;

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.utils.io.IoUtils;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.io.ro_flag_clearers.FactoryReadOnlyFlagClearer;
import com.utils.log.Logger;

class FileMoverImpl implements FileMover {

	FileMoverImpl() {
	}

	@Override
	public boolean moveFile(
			final String srcFilePathString,
			final String dstFilePathString,
			final boolean copyAttributes,
			final boolean verbose) {

		boolean success = false;
		try {
			Logger.printProgress("moving file:");
			Logger.printLine(srcFilePathString);
			Logger.printLine("to:");
			Logger.printLine(dstFilePathString);

			final boolean keepGoing;
			if (IoUtils.fileExists(dstFilePathString)) {
				keepGoing = FactoryReadOnlyFlagClearer.getInstance()
						.clearReadOnlyFlagFileNoChecks(dstFilePathString, true);
			} else {
				keepGoing = FactoryFolderCreator.getInstance()
						.createParentDirectories(dstFilePathString, true);
			}
			if (keepGoing) {

				final List<CopyOption> copyOptionList = new ArrayList<>();
				copyOptionList.add(StandardCopyOption.REPLACE_EXISTING);
				if (copyAttributes) {
					copyOptionList.add(StandardCopyOption.COPY_ATTRIBUTES);
				}
				final CopyOption[] copyOptionArray = copyOptionList.toArray(new CopyOption[] {});

				final Path srcFilePath = Paths.get(srcFilePathString);
				final Path dstFilePath = Paths.get(dstFilePathString);
				Files.move(srcFilePath, dstFilePath, copyOptionArray);
				success = true;
			}

		} catch (final Exception exc) {
			Logger.printException(exc);
		}

		if (verbose && !success) {
			Logger.printError("failed to move file " +
					System.lineSeparator() + srcFilePathString +
					System.lineSeparator() + "to:" +
					System.lineSeparator() + dstFilePathString);
		}

		return success;
	}
}
