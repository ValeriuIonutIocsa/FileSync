package com.utils.io.zip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ZipFileCreatorTest {

	@Test
	void testWork() {

		final String srcFolderPathString = "D:\\IVI_MISC\\Misc\\nf\\tmp\\abcd";
		final String zipArchiveFilePathString = "D:\\IVI_MISC\\Misc\\nf\\tmp\\abcd.zip";
		final boolean useTempFile = true;
		final boolean deleteExisting = true;
		final int threadCount = 12;
		final boolean updateFileTimes = true;
		final boolean verbose = true;

		final ZipFileCreator zipFileCreator = new ZipFileCreator(srcFolderPathString, zipArchiveFilePathString,
				useTempFile, deleteExisting, threadCount, updateFileTimes, verbose);
		zipFileCreator.work();
		final boolean success = zipFileCreator.isSuccess();
		Assertions.assertTrue(success);
	}
}
