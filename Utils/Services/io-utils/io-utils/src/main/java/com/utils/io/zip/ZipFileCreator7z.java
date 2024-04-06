package com.utils.io.zip;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.utils.io.IoUtils;
import com.utils.io.PathUtils;
import com.utils.io.file_deleters.FactoryFileDeleter;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.io.processes.InputStreamReaderThread;
import com.utils.io.processes.ReadBytesHandlerLinesPrint;
import com.utils.log.Logger;

public class ZipFileCreator7z {

	private final String sevenZipExecutablePathString;
	private final String srcFilePathString;
	private final String zipArchiveFilePathString;
	private final boolean deleteExisting;

	private boolean success;

	public ZipFileCreator7z(
			final String sevenZipExecutablePathString,
			final String srcFilePathString,
			final String zipArchiveFilePathString,
			final boolean deleteExisting) {

		this.sevenZipExecutablePathString = sevenZipExecutablePathString;
		this.srcFilePathString = srcFilePathString;
		this.zipArchiveFilePathString = zipArchiveFilePathString;
		this.deleteExisting = deleteExisting;
	}

	public void work() {

		success = false;
		try {
			if (!IoUtils.fileExists(srcFilePathString)) {
				Logger.printError("source file does not exist:" +
						System.lineSeparator() + srcFilePathString);

			} else {
				boolean keepGoing;
				if (deleteExisting && IoUtils.fileExists(zipArchiveFilePathString)) {
					keepGoing = FactoryFileDeleter.getInstance()
							.deleteFile(zipArchiveFilePathString, true, true);
				} else {
					keepGoing = true;
				}

				if (keepGoing) {

					keepGoing = FactoryFolderCreator.getInstance()
							.createParentDirectories(zipArchiveFilePathString, false, true);
					if (keepGoing) {

						final List<String> commandPartList = new ArrayList<>();
						commandPartList.add(sevenZipExecutablePathString);
						commandPartList.add("a");
						commandPartList.add(zipArchiveFilePathString);
						commandPartList.add(srcFilePathString);

						Logger.printProgress("running command:");
						Logger.printLine(StringUtils.join(commandPartList, ' '));

						final String zipArchiveFolderPathString =
								PathUtils.computeParentPath(zipArchiveFilePathString);
						final File zipArchiveFolder = new File(zipArchiveFolderPathString);

						final Process process = new ProcessBuilder()
								.directory(zipArchiveFolder)
								.command(commandPartList)
								.redirectErrorStream(true)
								.start();

						final InputStreamReaderThread inputStreamReaderThread = new InputStreamReaderThread(
								"create zip archive input stream reader", process.getInputStream(),
								StandardCharsets.UTF_8, new ReadBytesHandlerLinesPrint());
						inputStreamReaderThread.start();

						final int exitCode = process.waitFor();
						inputStreamReaderThread.join();

						if (exitCode == 0) {
							success = IoUtils.fileExists(zipArchiveFilePathString);
						}
					}
				}
			}

		} catch (final Exception exc) {
			Logger.printException(exc);
		}

		if (!success) {
			Logger.printError("failed to create ZIP archive:" +
					System.lineSeparator() + zipArchiveFilePathString);
		}
	}

	public boolean isSuccess() {
		return success;
	}
}
