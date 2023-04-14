package com.utils.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;

import com.utils.annotations.ApiMethod;
import com.utils.io.file_creators.FactoryFileCreator;
import com.utils.io.file_deleters.FactoryFileDeleter;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.io.ro_flag_clearers.FactoryReadOnlyFlagClearer;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

public class FileLocker {

	private final String lockerName;
	private final String lockFilePathString;

	private RandomAccessFile randomAccessFile;
	private FileChannel fileChannel;
	private FileLock fileLock;

	public FileLocker(
			final String lockerName,
			final String lockFilePathString) {

		this.lockerName = lockerName;
		this.lockFilePathString = lockFilePathString;
	}

	@ApiMethod
	public boolean lock() {

		boolean success = true;
		try {
			Logger.printProgress("acquiring " + lockerName + " file lock");

			if (IoUtils.fileExists(lockFilePathString)) {
				try {
					final String text;
					if (lockerName != null) {
						text = "locked by " + lockerName;
					} else {
						text = "locked";
					}
					WriterUtils.stringToFile(text, StandardCharsets.UTF_8, lockFilePathString);

				} catch (final Exception ignored) {
					success = false;
				}
				if (success) {
					lockExistingFile();
				}

			} else {
				FactoryFolderCreator.getInstance().createParentDirectories(lockFilePathString, true);
				FactoryReadOnlyFlagClearer.getInstance().clearReadOnlyFlagFile(lockFilePathString, true);
				FactoryFileCreator.getInstance().createFile(lockFilePathString, true);
				lockExistingFile();
			}

		} catch (final Exception exc) {
			Logger.printError("failed to acquire the " + lockerName + " file lock");
			Logger.printException(exc);
		}
		return success;
	}

	private void lockExistingFile() throws IOException {

		final File lockFile = new File(lockFilePathString);
		randomAccessFile = new RandomAccessFile(lockFile, "rw");
		fileChannel = randomAccessFile.getChannel();
		fileLock = fileChannel.lock();
	}

	@ApiMethod
	public void unlock() {

		try {
			Logger.printProgress("releasing the " + lockerName + " file lock");

			if (fileLock != null) {

				fileLock.release();
				if (fileChannel == null) {
					Logger.printError("file channel is null");

				} else {
					fileChannel.close();
					if (randomAccessFile == null) {
						Logger.printError("random access file is null");

					} else {
						randomAccessFile.close();
						FactoryFileDeleter.getInstance().deleteFile(lockFilePathString, true);
					}
				}
			}

		} catch (final Exception exc) {
			Logger.printError("failed to release the " + lockerName + " file lock");
			Logger.printException(exc);
		}
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
