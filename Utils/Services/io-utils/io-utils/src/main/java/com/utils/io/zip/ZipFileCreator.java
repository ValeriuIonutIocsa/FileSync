package com.utils.io.zip;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.utils.concurrency.no_progress.ConcurrencyUtilsSimpleRegular;
import com.utils.io.IoUtils;
import com.utils.io.PathUtils;
import com.utils.io.file_copiers.FactoryFileCopier;
import com.utils.io.file_deleters.FactoryFileDeleter;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

public class ZipFileCreator {

	private final String srcFilePathString;
	private final String zipArchiveFilePathString;
	private final boolean useTempFile;
	private final boolean deleteExisting;
	private final int threadCount;
	private final boolean updateFileTimes;
	private final boolean verbose;

	private boolean folder;
	private boolean success;

	public ZipFileCreator(
			final String srcFilePathString,
			final String zipArchiveFilePathString,
			final boolean useTempFile,
			final boolean deleteExisting,
			final int threadCount,
			final boolean updateFileTimes,
			final boolean verbose) {

		this.srcFilePathString = srcFilePathString;
		this.zipArchiveFilePathString = zipArchiveFilePathString;
		this.useTempFile = useTempFile;
		this.deleteExisting = deleteExisting;
		this.threadCount = threadCount;
		this.updateFileTimes = updateFileTimes;
		this.verbose = verbose;
	}

	public void work() {

		folder = false;
		success = false;

		Logger.printProgress("creating ZIP archive:");
		Logger.printLine(zipArchiveFilePathString);

		final boolean keepGoing;

		if (IoUtils.directoryExists(srcFilePathString)) {

			folder = true;
			keepGoing = true;

		} else {
			if (IoUtils.fileExists(srcFilePathString)) {
				keepGoing = true;
			} else {
				Logger.printWarning("the source file does not exist:" +
						System.lineSeparator() + srcFilePathString);
				keepGoing = false;
			}
		}
		if (keepGoing) {

			if (deleteExisting && IoUtils.fileExists(zipArchiveFilePathString)) {
				FactoryFileDeleter.getInstance().deleteFile(zipArchiveFilePathString, true);
			}

			FactoryFolderCreator.getInstance().createParentDirectories(zipArchiveFilePathString, true);

			try (FileSystem zipFileSystem =
					ZipUtils.createNewZipFileSystem(zipArchiveFilePathString, useTempFile)) {

				if (folder) {

					final List<Runnable> runnableList = new ArrayList<>();
					final Path srcFilePath = Paths.get(srcFilePathString);
					Files.walkFileTree(srcFilePath, new SimpleFileVisitor<>() {

						@Override
						public FileVisitResult visitFile(
								final Path filePath,
								final BasicFileAttributes attrs) throws IOException {

							runnableList.add(() -> {

								try {
									if (verbose) {
										Logger.printLine("zipping file: " + filePath);
									}

									final Path relativeFilePath = srcFilePath.relativize(filePath);
									final String relativeFilePathString = relativeFilePath.toString();
									final Path zipFilePath = zipFileSystem.getPath(relativeFilePathString);
									final Path zipFileParentFolderPath = zipFilePath.getParent();
									if (zipFileParentFolderPath != null &&
											!Files.isDirectory(zipFileParentFolderPath)) {
										synchronized (this) {
											Files.createDirectories(zipFileParentFolderPath);
										}
									}
									Files.copy(filePath, zipFilePath, StandardCopyOption.REPLACE_EXISTING,
											StandardCopyOption.COPY_ATTRIBUTES);
									if (updateFileTimes) {
										Files.setLastModifiedTime(zipFilePath, FileTime.from(Instant.now()));
									}

								} catch (final Exception exc) {
									Logger.printError("failed to zip file:" + System.lineSeparator() + filePath);
									Logger.printException(exc);
								}
							});

							return super.visitFile(filePath, attrs);
						}
					});
					if (!runnableList.isEmpty()) {
						new ConcurrencyUtilsSimpleRegular(threadCount).executeMultiThreadedTask(runnableList);
					}

				} else {

					try {
						if (verbose) {
							Logger.printLine("zipping file: " + srcFilePathString);
						}

						final String srcFileName = PathUtils.computeFileName(srcFilePathString);
						final Path zipFilePath = zipFileSystem.getPath(srcFileName);
						final String zipFilePathString = zipFilePath.toString();
						FactoryFileCopier.getInstance().copyFile(
								srcFilePathString, zipFilePathString, true, false);
						if (updateFileTimes) {
							Files.setLastModifiedTime(zipFilePath, FileTime.from(Instant.now()));
						}

					} catch (final Exception exc) {
						Logger.printError("failed to zip file:" +
								System.lineSeparator() + srcFilePathString);
						Logger.printException(exc);
					}
				}

				Logger.printStatus("Finished creating ZIP archive.");
				success = true;

			} catch (final Exception exc) {
				Logger.printError("failed to create ZIP archive:" +
						System.lineSeparator() + zipArchiveFilePathString);
				Logger.printException(exc);
			}
		}
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public boolean isFolder() {
		return folder;
	}

	public boolean isSuccess() {
		return success;
	}
}
