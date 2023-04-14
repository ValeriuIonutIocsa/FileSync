package com.utils.io.progress.listeners;

public interface ProgressListener {

	void transferred(
			long totalRead,
			long totalLength);
}
