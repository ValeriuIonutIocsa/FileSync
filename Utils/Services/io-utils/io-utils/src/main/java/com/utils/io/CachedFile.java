package com.utils.io;

import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;

public class CachedFile<
		ObjectT> {

	private String filePathString;
	private long size;
	private long lastModifiedTime;
	private ObjectT dataObject;

	public CachedFile() {

		size = -1;
		lastModifiedTime = -1;
	}

	@ApiMethod
	public void cache(
			final String filePathString,
			final ObjectT dataObject) {

		try {
			if (IoUtils.fileExists(filePathString)) {

				this.filePathString = filePathString;
				size = FileSizeUtils.fileSize(filePathString);
				lastModifiedTime = IoUtils.computeFileLastModifiedTime(filePathString);
				this.dataObject = dataObject;
			}

		} catch (final Exception exc) {
			Logger.printError("failed to cache file:" + System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
	}

	@ApiMethod
	public boolean isCached(
			final String filePathString) {

		boolean cached = false;
		try {
			if (this.filePathString != null && this.filePathString.equals(filePathString)) {

				final long size = FileSizeUtils.fileSize(filePathString);
				if (this.size > 0 && this.size == size) {

					final long lastModifiedTime = IoUtils.computeFileLastModifiedTime(filePathString);
					cached = this.lastModifiedTime > 0 && this.lastModifiedTime == lastModifiedTime;
				}
			}

		} catch (final Exception exc) {
			Logger.printError("failed to check if file is cached:" +
					System.lineSeparator() + filePathString);
			Logger.printException(exc);
		}
		return cached;
	}

	@ApiMethod
	public ObjectT getDataObject() {
		return dataObject;
	}
}
