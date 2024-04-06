package com.utils.io.zip;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.utils.io.IoUtils;
import com.utils.io.PathUtils;
import com.utils.io.file_deleters.FactoryFileDeleter;
import com.utils.io.folder_deleters.FactoryFolderDeleter;
import com.utils.io.processes.InputStreamReaderThread;
import com.utils.io.processes.ReadBytesHandlerLinesPrint;
import com.utils.log.Logger;

public class ZipFileExtractor7z {

	private final String sevenZipExecutablePathString;
	private final String zipArchiveFilePathString;
	private final String dstFolderPathString;
	private final boolean deleteExisting;

	private boolean success;

	public ZipFileExtractor7z(
			final String sevenZipExecutablePathString,
			final String zipArchiveFilePathString,
			final String dstFolderPathString,
			final boolean deleteExisting) {

		this.sevenZipExecutablePathString = sevenZipExecutablePathString;
		this.zipArchiveFilePathString = zipArchiveFilePathString;
		this.dstFolderPathString = dstFolderPathString;
		this.deleteExisting = deleteExisting;
	}

	public void work() {

		success = false;
		try {
			if (!IoUtils.fileExists(zipArchiveFilePathString)) {
				Logger.printError("ZIP archive does not exist:" +
						System.lineSeparator() + zipArchiveFilePathString);

			} else {
				final boolean keepGoing;
				if (deleteExisting) {

					String zipArchiveNameWoExt = null;
					final String suffix = ".zip";
					final String zipArchiveName = PathUtils.computeFileName(zipArchiveFilePathString);
					if (zipArchiveName.endsWith(suffix)) {
						zipArchiveNameWoExt =
								zipArchiveName.substring(0, zipArchiveName.length() - suffix.length());
					}

					if (StringUtils.isNotBlank(zipArchiveNameWoExt)) {

						final String extractedFilePathString =
								PathUtils.computePath(dstFolderPathString, zipArchiveNameWoExt);
						if (IoUtils.directoryExists(extractedFilePathString)) {
							keepGoing = FactoryFolderDeleter.getInstance()
									.cleanFolder(extractedFilePathString, true, true);
						} else if (IoUtils.regularFileExists(extractedFilePathString)) {
							keepGoing = FactoryFileDeleter.getInstance()
									.deleteFile(extractedFilePathString, true, true);
						} else {
							keepGoing = true;
						}

					} else {
						keepGoing = true;
					}

				} else {
					keepGoing = true;
				}

				if (keepGoing) {

					final List<String> commandPartList = new ArrayList<>();
					commandPartList.add(sevenZipExecutablePathString);
					commandPartList.add("x");
					commandPartList.add(zipArchiveFilePathString);
					commandPartList.add("-o" + dstFolderPathString);
					commandPartList.add("-r");
					commandPartList.add("-y");

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
							"extract zip archive input stream reader", process.getInputStream(),
							StandardCharsets.UTF_8, new ReadBytesHandlerLinesPrint());
					inputStreamReaderThread.start();

					final int exitCode = process.waitFor();
					inputStreamReaderThread.join();

					success = exitCode == 0;
				}
			}

		} catch (final Exception exc) {
			Logger.printException(exc);
		}

		if (!success) {
			Logger.printError("failed to extract ZIP archive:" +
					System.lineSeparator() + zipArchiveFilePathString);
		}
	}

	public boolean isSuccess() {
		return success;
	}
}
