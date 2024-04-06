package com.personal.scripts.gen.fs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.personal.scripts.gen.fs.settings.FolderPair;
import com.utils.concurrency.ThreadUtils;
import com.utils.io.FileSizeUtils;
import com.utils.io.IoUtils;
import com.utils.io.ListFileUtils;
import com.utils.io.PathUtils;
import com.utils.io.file_copiers.FactoryFileCopier;
import com.utils.io.file_deleters.FactoryFileDeleter;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.io.folder_deleters.FactoryFolderDeleter;
import com.utils.log.Logger;

class SyncFoldersWorker {

	private final ExecutorService executorService;

	private int taskCount;

	SyncFoldersWorker() {

		executorService = Executors.newFixedThreadPool(12);
	}

	void work(
			final List<FolderPair> folderPairList) {

		try {
			taskCount = 0;

			for (final FolderPair folderPair : folderPairList) {

				final String srcFolderPathString = folderPair.getSrcFolderPathString();
				final String dstFolderPathString = folderPair.getDstFolderPathString();
				addTask(() -> syncFolders(srcFolderPathString, dstFolderPathString));
			}

			while (taskCount > 0) {
				ThreadUtils.trySleep(100);
			}

			executorService.shutdown();
			final boolean terminationSuccess = executorService.awaitTermination(1000, TimeUnit.MINUTES);
			if (!terminationSuccess) {

				final List<Runnable> notTerminatedRunnableList = executorService.shutdownNow();
				Logger.printWarning(notTerminatedRunnableList.size() +
						" runnables did not terminate execution");
			}

		} catch (final Exception exc) {
			Logger.printError("error occurred while synchronizing files");
			Logger.printException(exc);
		}
	}

	void syncFolders(
			final String srcFolderPathString,
			final String dstFolderPathString) {

		final List<String> srcFolderFolderPathStringList = new ArrayList<>();
		final List<String> srcFolderFilePathStringList = new ArrayList<>();
		ListFileUtils.visitFiles(srcFolderPathString,
				dirPath -> {
					final String folderPathString = dirPath.toString();
					srcFolderFolderPathStringList.add(folderPathString);
				},
				filePath -> {
					final String filePathString = filePath.toString();
					srcFolderFilePathStringList.add(filePathString);
				});

		final List<String> dstFolderFolderPathStringList = new ArrayList<>();
		final List<String> dstFolderFilePathStringList = new ArrayList<>();
		ListFileUtils.visitFiles(dstFolderPathString,
				dirPath -> {
					final String folderPathString = dirPath.toString();
					dstFolderFolderPathStringList.add(folderPathString);
				},
				filePath -> {
					final String filePathString = filePath.toString();
					dstFolderFilePathStringList.add(filePathString);
				});

		for (final String srcFolderFolderPathString : srcFolderFolderPathStringList) {

			final String relativePathString =
					PathUtils.computeRelativePath(srcFolderPathString, srcFolderFolderPathString);
			final String dstFolderFolderPathString =
					PathUtils.computePath(dstFolderPathString, relativePathString);

			if (dstFolderFolderPathStringList.contains(dstFolderFolderPathString)) {

				dstFolderFilePathStringList.remove(dstFolderFolderPathString);
				addTask(() -> syncFolders(srcFolderFolderPathString, dstFolderFolderPathString));

			} else if (dstFolderFilePathStringList.contains(dstFolderFolderPathString)) {

				dstFolderFilePathStringList.remove(dstFolderFolderPathString);
				addTask(() -> {
					deleteFile(dstFolderFolderPathString);
					createFolder(srcFolderFolderPathString, dstFolderFolderPathString);
				});

			} else {
				addTask(() -> createFolder(srcFolderFolderPathString, dstFolderFolderPathString));
			}
		}

		for (final String srcFolderFilePathString : srcFolderFilePathStringList) {

			final String relativePathString =
					PathUtils.computeRelativePath(srcFolderPathString, srcFolderFilePathString);
			final String dstFolderFilePathString =
					PathUtils.computePath(dstFolderPathString, relativePathString);

			if (dstFolderFolderPathStringList.contains(dstFolderFilePathString)) {

				dstFolderFilePathStringList.remove(dstFolderFilePathString);
				addTask(() -> {
					deleteFolder(dstFolderFilePathString);
					createFile(srcFolderFilePathString, dstFolderFilePathString);
				});

			} else if (dstFolderFilePathStringList.contains(dstFolderFilePathString)) {

				dstFolderFilePathStringList.remove(dstFolderFilePathString);
				addTask(() -> syncFiles(srcFolderFilePathString, dstFolderFilePathString));

			} else {
				addTask(() -> createFile(srcFolderFilePathString, dstFolderFilePathString));
			}
		}

		for (final String dstFolderFolderPathString : dstFolderFolderPathStringList) {
			addTask(() -> deleteFolder(dstFolderFolderPathString));
		}
		for (final String dstFolderFilePathString : dstFolderFilePathStringList) {
			addTask(() -> deleteFile(dstFolderFilePathString));
		}
	}

