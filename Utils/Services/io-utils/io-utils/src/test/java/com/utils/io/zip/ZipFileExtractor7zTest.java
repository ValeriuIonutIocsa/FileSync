package com.utils.io.zip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.string.StrUtils;

class ZipFileExtractor7zTest {

	@Test
	void testWork() {

		final String zipArchiveFilePathString;
		final String dstFolderPathString;
		final int input = StrUtils.tryParsePositiveInt("11");
		if (input == 1) {
			zipArchiveFilePathString = "D:\\IVI_MISC\\Misc\\mnf\\test\\ChosenPictures.zip";
			dstFolderPathString = "D:\\IVI_MISC\\Misc\\mnf\\test";

		} else if (input == 11) {
			zipArchiveFilePathString = "D:\\IVI_MISC\\Misc\\mnf\\test\\pic1.jpg.zip";
			dstFolderPathString = "D:\\IVI_MISC\\Misc\\mnf\\test";

		} else {
			throw new RuntimeException();
		}

		final boolean deleteExisting = true;

		final ZipFileExtractor7z zipFileExtractor7z = new ZipFileExtractor7z("7z",
				zipArchiveFilePathString, dstFolderPathString, deleteExisting);
		zipFileExtractor7z.work();

		final boolean success = zipFileExtractor7z.isSuccess();
		Assertions.assertTrue(success);
	}
}
