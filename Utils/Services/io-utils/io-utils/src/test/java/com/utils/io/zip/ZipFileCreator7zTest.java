package com.utils.io.zip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ZipFileCreator7zTest {

	@Test
	void testWork() {

		final String srcFilePathString;
		final String zipArchiveFilePathString;
		final int input = Integer.parseInt("11");
		if (input == 1) {
			srcFilePathString = "D:\\IVI_MISC\\Misc\\mnf\\test\\ChosenPictures";
			zipArchiveFilePathString = "D:\\IVI_MISC\\Misc\\mnf\\test\\ChosenPictures.zip";

		} else if (input == 11) {
			srcFilePathString = "D:\\IVI_MISC\\Misc\\mnf\\test\\pic1.jpg";
			zipArchiveFilePathString = "D:\\IVI_MISC\\Misc\\mnf\\test\\pic1.jpg.zip";

		} else {
			throw new RuntimeException();
		}

		final boolean deleteExisting = true;

		final ZipFileCreator7z zipFileCreator7z = new ZipFileCreator7z(
				srcFilePathString, zipArchiveFilePathString, deleteExisting);
		zipFileCreator7z.work();

		final boolean success = zipFileCreator7z.isSuccess();
		Assertions.assertTrue(success);
	}
}