	private static void syncFiles(
			final String srcFolderFilePathString,
			final String dstFolderFilePathString) {

		boolean sameFile = false;
		final long srcFolderFileLastModifiedTime =
				IoUtils.computeFileLastModifiedTime(srcFolderFilePathString);
		if (srcFolderFileLastModifiedTime >= 0) {

			final long dstFolderFileLastModifiedTime =
					IoUtils.computeFileLastModifiedTime(dstFolderFilePathString);
			if (srcFolderFileLastModifiedTime == dstFolderFileLastModifiedTime) {

				final long srcFolderFileSize =
						FileSizeUtils.fileSize(srcFolderFilePathString);
				if (srcFolderFileSize >= 0) {

					final long dstFolderFileSize =
							FileSizeUtils.fileSize(dstFolderFilePathString);
					if (srcFolderFileSize == dstFolderFileSize) {

						sameFile = true;
					}
				}
			}
		}
		if (!sameFile) {

			FactoryFileCopier.getInstance().copyFileNoChecks(
					srcFolderFilePathString, dstFolderFilePathString, true, true, true, true);
		}
	}

	private void createFolder(
			final String srcFolderPathString,
			final String dstFolderPathString) {

		final boolean success = FactoryFolderCreator.getInstance()
				.createDirectories(dstFolderPathString, true, true);
		if (success) {

			final List<String> srcFolderFolderPathStringList = new ArrayList<>();
			final List<String> srcFolderFilePathStringList = new ArrayList<>();
			ListFileUtils.visitFiles(srcFolderPathString,
					dirPath -> {
						final String folderPathString = dirPath.toString();
						srcFolderFolderPathStringList.add(folderPathString);
					},
					filePath -> {
						final String filePathString = filePath.toString();
						srcFolderFilePathStringList.add(filePathString);
					});

			for (final String srcFolderFolderPathString : srcFolderFolderPathStringList) {

				final String relativePathString =
						PathUtils.computeRelativePath(srcFolderPathString, srcFolderFolderPathString);
				final String dstFolderFolderPathString =
						PathUtils.computePath(dstFolderPathString, relativePathString);
				addTask(() -> createFolder(srcFolderFolderPathString, dstFolderFolderPathString));
			}
			for (final String srcFolderFilePathString : srcFolderFilePathStringList) {

				final String relativePathString =
						PathUtils.computeRelativePath(srcFolderPathString, srcFolderFilePathString);
				final String dstFolderFilePathString =
						PathUtils.computePath(dstFolderPathString, relativePathString);
				addTask(() -> createFile(srcFolderFilePathString, dstFolderFilePathString));
			}
		}
	}

	private static void createFile(
			final String srcFolderFilePathString,
			final String dstFolderFilePathString) {

		FactoryFileCopier.getInstance().copyFileNoChecks(
				srcFolderFilePathString, dstFolderFilePathString, false, true, true, true);
	}

	private static void deleteFolder(
			final String dstFolderFilePathString) {

		FactoryFolderDeleter.getInstance()
				.deleteFolder(dstFolderFilePathString, true, true);
	}

	private static void deleteFile(
			final String dstFolderFilePathString) {

		FactoryFileDeleter.getInstance()
				.deleteFile(dstFolderFilePathString, true, true);
	}

	private void addTask(
			final Runnable runnable) {

		synchronized (this) {
			taskCount++;
		}

		executorService.execute(() -> {

			runnable.run();
			synchronized (this) {
				taskCount--;
			}
		});
	}
}
