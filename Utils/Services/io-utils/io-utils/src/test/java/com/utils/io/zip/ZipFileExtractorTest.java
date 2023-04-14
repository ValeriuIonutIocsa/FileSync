package com.utils.io.zip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ZipFileExtractorTest {

	@Test
	void testWork() {

		final String zipArchiveFilePathString = "D:\\IVI_MISC\\Misc\\mnf\\tmp\\abcd.zip";
		final String dstFolderPathString = "D:\\IVI_MISC\\Misc\\mnf\\tmp";
		final boolean useTempFile = true;
		final boolean deleteExisting = false;
		final int threadCount = 12;
		final boolean updateFileTimes = true;
		final boolean verbose = true;

		final ZipFileExtractor zipFileExtractor = new ZipFileExtractor(zipArchiveFilePathString, dstFolderPathString,
				useTempFile, deleteExisting, threadCount, updateFileTimes, verbose);
		zipFileExtractor.work();
		final boolean success = zipFileExtractor.isSuccess();
		Assertions.assertTrue(success);
	}
}
